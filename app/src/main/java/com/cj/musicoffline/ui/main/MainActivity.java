package com.cj.musicoffline.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.cj.musicoffline.R;
import com.cj.musicoffline.itf.OnCurrentFragmentListener;
import com.cj.musicoffline.ui.fragment.library.base.BaseLibraryFragment;
import com.cj.musicoffline.ui.fragment.library.songs.SongsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnCurrentFragmentListener {
    private ViewPagerAdapter mAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }

    private void setUp() {
        mViewPager = findViewById(R.id.mViewPager);
        BottomNavigationView mBottomNav = findViewById(R.id.mBottomNav);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addData();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBottomNav.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_search:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_library:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onCurrentFragment(int i) {
        if (i == 1) {
        }
    }

    @Override
    public void onBackPressed() {
        if (!backStack()){
            super.onBackPressed();
        }
    }

    private boolean backStack() {
        for (Fragment frag : getSupportFragmentManager().getFragments()) {
            if (frag.isVisible() && mViewPager.getCurrentItem() == 2) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return true;
                }
            }
        }
        return false;
    }
}