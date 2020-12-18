package com.cj.musicoffline.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.room.FavouriteRepository;
import com.cj.musicoffline.room.MusicRepository;

import java.util.List;

public class ShareViewModel extends AndroidViewModel {
    private MusicRepository mRepositorySongs;
    private LiveData<List<AudioModel>> mAllMusic;

    private FavouriteRepository mRepositoryFavourite;
    private LiveData<List<FavouriteModel>> mAllFavourite;

    public ShareViewModel(@NonNull Application application) {
        super(application);
        mRepositorySongs = new MusicRepository(application);
        mAllMusic = mRepositorySongs.getAllMusic();

        mRepositoryFavourite = new FavouriteRepository(application);
        mAllFavourite = mRepositoryFavourite.getFavourite();
    }

    //Songs
    public LiveData<List<AudioModel>> getAllMusic() {
        return mAllMusic;
    }

    public void insertSong(AudioModel word) {
        mRepositorySongs.insert(word);
    }

    public LiveData<List<AudioModel>> getAllByID(String url) {
        return mRepositorySongs.getAllByID(url);
    }

    //Favourite
    public LiveData<List<FavouriteModel>> getFavourite() {
        return mAllFavourite;
    }

    public void insertFavourite(FavouriteModel favouriteModel) {
        mRepositoryFavourite.insertFavourite(favouriteModel);
    }

    public void deleteFavourite(int s) {
        mRepositoryFavourite.deleteFavourite(s);
    }

    public LiveData<List<FavouriteModel>> getAllFavourite(String album) {
        return mRepositoryFavourite.getAllFavourite(album);
    }
}
