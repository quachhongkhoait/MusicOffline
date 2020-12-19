package com.cj.musicoffline.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite")
public class FavouriteModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String url;
    private int idplaylist;

    public FavouriteModel(int id, String url, int idplaylist) {
        this.id = id;
        this.url = url;
        this.idplaylist = idplaylist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdplaylist() {
        return idplaylist;
    }

    public void setIdplaylist(int idplaylist) {
        this.idplaylist = idplaylist;
    }
}
