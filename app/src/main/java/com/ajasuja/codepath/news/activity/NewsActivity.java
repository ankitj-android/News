package com.ajasuja.codepath.news.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.adapter.NewsArticleAdapter;
import com.ajasuja.codepath.news.fragment.SettingsDialogFragment;
import com.ajasuja.codepath.news.listener.EndlessScrollListener;
import com.ajasuja.codepath.news.model.NewsArticle;
import com.ajasuja.codepath.news.model.Settings;
import com.ajasuja.codepath.news.util.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

import static android.support.v7.widget.SearchView.OnQueryTextListener;
import static butterknife.ButterKnife.bind;
import static com.ajasuja.codepath.news.model.NewsArticle.fromJsonArray;

public class NewsActivity extends AppCompatActivity implements SettingsDialogFragment.SettingsListener{

    @BindView(R.id.webViewNewsDetails) WebView webViewNewsDetails;
    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.gridViewNewsArticles) GridView gridViewNews;


    private NewsArticleAdapter newsArticleAdapter;

    private List<NewsArticle> newsArticles;
    private Settings settings;
    private String query;
    private ConnectivityManager connectivityManager;
    private View parentView;


    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        parentView = findViewById(R.id.activity_news);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (NetworkUtil.canCallService(connectivityManager)) {
            Snackbar.make(parentView, "No Network", Snackbar.LENGTH_SHORT).show();
        }
        bind(this);
        setSupportActionBar(toolbar);

        newsArticles = new ArrayList<>();
        newsArticleAdapter = new NewsArticleAdapter(this, newsArticles);
        gridViewNews.setAdapter(newsArticleAdapter);
        if (settings == null) {
            settings = Settings.getInstance();
        }
        if (query == null) {
            query = formatQuery("donald trump");
        }
        fetchArticles(0);
        gridViewNews.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                fetchArticles(page);
                return true;
            }
        });
//        gridViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                NewsArticle newsArticle = newsArticles.get(i);
//                Log.d("FLOW", newsArticle.toString());
//                Intent news2NewsDetailsIntent = new Intent(NewsActivity.this, NewsArticleDetailsActivity.class);
//                news2NewsDetailsIntent.putExtra("newsArticle", wrap(newsArticle));
//                startActivity(news2NewsDetailsIntent);
//            }
//        });
        gridViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsArticle newsArticle = newsArticles.get(i);
                Log.d("FLOW", newsArticle.toString());
                webViewNewsDetails.getSettings().setLoadsImagesAutomatically(true);
                webViewNewsDetails.getSettings().setJavaScriptEnabled(true);
                webViewNewsDetails.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webViewNewsDetails.loadUrl(newsArticle.getWebUrl());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        MenuItem searchItem = menu.findItem(R.id.search_news);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                query = formatQuery(queryString);
                fetchArticles(0);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private String formatQuery(String queryString) {
        return queryString.replaceAll(" ", "+");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings :
                Log.d("[DEBUG]", "settings action selected");
                showSettingsDialog();
                return true;
            case R.id.search_news :
                Log.d("[DEBUG]", "search selected");
                return true;
            default:
                Log.d("[DEBUG]", "default item selected");
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSettingsDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment();
        settingsDialogFragment.show(fragmentManager, "Settings");
    }

    private void fetchArticles(final int page) {
        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final String nyTimesUrl = buildNyTimesUrl(page, settings);
        Log.d("debug", nyTimesUrl);
        asyncHttpClient.get(nyTimesUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("[RESPONSE]", response.toString());
                try {
                    if (page == 0) {
                        newsArticles.clear();
                    }
                    newsArticles.addAll(fromJsonArray(response.getJSONObject("response").getJSONArray("docs")));
                    newsArticleAdapter.notifyDataSetChanged();
                    Log.d("[DEBUG]", newsArticles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String errorMessage = "Failed NyTimes Api Call : " + statusCode + nyTimesUrl;
                Log.d("debug", errorMessage);
                if (statusCode == 429) {
                    try {
                        Snackbar.make(parentView, "API Rate Limiting", Snackbar.LENGTH_SHORT).show();
                        Thread.sleep(1000);
                        fetchArticles(page);
                    } catch (InterruptedException e) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                }
            }
        });
    }


    private String buildNyTimesUrl(int page, Settings settings) {
        StringBuilder urlBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(settings.getBeginDateInMillis());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        int monthInteger = calendar.get(Calendar.MONTH) + 1;
        String month = (monthInteger > 9) ? String.valueOf(monthInteger) : String.valueOf("0" + monthInteger);
        String day = (calendar.get(Calendar.DAY_OF_MONTH) >= 10) ? String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) : String.valueOf("0" + calendar.get(Calendar.DAY_OF_MONTH));
        String beginDate = year+month+day;//"20170112";
        String sortOrder = settings.getSortOrder().getSortOrder().toLowerCase();
        String newsDeskValues = toNewsDeskValues(settings);
        String urlWithoutNewsDesk = String.format("https://api.nytimes.com/svc/search/v2/articlesearch.json?" +
                "q=%s&page=%d&begin_date=%s&sort=%s&api-key=2ff5b653ccc749ed9bd0640dcec277da",
                query, page, beginDate, sortOrder);
        urlBuilder.append(urlWithoutNewsDesk);
        if (newsDeskValues != null && !newsDeskValues.isEmpty()) {
            urlBuilder.append(String.format("&fq=news_desk:(%s)", toNewsDeskValues(settings)));
        }
        return urlBuilder.toString();
    }

    private String toNewsDeskValues(Settings settings) {
        StringBuilder newsDeskValuesBuilder = new StringBuilder();
        if (settings.isNewsDeskArtsSelected()) {
            newsDeskValuesBuilder.append("\"Arts\"");
            newsDeskValuesBuilder.append(" ");
        }
        if (settings.isNewsDeskFashionAndStyleSelected()) {
            newsDeskValuesBuilder.append("\"Fashion And Style\"");
            newsDeskValuesBuilder.append(" ");
        }
        if (settings.isNewsDeskSportsSelected()) {
            newsDeskValuesBuilder.append("\"Sports\"");
            newsDeskValuesBuilder.append(" ");
        }
        return newsDeskValuesBuilder.toString().trim();
    }

    @Override
    public void onSettingsSave(Settings settings) {
        this.settings = settings;
        fetchArticles(0);
        Log.d("DEBUG", "Back to settings from SettingsDialogFragment with settings : " + settings);
    }
}
