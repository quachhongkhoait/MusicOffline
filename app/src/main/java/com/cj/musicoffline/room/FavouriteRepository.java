package com.cj.musicoffline.room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.FavouriteModel;

import java.util.List;

public class FavouriteRepository {
    private FavouriteDao mFavouriteDao;
    private LiveData<List<FavouriteModel>> mAllFavourite;

    public FavouriteRepository(Context context) {
        MusicDatabase db = MusicDatabase.getDatabase(context);
        mFavouriteDao = db.favouriteDao();
    }

    public LiveData<List<FavouriteModel>> getFavourite() {
        return mAllFavourite;
    }

    public LiveData<List<FavouriteModel>> getAllFavourite(String idAlbum) {
        return mFavouriteDao.getAllFavourite(idAlbum);
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
