package com.cj.musicoffline.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class PlayListModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameList;

    public PlayListModel(int id, String nameList) {
        this.id = id;
        this.nameList = nameList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }
}
