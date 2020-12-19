package com.cj.musicoffline.model;

public class PlayList {
    private int id;
    private String nameList;
    private int count;

    public PlayList(int id, String nameList, int count) {
        this.id = id;
        this.nameList = nameList;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
