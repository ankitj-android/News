package com.ajasuja.codepath.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.model.NewsArticle;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/16/17.
 */

public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {

    static class ViewHolder {
        @BindView(R.id.imageViewThumbNail) ImageView imageViewThumbNail;
        @BindView(R.id.textViewHeadline) TextView textViewHeadline;

        public ViewHolder(View view) {
            bind(this, view);
        }
    }

    public NewsArticleAdapter(Context context, List<NewsArticle> newsArticles) {
        super(context, android.R.layout.simple_list_item_1, newsArticles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsArticle newsArticle = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news_article, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext()).load(newsArticle.getThumbNailImageUrl()).into(viewHolder.imageViewThumbNail);
        viewHolder.textViewHeadline.setText(newsArticle.getHeadline());
        return convertView;
    }
}
