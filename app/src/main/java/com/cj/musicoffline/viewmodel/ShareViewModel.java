package com.cj.musicoffline.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.room.FavouriteRepository;
import com.cj.musicoffline.room.MusicRepository;
import com.cj.musicoffline.room.PlayListDao;
import com.cj.musicoffline.room.PlayListRepository;

import java.util.List;

public class ShareViewModel extends AndroidViewModel {
    //Songs
    private MusicRepository mRepositorySongs;
    private LiveData<List<AudioModel>> mAllMusic;
    //favourite
    private FavouriteRepository mRepositoryFavourite;
    private LiveData<List<FavouriteModel>> mAllFavourite;
    //playlist
    private PlayListRepository mRepositoryPlayList;
    private LiveData<List<PlayListModel>> mAllPlayList;

    public ShareViewModel(@NonNull Application application) {
        super(application);
        mRepositorySongs = new MusicRepository(application);
        mAllMusic = mRepositorySongs.getAllMusic();

        mRepositoryFavourite = new FavouriteRepository(application);

        mRepositoryPlayList = new PlayListRepository(application);
        mAllPlayList = mRepositoryPlayList.getPlayList();
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

    //start favourite
    public LiveData<List<FavouriteModel>> getFavourite(int id) {
        return mRepositoryFavourite.getFavourite(id);
    }

    public void insertFavourite(FavouriteModel favouriteModel) {
        mRepositoryFavourite.insertFavourite(favouriteModel);
    }

    public void deleteFavourite(int s) {
        mRepositoryFavourite.deleteFavourite(s);
    }

    public LiveData<Integer> getCountFavourite(int idplaylist) {
        return mRepositoryFavourite.getCountFavourite(idplaylist);
    }
    //end favourite

    //playlist
    public LiveData<List<PlayListModel>> getPlayList() {
        return mAllPlayList;
    }

    public void insertPlayList(PlayListModel playListModel) {
        mRepositoryPlayList.insertPlayList(playListModel);
    }

    public void deletePlayList(int s) {
        mRepositoryPlayList.deletePlayList(s);
    }

//    public LiveData<List<PlayListModel>> getAllPlayList() {
//        return mAllPlayList;
//    }

}
