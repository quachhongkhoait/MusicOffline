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
    private String lyrics;
    private String pathLyrics;

    public AudioModel(@NonNull String url, String title, String duration, String idAlbum, String nameAlbum, String lyrics, String pathLyrics) {
        this.url = url;
        this.title = title;
        this.duration = duration;
        this.idAlbum = idAlbum;
        this.nameAlbum = nameAlbum;
        this.lyrics = lyrics;
        this.pathLyrics = pathLyrics;
    }

    protected AudioModel(Parcel in) {
        url = in.readString();
        title = in.readString();
        duration = in.readString();
        idAlbum = in.readString();
        nameAlbum = in.readString();
        lyrics = in.readString();
        pathLyrics = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(duration);
        dest.writeString(idAlbum);
        dest.writeString(nameAlbum);
        dest.writeString(lyrics);
        dest.writeString(pathLyrics);
    }

    @Override
    public int describeContents() {
        return 0;
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

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

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

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getPathLyrics() {
        return pathLyrics;
    }

    public void setPathLyrics(String pathLyrics) {
        this.pathLyrics = pathLyrics;
    }
}