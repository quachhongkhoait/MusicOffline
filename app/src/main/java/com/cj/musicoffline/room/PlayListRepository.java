package com.cj.musicoffline.room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.PlayListModel;

import java.util.List;

public class PlayListRepository {
    private PlayListDao mPlayListDao;
    private LiveData<List<PlayListModel>> mAllPlayList;

    public PlayListRepository(Context context) {
        MusicDatabase db = MusicDatabase.getDatabase(context);
        mPlayListDao = db.playlistDao();
        mAllPlayList = mPlayListDao.getPlayList();
    }

    public LiveData<List<PlayListModel>> getPlayList() {
        return mAllPlayList;
    }

    public void insertPlayList(PlayListModel playListModel) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mPlayListDao.insert(playListModel);
        });
    }

    public void deletePlayList(int id) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mPlayListDao.deleteByID(id);
        });
    }
}
