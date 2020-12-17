package com.cj.musicoffline.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "audio")
public class AudioModel implements Parcelable {

    @NonNull
    @PrimaryKey
    private String url;
    private String title;
    private String duration;
    private String idAlbum;
    private String nameAlbum;

    public AudioModel(@NonNull String url, String title, String duration, String idAlbum, String nameAlbum) {
        this.url = url;
        this.title = title;
        this.duration = duration;
        this.idAlbum = idAlbum;
        this.nameAlbum = nameAlbum;
    }

    protected AudioModel(Parcel in) {
        title = in.readString();
        duration = in.readString();
        url = in.readString();
        idAlbum = in.readString();
        nameAlbum = in.readString();
    }

    public static final Creator<AudioModel> CREATOR = new Creator<AudioModel>() {
        @Override
        public AudioModel createFromParcel(Parcel in) {
            return new AudioModel(in);
        }

        @Override
        public AudioModel[] newArray(int size) {
            return new AudioModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public static Creator<AudioModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(duration);
        parcel.writeString(url);
        parcel.writeString(idAlbum);
        parcel.writeString(nameAlbum);
    }
}