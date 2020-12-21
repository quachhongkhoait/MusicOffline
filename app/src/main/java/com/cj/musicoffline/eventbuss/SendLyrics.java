package com.cj.musicoffline.eventbuss;

public class SendLyrics {
    String idLyrics;
    String path;

    public SendLyrics(String idLyrics, String path) {
        this.idLyrics = idLyrics;
        this.path = path;
    }

    public String getIdLyrics() {
        return idLyrics;
    }

    public void setIdLyrics(String idLyrics) {
        this.idLyrics = idLyrics;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
