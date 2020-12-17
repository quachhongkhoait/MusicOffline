package com.cj.musicoffline.room;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.AudioModel;

import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private MusicDao mMusicDao;
    private LiveData<List<AudioModel>> mAllMusic;

    public MusicRepository(Context context) {
        MusicDatabase db = MusicDatabase.getDatabase(context);
        mMusicDao = db.musicDao();
        mAllMusic = mMusicDao.getAllMusics();
    }

    public LiveData<List<AudioModel>> getAllMusic() {
        return mAllMusic;
    }

    public void insert(AudioModel word) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mMusicDao.insert(word);
        });
    }
}
