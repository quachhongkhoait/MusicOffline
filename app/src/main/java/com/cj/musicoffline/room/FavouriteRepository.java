package com.cj.musicoffline.room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.FavouriteModel;

import java.util.List;

public class FavouriteRepository {
    private FavouriteDao mFavouriteDao;

    public FavouriteRepository(Context context) {
        MusicDatabase db = MusicDatabase.getDatabase(context);
        mFavouriteDao = db.favouriteDao();
    }

    public LiveData<List<FavouriteModel>> getFavourite(int id) {
        return mFavouriteDao.getFavourite(id);
    }

    public LiveData<Integer> getCountFavourite(int idplaylist) {
        return mFavouriteDao.getCountFavourite(idplaylist);
    }

    public void insertFavourite(FavouriteModel favouriteModel) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mFavouriteDao.insertFavourite(favouriteModel);
        });
    }

    public void deleteFavourite(int s) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mFavouriteDao.deleteFavourite(s);
        });
    }
}
