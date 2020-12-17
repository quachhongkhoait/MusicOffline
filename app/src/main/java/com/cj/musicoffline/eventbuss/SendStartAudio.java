package com.cj.musicoffline.eventbuss;

public class SendStartAudio {
    private boolean check = false;

    public SendStartAudio(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
