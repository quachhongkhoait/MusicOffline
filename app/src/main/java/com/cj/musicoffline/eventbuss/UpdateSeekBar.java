package com.cj.musicoffline.eventbuss;

import android.media.MediaPlayer;

public class UpdateSeekBar {
    private MediaPlayer mediaPlayer;

    public UpdateSeekBar(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
