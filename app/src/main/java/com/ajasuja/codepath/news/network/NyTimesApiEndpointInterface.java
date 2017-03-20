package com.ajasuja.codepath.news.network;

import com.ajasuja.codepath.news.model.NyTimesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ajasuja on 3/19/17.
 */

public interface NyTimesApiEndpointInterface {

    @GET("/svc/search/v2/articlesearch.json")
    Call<NyTimesResponse> searchArticles(@Query("page") int pageNumber,
            @Query("q") String query, @Query("begin_date") String beginDate,
            @Query("sort") String sortOrder, @Query("api-key") String apiKey);
}
