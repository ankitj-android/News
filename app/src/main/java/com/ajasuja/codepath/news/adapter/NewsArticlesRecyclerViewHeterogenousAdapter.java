package com.ajasuja.codepath.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.model.NewsArticle;
import com.ajasuja.codepath.news.view.NewsArticleRecyclerViewHolderWithoutImage;
import com.ajasuja.codepath.news.view.NewsArticleRecylerViewHolderWithImage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ajasuja on 3/19/17.
 */

public class NewsArticlesRecyclerViewHeterogenousAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsArticle> newsArticles;
    private Context context;
    private static final int WITHOUT_IMAGE = 0;
    private static final int WITH_IMAGE = 1;

    public NewsArticlesRecyclerViewHeterogenousAdapter(Context context, List<NewsArticle> newsArticles) {
        this.context = context;
        this.newsArticles = newsArticles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case WITH_IMAGE:
                View viewWithImage = inflater.inflate(R.layout.item_news_article, parent, false);
                viewHolder = new NewsArticleRecylerViewHolderWithImage(viewWithImage);
                break;
            case WITHOUT_IMAGE :
            default:
                View viewWithoutImage = inflater.inflate(R.layout.item_news_article_without_image, parent, false);
                viewHolder = new NewsArticleRecyclerViewHolderWithoutImage(viewWithoutImage);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        NewsArticle newsArticle = newsArticles.get(position);
        switch (viewHolder.getItemViewType()) {
            case WITH_IMAGE:
                NewsArticleRecylerViewHolderWithImage newsArticleRecylerViewHolderWithImage = (NewsArticleRecylerViewHolderWithImage) viewHolder;
                Picasso.with(context)
                        .load(newsArticle.getThumbNailImageUrl())
                        .into(newsArticleRecylerViewHolderWithImage.getImageViewThumbNail());
                newsArticleRecylerViewHolderWithImage.getTextViewHeadline().setText(newsArticle.getHeadline());
                break;
            case WITHOUT_IMAGE:
            default:
                NewsArticleRecyclerViewHolderWithoutImage newsArticleRecyclerViewHolderWithoutImage = (NewsArticleRecyclerViewHolderWithoutImage) viewHolder;
                newsArticleRecyclerViewHolderWithoutImage.getTextViewHeadline().setText(newsArticle.getHeadline());
        }
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        NewsArticle newsArticle = newsArticles.get(position);
        if (newsArticle.getThumbNailImageUrl() == null
                || newsArticle.getThumbNailImageUrl().isEmpty()) {
            return WITHOUT_IMAGE;
        }
        return WITH_IMAGE;
    }
}
