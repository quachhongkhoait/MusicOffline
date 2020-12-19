package com.cj.musicoffline.eventbuss;

import androidx.fragment.app.Fragment;

public class ChangeFragment {
    private Fragment fm;
    private boolean checkadd;

    public ChangeFragment(Fragment fm) {
        this.fm = fm;
    }

    public ChangeFragment(Fragment fm, boolean checkadd) {
        this.fm = fm;
        this.checkadd = checkadd;
    }

    public boolean isCheckadd() {
        return checkadd;
    }

    public void setCheckadd(boolean checkadd) {
        this.checkadd = checkadd;
    }

    public Fragment getFm() {
        return fm;
    }

    public void setFm(Fragment fm) {
        this.fm = fm;
    }
}
