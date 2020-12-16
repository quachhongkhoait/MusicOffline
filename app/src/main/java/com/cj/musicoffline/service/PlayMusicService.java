package com.cj.musicoffline.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.PlayAudio;
import com.cj.musicoffline.eventbuss.SendInfo;
import com.cj.musicoffline.eventbuss.SendService;
import com.cj.musicoffline.eventbuss.SendUI;
import com.cj.musicoffline.eventbuss.UpdateSeekBar;
import com.cj.musicoffline.itf.Playable;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.ui.main.MainActivity;
import com.cj.musicoffline.utils.CreateNotification;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicService extends Service implements Playable {
    private List<AudioModel> mList = new ArrayList();
    private static final String TAG = "nnn";
    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying = true;
    public static int position;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        String json = intent.getStringExtra("list");
        Gson gson = new Gson();
        Type type = new TypeToken<List<AudioModel>>() {
        }.getType();
        mList = gson.fromJson(json, type);
        position = intent.getIntExtra("postion", 0);
        initMediaPlayer();
        playAudio(position);
        return START_NOT_STICKY;
    }

    private void playAudio(int pos) {
        position = pos;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        Uri uri = Uri.parse(mList.get(pos).getUrl());//"content://media/external/audio/media/25"
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (pos >= (mList.size() - 1)) {
                    mediaPlayer.stop();
                } else {
                    onMusicNext();
                }
            }
        });
        CreateNotification.createNotification(this, R.drawable.ic_pause, pos, mList);
        startForeground(1, CreateNotification.notification);
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            Log.d(TAG, isPlaying + " onReceive: " + action);
            switch (action) {
                case "ACTION_PREVIUOS":
                    onMusicPrevious();
                    break;
                case "ACTION_PLAY":
                    if (isPlaying) {
                        onMusicPause();
                    } else {
                        onMusicPlay();
                    }
                    break;
                case "ACTION_NEXT":
                    onMusicNext();
                    break;
                case "ACTION_CLOSE":
                    EventBus.getDefault().post(new SendUI(0, "close"));
                    stopSelf();
                    mediaPlayer.stop();
                    break;
            }
        }
    };

    @Override
    public void onMusicPrevious() {
        if (position != 0) {
            position--;
        } else {
            position = mList.size() - 1;
        }
        CreateNotification.createNotification(this,
                R.drawable.ic_pause, position, mList);
        startForeground(1, CreateNotification.notification);
        playAudio(position);
        EventBus.getDefault().post(new SendUI(position, "play"));
        EventBus.getDefault().post(new SendInfo(mList.get(position).getTitle(), mList.get(position).getIdAlbum()));
        EventBus.getDefault().post(new UpdateSeekBar(mediaPlayer));
    }

    @Override
    public void onMusicPlay() {
        CreateNotification.createNotification(this,
                R.drawable.ic_pause, position, mList);
        startForeground(1, CreateNotification.notification);
        isPlaying = true;
        mediaPlayer.start();
        EventBus.getDefault().post(new SendUI(0, "play"));
    }

    @Override
    public void onMusicPause() {
        CreateNotification.createNotification(this,
                R.drawable.ic_play_arrow, position, mList);
        startForeground(1, CreateNotification.notification);
        isPlaying = false;
        mediaPlayer.pause();
        EventBus.getDefault().post(new SendUI(0, "pause"));
    }

    @Override
    public void onMusicNext() {
        if (position != (mList.size() - 1)) {
            position++;
        } else {
            position = 0;
        }
        CreateNotification.createNotification(this,
                R.drawable.ic_pause, position, mList);
        startForeground(1, CreateNotification.notification);
        playAudio(position);
        EventBus.getDefault().post(new SendUI(position, "play"));
        EventBus.getDefault().post(new SendInfo(mList.get(position).getTitle(), mList.get(position).getIdAlbum()));
        EventBus.getDefault().post(new UpdateSeekBar(mediaPlayer));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateService(SendService sendService) {
        if (sendService.getKey().equals("previous")) {
            onMusicPrevious();
        } else if (sendService.getKey().equals("next")) {
            onMusicNext();
        } else if (sendService.getKey().equals("play")) {
            onMusicPlay();
        } else {
            onMusicPause();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PlayAudio(PlayAudio audio) {
        mediaPlayer.stop();
        playAudio(audio.getPosition());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(broadcastReceiver);
    }
}
