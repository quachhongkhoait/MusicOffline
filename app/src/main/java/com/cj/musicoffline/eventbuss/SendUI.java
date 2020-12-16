package com.cj.musicoffline.eventbuss;

import com.cj.musicoffline.model.AudioModel;

public class SendUI {
    private int position;
    private String action;

    public SendUI(int position) {
        this.position = position;
    }

    public SendUI(int position, String action) {
        this.action = action;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

