package com.ajasuja.codepath.news.network;

import com.ajasuja.codepath.news.model.NewsArticle;
import com.ajasuja.codepath.news.model.NyTimesResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ajasuja on 3/19/17.
 */

public class NyTimesResponseDeserializer implements JsonDeserializer<NyTimesResponse> {
    @Override
    public NyTimesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject responseObject = jsonObject.get("response").getAsJsonObject();
        JsonArray newsArticlesJsonArray = responseObject.get("docs").getAsJsonArray();
        List<NewsArticle> newsArticles = NewsArticle.fromJsonArray(newsArticlesJsonArray);
        NyTimesResponse nyTimesResponse = new NyTimesResponse();
        nyTimesResponse.setDocs(newsArticles);
        return nyTimesResponse;
    }
}
