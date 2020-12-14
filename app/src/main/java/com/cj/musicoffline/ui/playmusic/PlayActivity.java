package com.cj.musicoffline.ui.playmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.SendInfo;
import com.cj.musicoffline.eventbuss.SendService;
import com.cj.musicoffline.eventbuss.SendUI;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.service.PlayMusicService;
import com.cj.musicoffline.ui.playmusic.fragment.InfoFragment;
import com.cj.musicoffline.ui.playmusic.fragment.LyricsFragment;
import com.cj.musicoffline.utils.HandlingMusic;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private ImageView mIVBack, mIVAlarm;
    private TextView mTVPrevious, mTVPause, mTVNext, mTVTotalTime, mTVCurrentTime;
    private SeekBar mSeekBar;
    private Handler mHandler = new Handler();
    private AudioModel mAudioModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_play);
        mAudioModel = getIntent().getParcelableExtra("audio");
        setUp();
        onClick();
        addData(mAudioModel);
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

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.mTabDot);
        mViewPager = findViewById(R.id.mViewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //
        String title = "";
//        title = mAudioModel.getTitle();
        String image = "";
//        image = mAudioModel.getIdAlbum();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("image", image);
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(bundle);
        mAdapter.addData(infoFragment);
        mAdapter.addData(new LyricsFragment());
        //
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SendUI sendUI) {
        if (sendUI.getAudioModel() != null) {
            addData(sendUI.getAudioModel());
        } else {
            if (sendUI.getAction().equals("pause")) {
                mTVPause.setBackgroundResource(R.drawable.ic_play_while);
            } else {
                mTVPause.setBackgroundResource(R.drawable.ic_pause_while);
            }
        }
    }

    private void addData(AudioModel audioModel) {
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
        final Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (PlayMusicService.mediaPlayer != null) {
                    mSeekBar.setProgress(PlayMusicService.mediaPlayer.getCurrentPosition());
                    int mCurrentPosition = mSeekBar.getProgress();
                    mTVCurrentTime.setText(HandlingMusic.createTimerLabel(mCurrentPosition));
                    if (mSeekBar.getProgress() == mSeekBar.getMax()) {
//                        rotate.cancel();
//                        mImgAlbum.startAnimation(rotate);
                    }
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
                break;
            case R.id.mTVPause:
                if (PlayMusicService.isPlaying) action = "pause";
                else action = "play";
                break;
            case R.id.mTVNext:
                action = "next";
                break;
            case R.id.mIVAlarm:
                Toast.makeText(this, "Thiết lập tắt", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mIVBack:
                Toast.makeText(this, "Back nè", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        if (!action.equals("")) {
            EventBus.getDefault().post(new SendService(action));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}