package com.cj.musicoffline.utils;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.cj.musicoffline.eventbuss.PlayAudio;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.service.PlayMusicService;
import com.cj.musicoffline.ui.playmusic.PlayActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PlayMusic {
    public static void StartMusic(List<AudioModel> arrayList, Context context, int position) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        //start activity
        Intent mIntent = new Intent(context, PlayActivity.class);
        mIntent.putExtra("postion", position);
        mIntent.putExtra("list", json);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
        //start service
        if (!CheckService.isMyServiceRunning(context, PlayMusicService.class)) {
            Intent intent = new Intent(context, PlayMusicService.class);
            intent.putExtra("list", json);
            intent.putExtra("postion", position);
            ContextCompat.startForegroundService(context, intent);
        } else {
            EventBus.getDefault().post(new PlayAudio(position));
        }
    }
}
