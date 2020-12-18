package com.cj.musicoffline.ui.fragment.library.songs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.room.MusicRepository;

import java.util.List;

public class SongsViewModel extends AndroidViewModel {
    private MusicRepository mRepository;
    private LiveData<List<AudioModel>> mAllMusic;

    public SongsViewModel(@NonNull Application application) {
        super(application);
        mRepository =new MusicRepository(application);
        mAllMusic = mRepository.getAllMusic();
    }

    public LiveData<List<AudioModel>> getAllMusic() {
        return mAllMusic;
    }

    public void insert(AudioModel word) {
            mRepository.insert(word);
    }
}
