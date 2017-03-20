package com.ajasuja.codepath.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ajasuja on 3/19/17.
 */

public class NyTimesResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("docs")
    @Expose
    private List<NewsArticle> articles = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public void setDocs(List<NewsArticle> articles) {
        this.articles = articles;
    }
}
