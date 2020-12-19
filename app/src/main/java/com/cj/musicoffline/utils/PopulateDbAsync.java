package com.cj.musicoffline.utils;

import android.os.AsyncTask;

import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.room.MusicDatabase;
import com.cj.musicoffline.room.PlayListDao;


public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
    private final PlayListDao mPlayListDao;

    public PopulateDbAsync(MusicDatabase db) {
        this.mPlayListDao = db.playlistDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (SessionManager.getInstance().getKeyInsertFavourite()) {
            SessionManager.getInstance().setKeyInsertFavourite(false);
            mPlayListDao.insert(new PlayListModel(0, "favourite"));
        }
        return null;
    }
}
