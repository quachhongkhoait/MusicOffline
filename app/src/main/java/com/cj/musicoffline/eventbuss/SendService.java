package com.cj.musicoffline.eventbuss;

public class SendService {
    private String key;

    public SendService(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
