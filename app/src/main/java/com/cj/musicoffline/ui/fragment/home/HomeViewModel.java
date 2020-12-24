package com.cj.musicoffline.ui.fragment.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.room.FavouriteRepository;
import com.cj.musicoffline.room.MusicRepository;
import com.cj.musicoffline.room.PlayListRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    //Songs
    private MusicRepository mRepositorySongs;
    private LiveData<List<AudioModel>> mAllMusic;
    //playlist
    private PlayListRepository mRepositoryPlayList;
    private LiveData<List<PlayListModel>> mAllPlayList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mRepositorySongs = new MusicRepository(application);
        mAllMusic = mRepositorySongs.getMusicNew();

        mRepositoryPlayList = new PlayListRepository(application);
        mAllPlayList = mRepositoryPlayList.getPlayList();
    }

    public LiveData<List<AudioModel>> getAllMusic() {
        return mAllMusic;
    }

    public LiveData<List<PlayListModel>> getPlayList() {
        return mAllPlayList;
    }
}
