package com.cj.musicoffline.room;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.SearchContent;

import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private MusicDao mMusicDao;
    private LiveData<List<AudioModel>> mAllMusic;

    private SearchDao mSearchDao;
    private LiveData<List<SearchContent>> mAllSearch;

    public MusicRepository(Context context) {
        MusicDatabase db = MusicDatabase.getDatabase(context);
        mMusicDao = db.musicDao();
        mAllMusic = mMusicDao.getAllMusics();

        mSearchDao = db.searchDao();
        mAllSearch = mSearchDao.getSearch();
    }

    public LiveData<List<AudioModel>> getAllMusic() {
        return mAllMusic;
    }

    public LiveData<List<AudioModel>> getMusicNew() {
        return mMusicDao.getNew();
    }

    public void insert(AudioModel word) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mMusicDao.insert(word);
        });
    }

    public LiveData<List<AudioModel>> getAllByID(String m) {
        return mMusicDao.getAllByID(m);
    }

    public LiveData<AudioModel> getByID(String m) {
        return mMusicDao.getByID(m);
    }

    //search
    public LiveData<List<SearchContent>> getAllSearch() {
        return mAllSearch;
    }

    public void insertSearch(SearchContent searchContent) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mSearchDao.insertSearch(searchContent);
        });
    }

    public void deleteAllSearch() {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mSearchDao.deleteAllSearch();
        });
    }

    public void deleteSearch(String url) {
        MusicDatabase.databaseWriteExecutor.execute(() -> {
            mSearchDao.deleteSearch(url);
        });
    }

    public LiveData<List<AudioModel>> getAlbum() {
        return mMusicDao.getAlbum();
    }

    public LiveData<List<AudioModel>> getAlbumDetail(int idAlbum) {
        return mMusicDao.getAlbumDetail(idAlbum);
    }
}
