package com.ajasuja.codepath.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.model.NewsArticle;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/19/17.
 */

public class NewsArcticlesRecyclerViewAdapter extends RecyclerView.Adapter<NewsArcticlesRecyclerViewAdapter.ViewHolder> {

    private List<NewsArticle> newsArticles;
    private Context context;

    public NewsArcticlesRecyclerViewAdapter(Context context, List<NewsArticle> newsArticles) {
        this.context = context;
        this.newsArticles = newsArticles;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemNewsArticleView = layoutInflater.inflate(R.layout.item_news_article, parent, false);
        return new ViewHolder(itemNewsArticleView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        NewsArticle newsArticle = newsArticles.get(position);
        Glide.with(context).load(newsArticle.getThumbNailImageUrl()).into(viewHolder.imageViewThumbNail);
//        Picasso.with(context).load(newsArticle.getThumbNailImageUrl()).into(viewHolder.imageViewThumbNail);
        viewHolder.textViewHeadline.setText(newsArticle.getHeadline());
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewThumbNail) ImageView imageViewThumbNail;
        @BindView(R.id.textViewHeadline) TextView textViewHeadline;

        public ViewHolder(View itemView) {
            super(itemView);
            bind(this, itemView);
        }
    }
}
