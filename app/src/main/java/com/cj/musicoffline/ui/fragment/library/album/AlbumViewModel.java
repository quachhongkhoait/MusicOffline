package com.cj.musicoffline.ui.fragment.library.album;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.room.MusicRepository;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {
    //Songs
    private MusicRepository mRepositorySongs;
    private LiveData<List<AudioModel>> mAllMusic;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        mRepositorySongs = new MusicRepository(application);
        mAllMusic = mRepositorySongs.getAlbum();
    }

    public LiveData<List<AudioModel>> getAllMusic() {
        return mAllMusic;
    }

    public LiveData<List<AudioModel>> getAlbumDetail(int idAlbum) {
        return mRepositorySongs.getAlbumDetail(idAlbum);
    }
}
