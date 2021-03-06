package com.cj.musicoffline.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cj.musicoffline.ui.fragment.home.HomeFragment;
import com.cj.musicoffline.ui.fragment.library.LibraryFragment;
import com.cj.musicoffline.ui.fragment.library.base.BaseLibraryFragment;
import com.cj.musicoffline.ui.fragment.search.SearchFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mList = new ArrayList();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void addData() {
        mList.add(new HomeFragment());
        mList.add(new SearchFragment());
        mList.add(new BaseLibraryFragment());
    }
}
