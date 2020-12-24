package com.cj.musicoffline.ui.playmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.PlayAudio;
import com.cj.musicoffline.eventbuss.SendInfo;
import com.cj.musicoffline.eventbuss.SendLyrics;
import com.cj.musicoffline.eventbuss.SendService;
import com.cj.musicoffline.eventbuss.SendUI;
import com.cj.musicoffline.eventbuss.UpdateSeekBar;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.service.PlayMusicService;
import com.cj.musicoffline.ui.fragment.library.dialog.TimeOffDialog;
import com.cj.musicoffline.ui.playmusic.fragment.InfoFragment;
import com.cj.musicoffline.ui.playmusic.fragment.LyricsFragment;
import com.cj.musicoffline.utils.Constain;
import com.cj.musicoffline.utils.HandlingMusic;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<AudioModel> mList = new ArrayList<>();
    private ViewPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private ImageView mIVBack, mIVAlarm;
    private TextView mTVPrevious, mTVPause, mTVNext, mTVTotalTime, mTVCurrentTime;
    private SeekBar mSeekBar;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    public static int position;
    public static boolean isPlayingUI = true;
    private volatile boolean mIsStopped = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_play);
        String json = getIntent().getStringExtra("list");
        Gson gson = new Gson();
        Type type = new TypeToken<List<AudioModel>>() {
        }.getType();
        mList = gson.fromJson(json, type);
        position = getIntent().getIntExtra("postion", 0);
        setUp();
        onClick();
        addData(PlayMusicService.mediaPlayer);
    }


    private void onClick() {
        mTVPrevious.setOnClickListener(this);
        mTVPause.setOnClickListener(this);
        mTVNext.setOnClickListener(this);
        mIVBack.setOnClickListener(this);
        mIVAlarm.setOnClickListener(this);
    }

    private void setUp() {
        mIVBack = findViewById(R.id.mIVBack);
        mIVAlarm = findViewById(R.id.mIVAlarm);
        mTVPrevious = findViewById(R.id.mTVPrevious);
        mTVPause = findViewById(R.id.mTVPause);
        mTVNext = findViewById(R.id.mTVNext);
        mTVTotalTime = findViewById(R.id.mTVTotalTime);
        mTVCurrentTime = findViewById(R.id.mTVCurrentTime);
        mSeekBar = findViewById(R.id.mMusicSeekBar);

        TabLayout mTabLayout = findViewById(R.id.mTabDot);
        mViewPager = findViewById(R.id.mViewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //
        String title = "";
        title = mList.get(position).getTitle();
        String image = "";
        image = mList.get(position).getIdAlbum();
        Bundle bundleIF = new Bundle();
        bundleIF.putString("title", title);
        bundleIF.putString("image", image);
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(bundleIF);
//        EventBus.getDefault().post(new SendLyrics(mList.get(position).getLyrics(), mList.get(position).getPathLyrics()));
        Bundle bundleLR = new Bundle();
        bundleLR.putString("idLyrics", mList.get(position).getLyrics());
        bundleLR.putString("Path", mList.get(position).getPathLyrics());
        LyricsFragment lyricsFragment = new LyricsFragment();
        lyricsFragment.setArguments(bundleLR);
        mAdapter.addData(infoFragment);
        mAdapter.addData(lyricsFragment);
        //
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SendUI sendUI) {
        if (isStringInt(sendUI.getAction())) {
            changStart("play");
        } else {
            if (sendUI.getAction().equals("close")) {
                isPlayingUI = false;
                changStart("pause");
            } else {
                changStart(sendUI.getAction());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateSeekBar(UpdateSeekBar updateSeekBar) {
//        mIsStopped = true;
//        mHandler = new Handler();
        addData(PlayMusicService.mediaPlayer);
    }

    private void changStart(String s) {
        if (s.equals("pause")) {
            mTVPause.setBackgroundResource(R.drawable.ic_play_while);
        } else {
            mTVPause.setBackgroundResource(R.drawable.ic_pause_while);
        }
    }

    private void addData(MediaPlayer mediaPlayer) {
        mSeekBar.setProgress(PlayMusicService.mediaPlayer.getCurrentPosition() / 1000 % 60);
        mSeekBar.setMax(PlayMusicService.mediaPlayer.getDuration());
        mTVTotalTime.setText(HandlingMusic.createTimerLabel(PlayMusicService.mediaPlayer.getDuration()));
        //xử lý
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    PlayMusicService.mediaPlayer.seekTo(i);
                    mSeekBar.setProgress(i);
                    mTVCurrentTime.setText(HandlingMusic.createTimerLabel(PlayMusicService.mediaPlayer.getCurrentPosition()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress(PlayMusicService.mediaPlayer.getCurrentPosition());
                int mCurrentPosition = mSeekBar.getProgress();
                mTVCurrentTime.setText(HandlingMusic.createTimerLabel(mCurrentPosition));
                if (mSeekBar.getProgress() == mSeekBar.getMax()) {
//                        rotate.cancel();
//                        mImgAlbum.startAnimation(rotate);
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        mRunnable.run();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String action = "";
        switch (v.getId()) {
            case R.id.mTVPrevious:
                action = "previous";
                changStart("play");
                sendService(action);
                break;
            case R.id.mTVPause:
                isPlayingUI = PlayMusicService.isPlaying;
                if (isPlayingUI) {
                    action = "pause";
                } else {
                    action = "play";
                }
                sendService(action);
                break;
            case R.id.mTVNext:
                action = "next";
                changStart("play");
                sendService(action);
                break;
            case R.id.mIVAlarm:
                TimeOffDialog timeOffDialog = new TimeOffDialog();
//                timeOffDialog.setCancelable(false);
                timeOffDialog.show(getSupportFragmentManager(), Constain.keyDialogAlarm);
                break;
            case R.id.mIVBack:
                finish();
                break;
        }
    }

    private void sendService(String action) {

        if (!isMyServiceRunning(PlayMusicService.class)) {
            Log.d("nnn", "sendService: " + position);
            if (action.equals("previous")) {
                position -= 1;
                EventBus.getDefault().post(new SendInfo(mList.get(position).getTitle(), mList.get(position).getIdAlbum()));
            } else if (action.equals("next")) {
                position += 1;
                EventBus.getDefault().post(new SendInfo(mList.get(position).getTitle(), mList.get(position).getIdAlbum()));
            } else if (action.equals("play")) {
                updateUI(new SendUI(0, "play"));
            } else {
                updateUI(new SendUI(0, "pause"));
            }
            Gson gson = new Gson();
            String json = gson.toJson(mList);
            Intent intent = new Intent(this, PlayMusicService.class);
            intent.putExtra("list", json);
            intent.putExtra("postion", position);
            ContextCompat.startForegroundService(this, intent);

        } else {
            if (!action.equals("")) {
                EventBus.getDefault().post(new SendService(action));
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}