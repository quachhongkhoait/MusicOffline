package com.cj.musicoffline.eventbuss;

import androidx.fragment.app.Fragment;

public class ChangeFragment {
    Fragment fm;

    public ChangeFragment(Fragment fm) {
        this.fm = fm;
    }

    public Fragment getFm() {
        return fm;
    }

    public void setFm(Fragment fm) {
        this.fm = fm;
    }
}
