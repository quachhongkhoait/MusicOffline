package com.cj.musicoffline.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search")
public class SearchContent {
    @PrimaryKey
    @NonNull
    private String url;
    private long time;

    public SearchContent(@NonNull String url, long time) {
        this.url = url;
        this.time = time;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
