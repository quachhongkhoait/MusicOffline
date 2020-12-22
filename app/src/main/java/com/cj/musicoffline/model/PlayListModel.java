package com.cj.musicoffline.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class PlayListModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameList;
    private int count;

    public PlayListModel(int id, String nameList, int count) {
        this.id = id;
        this.nameList = nameList;
        this.count = count;
    }

    protected PlayListModel(Parcel in) {
        id = in.readInt();
        nameList = in.readString();
        count = in.readInt();
    }

    public static final Creator<PlayListModel> CREATOR = new Creator<PlayListModel>() {
        @Override
        public PlayListModel createFromParcel(Parcel in) {
            return new PlayListModel(in);
        }

        @Override
        public PlayListModel[] newArray(int size) {
            return new PlayListModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nameList);
        dest.writeInt(count);
    }
}
