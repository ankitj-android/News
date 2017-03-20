package com.ajasuja.codepath.news.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajasuja on 3/15/17.
 */
@Parcel
public class NewsArticle {

    @SerializedName("web_url")
    String webUrl;
    String thumbNailImageUrl;
    String headline;

    public NewsArticle() {
        // for parceler
    }

    public NewsArticle(JSONObject jsonObject) throws JSONException {
        webUrl = jsonObject.getString("web_url");
        this.headline = jsonObject.getJSONObject("headline").getString("main");
        JSONArray multimediaObjects = jsonObject.getJSONArray("multimedia");
        if (multimediaObjects.length() > 0) {
            this.thumbNailImageUrl = multimediaObjects.getJSONObject(0).getString("url");
        }
    }

    public NewsArticle(JsonObject jsonObject) {
        webUrl = jsonObject.get("web_url").getAsString();
        this.headline = jsonObject.get("headline").getAsJsonObject().get("main").getAsString();
        JsonArray multimediaObjects = jsonObject.get("multimedia").getAsJsonArray();
        if (multimediaObjects.size() > 0) {
            this.thumbNailImageUrl = multimediaObjects.get(0).getAsJsonObject().get("url").getAsString();
        }
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbNailImageUrl() {
        if (thumbNailImageUrl != null) {
            return "http://www.nytimes.com/" + thumbNailImageUrl;
        }
//        else {
//            return "http://www.nytimes.com/" + "images/2012/01/01/us/01ground-span/01ground-span-thumbStandard.jpg";
//        }
        return null;
    }

    public static List<NewsArticle> fromJsonArray(JSONArray articlesJsonArray) throws JSONException {
        List<NewsArticle> newsArticles = new ArrayList<>();
        for (int i=0; i< articlesJsonArray.length(); i++) {
            newsArticles.add(new NewsArticle(articlesJsonArray.getJSONObject(i)));
        }
        return newsArticles;
    }

    public static List<NewsArticle> fromJsonArray(JsonArray articlesJsonArray) {
        List<NewsArticle> newsArticles = new ArrayList<>();
        for (int i=0; i< articlesJsonArray.size(); i++) {
            newsArticles.add(new NewsArticle(articlesJsonArray.get(i).getAsJsonObject()));
        }
        return newsArticles;
    }
    @Override
    public String toString() {
        return "NewsArticle{" +
                "thumbNailImageUrl='" + thumbNailImageUrl + '\'' +
                ", headline='" + headline + '\'' +
                '}';
    }
}
