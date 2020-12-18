package com.cj.musicoffline.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM favourite")
    LiveData<List<FavouriteModel>> getFavourite();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(FavouriteModel favouriteModel);

    @Query("DELETE FROM favourite WHERE id = :id")
    void deleteFavourite(int id);

    @Query("SELECT * FROM favourite WHERE idAlbum = :id")
    LiveData<List<FavouriteModel>> getAllFavourite(String id);
}
