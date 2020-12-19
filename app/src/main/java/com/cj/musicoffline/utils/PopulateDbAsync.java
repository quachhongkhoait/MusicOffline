package com.cj.musicoffline.utils;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.room.FavouriteDao;
import com.cj.musicoffline.room.MusicDatabase;
import com.cj.musicoffline.room.PlayListDao;

import java.util.ArrayList;
import java.util.List;


public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
    private final PlayListDao mPlayListDao;
    private final FavouriteDao mFavouriteDao;

    public PopulateDbAsync(MusicDatabase db) {
        this.mPlayListDao = db.playlistDao();
        this.mFavouriteDao = db.favouriteDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (SessionManager.getInstance().getKeyInsertFavourite()) {
            SessionManager.getInstance().setKeyInsertFavourite(false);
            mPlayListDao.insert(new PlayListModel(0, "favourite", 0));
        }

        return null;
    }
}
