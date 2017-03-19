package com.ajasuja.codepath.news.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajasuja.codepath.news.R;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/19/17.
 */

public class NewsArticleRecylerViewHolderWithImage extends RecyclerView.ViewHolder {

    @BindView(R.id.imageViewThumbNail) ImageView imageViewThumbNail;
    @BindView(R.id.textViewHeadline) TextView textViewHeadline;

    public NewsArticleRecylerViewHolderWithImage(View itemView) {
        super(itemView);
        bind(this, itemView);
    }

    public ImageView getImageViewThumbNail() {
        return imageViewThumbNail;
    }

    public void setImageViewThumbNail(ImageView imageViewThumbNail) {
        this.imageViewThumbNail = imageViewThumbNail;
    }

    public TextView getTextViewHeadline() {
        return textViewHeadline;
    }

    public void setTextViewHeadline(TextView textViewHeadline) {
        this.textViewHeadline = textViewHeadline;
    }
}
