package com.ajasuja.codepath.news.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.model.NewsArticle;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/16/17.
 */

public class NewsArticleDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.imageViewThumbNail) ImageView imageViewThumbNail;
    @BindView(R.id.textViewHeadline) TextView textViewHeadline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        //view
        bind(this);
        //data
        NewsArticle newsArticle = Parcels.unwrap(getIntent().getParcelableExtra("newsArticle"));
        // data to view
        Picasso.with(getApplicationContext()).load(newsArticle.getThumbNailImageUrl()).into(imageViewThumbNail);
        textViewHeadline.setText(newsArticle.getHeadline());
    }
}
