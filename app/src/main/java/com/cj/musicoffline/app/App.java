package com.cj.musicoffline.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cj.musicoffline.utils.SessionManager;

public class App extends Application {
    public static String CHANNEL_ID = "ServiceChannel";
    public static LocalBroadcastManager mBroadcaster;
    public static ViewModelProvider.Factory factory;

    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.getInstance().init(this);
        createNotificationChannel();
        mBroadcaster = LocalBroadcastManager.getInstance(this);
        factory = new ViewModelProvider.AndroidViewModelFactory(this);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Khoa Nè",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }


}
