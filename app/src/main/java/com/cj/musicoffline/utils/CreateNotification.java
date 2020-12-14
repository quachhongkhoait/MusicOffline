package com.cj.musicoffline.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.broadcast.NotificationActionService;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.ui.main.MainActivity;
import com.cj.musicoffline.ui.playmusic.PlayActivity;

import java.util.List;

public class CreateNotification {
    public static Notification notification;

    public static void createNotification(Context context, AudioModel audioModel, int playbutton, int postion, List<AudioModel> mList) {

        Bitmap icon = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(mList.get(postion).getIdAlbum()), context));
        if (icon == null) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_musicerror);
        }

        PendingIntent pendingIntentPrevious;
        int drw_previous;
        if (false) {//postion == 0
            pendingIntentPrevious = null;
            drw_previous = 0;
        } else {
            Intent intentPrevious = new Intent(context, NotificationActionService.class)
                    .setAction("ACTION_PREVIUOS");
            pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                    intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_previous = R.drawable.ic_skip_previous;
        }

        Intent intentPlay = new Intent(context, NotificationActionService.class)
                .setAction("ACTION_PLAY");
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntentNext;
        int drw_next;
        if (false) {//postion == mList.size() - 1
            pendingIntentNext = null;
            drw_next = 0;
        } else {
            Intent intentNext = new Intent(context, NotificationActionService.class)
                    .setAction("ACTION_NEXT");
            pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_next = R.drawable.ic_skip_next;
        }

        Intent intentClose = new Intent(context, NotificationActionService.class)
                .setAction("ACTION_CLOSE");
        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0,
                intentClose, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent notificationIntent = new Intent(context, PlayActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        RemoteViews notificationLayout =
                new RemoteViews(context.getPackageName(), R.layout.notification_push);

        notificationLayout.setTextViewText(R.id.mTvTitle, audioModel.getTitle());
        notificationLayout.setTextViewText(R.id.mTvDurection, HandlingMusic.createTimerLabel(Integer.parseInt(audioModel.getDuration())));
        notificationLayout.setImageViewBitmap(R.id.mIVAlbum, icon);

        notificationLayout.setImageViewResource(R.id.mPrevious, drw_previous);
        notificationLayout.setImageViewResource(R.id.mPause, playbutton);
        notificationLayout.setImageViewResource(R.id.mNext, drw_next);
        notificationLayout.setImageViewResource(R.id.mClose, R.drawable.ic_close);
        notificationLayout.setOnClickPendingIntent(R.id.mPrevious, pendingIntentPrevious);
        notificationLayout.setOnClickPendingIntent(R.id.mPause, pendingIntentPlay);
        notificationLayout.setOnClickPendingIntent(R.id.mNext, pendingIntentNext);
        notificationLayout.setOnClickPendingIntent(R.id.mClose, pendingIntentClose);

        notification = new NotificationCompat.Builder(context, App.CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_music_note)
                .setLargeIcon(icon)
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayout)
                .setContentIntent(pendingIntent)
                .build();
    }
}
