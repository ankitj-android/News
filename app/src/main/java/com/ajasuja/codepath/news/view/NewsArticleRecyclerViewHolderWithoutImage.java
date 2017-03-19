package com.ajasuja.codepath.news.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ajasuja.codepath.news.R;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/19/17.
 */

public class NewsArticleRecyclerViewHolderWithoutImage extends RecyclerView.ViewHolder {

    @BindView(R.id.textViewHeadline) TextView textViewHeadline;

    public NewsArticleRecyclerViewHolderWithoutImage(View itemView) {
        super(itemView);
        bind(this, itemView);
    }

    public TextView getTextViewHeadline() {
        return textViewHeadline;
    }

    public void setTextViewHeadline(TextView textViewHeadline) {
        this.textViewHeadline = textViewHeadline;
    }
}
