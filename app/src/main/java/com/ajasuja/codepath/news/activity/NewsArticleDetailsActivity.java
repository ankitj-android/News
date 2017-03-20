package com.ajasuja.codepath.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.model.NewsArticle;
import com.ajasuja.codepath.news.util.CustomBrowser;

import org.parceler.Parcels;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/16/17.
 */

public class NewsArticleDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.webViewNewsDetails) WebView webViewNewsDetails;

//    @BindView(R.id.imageViewThumbNail) ImageView imageViewThumbNail;
//    @BindView(R.id.textViewHeadline) TextView textViewHeadline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("Article Details");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        //view
        bind(this);
        setSupportActionBar(toolbar);

        //data
        NewsArticle newsArticle = Parcels.unwrap(getIntent().getParcelableExtra("newsArticle"));
        // data to view

        webViewNewsDetails.getSettings().setLoadsImagesAutomatically(true);
        webViewNewsDetails.getSettings().setJavaScriptEnabled(true);
        webViewNewsDetails.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webViewNewsDetails.setWebViewClient(new CustomBrowser());
        webViewNewsDetails.loadUrl(newsArticle.getWebUrl());

//        Picasso.with(getApplicationContext()).load(newsArticle.getThumbNailImageUrl()).into(imageViewThumbNail);
//        textViewHeadline.setText(newsArticle.getHeadline());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT, webViewNewsDetails.getUrl());

        miShare.setShareIntent(shareIntent);

        return true;
    }
}
