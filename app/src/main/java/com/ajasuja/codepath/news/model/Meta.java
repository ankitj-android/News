package com.ajasuja.codepath.news.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajasuja on 3/19/17.
 */

public class Meta {

    @SerializedName("hits")
    private Integer hits;
    @SerializedName("time")
    private Integer time;
    @SerializedName("offset")
    private Integer offset;

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
