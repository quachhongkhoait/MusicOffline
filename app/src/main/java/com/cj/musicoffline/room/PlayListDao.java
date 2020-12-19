package com.cj.musicoffline.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.PlayListModel;

import java.util.List;

@Dao
public interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlayListModel playListModel);

//    @Query("SELECT * FROM playlist")
//    LiveData<List<PlayListModel>> getAllPlayList();

    @Query("SELECT * FROM playlist WHERE id > 1")
    LiveData<List<PlayListModel>> getPlayList();

    @Query("SELECT id FROM playlist WHERE nameList = :m")
    LiveData<List<Integer>> getID(String m);

    @Query("DELETE FROM playlist WHERE id = :id")
    void deleteByID(int id);

    //update
    @Query("UPDATE playlist SET count = :count WHERE id = :id")
    void updatePlayList(int id, int count);

}
