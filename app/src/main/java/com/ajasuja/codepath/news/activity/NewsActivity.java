package com.ajasuja.codepath.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.adapter.NewsArticleAdapter;
import com.ajasuja.codepath.news.model.NewsArticle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

import static android.support.v7.widget.SearchView.OnQueryTextListener;
import static butterknife.ButterKnife.bind;
import static com.ajasuja.codepath.news.model.NewsArticle.fromJsonArray;
import static org.parceler.Parcels.wrap;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.gridViewNewsArticles) GridView gridViewNews;

    private NewsArticleAdapter newsArticleAdapter;

    private List<NewsArticle> newsArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        bind(this);
        setSupportActionBar(toolbar);

        newsArticles = new ArrayList<>();
        newsArticleAdapter = new NewsArticleAdapter(this, newsArticles);
        gridViewNews.setAdapter(newsArticleAdapter);
        fetchArticles("donald trump");
        gridViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsArticle newsArticle = newsArticles.get(i);
                Log.d("FLOW", newsArticle.toString());
                Intent news2NewsDetailsIntent = new Intent(NewsActivity.this, NewsArticleDetailsActivity.class);
                news2NewsDetailsIntent.putExtra("newsArticle", wrap(newsArticle));
                startActivity(news2NewsDetailsIntent);
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
            public boolean onQueryTextSubmit(String query) {
                fetchArticles(query);
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
    private void fetchArticles(String query) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String NY_TIMES_URL = String.format("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=%s&page=0&api-key=2ff5b653ccc749ed9bd0640dcec277da", query);
        asyncHttpClient.get(NY_TIMES_URL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("[RESPONSE]", response.toString());
                try {
                    newsArticles.clear();
                    newsArticles.addAll(fromJsonArray(response.getJSONObject("response").getJSONArray("docs")));
                    newsArticleAdapter.notifyDataSetChanged();
                    Log.d("[RESPONSE]", newsArticles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
