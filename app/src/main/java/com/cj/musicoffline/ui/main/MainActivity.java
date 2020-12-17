package com.cj.musicoffline.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.cj.musicoffline.R;
import com.cj.musicoffline.itf.OnCurrentFragmentListener;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.room.MusicRepository;
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
        checkPermission();
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
        if (!backStack()) {
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

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            getMusic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        getMusic();
                    }
                } else {
                    finish();
                }
                return;
            }
        }
    }

    private void insertRoom(AudioModel audioModel) {
        MusicRepository mReponsitory = new MusicRepository(getApplication());
        mReponsitory.insert(audioModel);
    }

    private void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int songID = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int idw = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            do {
                String nameAlbum = songCursor.getString(idw);
                String currentTitle = songCursor.getString(songTitle);
                String currentDuration = songCursor.getString(songDuration);
//                String currentDuration = HandlingMusic.convertDuration(songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                Long ur = songCursor.getLong(songID);
                Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ur);
                String albumId = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                insertRoom(new AudioModel(String.valueOf(trackUri),currentTitle, currentDuration, albumId, nameAlbum));
            } while (songCursor.moveToNext());
        }
    }
}