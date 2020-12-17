package com.cj.musicoffline.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AudioModel word);

    @Query("SELECT * FROM audio")
    LiveData<List<AudioModel>> getAllMusics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(FavouriteModel favouriteModel);

//    @Query("SELECT * FROM favourite WHERE idAlbum = idalbum")
//    LiveData<List<FavouriteModel>> getFavorite(String idalbum);
}
