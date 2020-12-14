package com.cj.musicoffline.eventbuss;

import com.cj.musicoffline.model.AudioModel;

public class SendUI {
    private AudioModel audioModel;
    private String action;

    public SendUI(AudioModel audioModel) {
        this.audioModel = audioModel;
    }

    public SendUI(String action) {
        this.action = action;
    }

    public AudioModel getAudioModel() {
        return audioModel;
    }

    public void setAudioModel(AudioModel audioModel) {
        this.audioModel = audioModel;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

