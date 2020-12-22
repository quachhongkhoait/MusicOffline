package com.cj.musicoffline.eventbuss;

public class UpdateVolum {
    private boolean volume;

    public UpdateVolum(boolean volume) {
        this.volume = volume;
    }

    public boolean isVolume() {
        return volume;
    }

    public void setVolume(boolean volume) {
        this.volume = volume;
    }
}
