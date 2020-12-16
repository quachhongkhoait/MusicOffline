package com.cj.musicoffline.eventbuss;

public class PlayAudio {
    private int position;

    public PlayAudio(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
