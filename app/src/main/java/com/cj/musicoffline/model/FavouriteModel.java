package com.cj.musicoffline.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite")
public class FavouriteModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String url;
    private String idAlbum;

    public FavouriteModel(int id, String url, String idAlbum) {
        this.id = id;
        this.url = url;
        this.idAlbum = idAlbum;
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

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }
}
