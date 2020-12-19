package com.cj.musicoffline.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("SELECT * FROM audio WHERE url = :m")
    LiveData<List<AudioModel>> getAllByID(String m);

    @Query("DELETE FROM audio")
    void deleteAll();
}
