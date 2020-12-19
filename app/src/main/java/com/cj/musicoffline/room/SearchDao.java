package com.cj.musicoffline.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cj.musicoffline.model.SearchContent;

import java.util.List;

@Dao
public interface SearchDao {
    @Query("SELECT * FROM search ORDER BY  time DESC")
    LiveData<List<SearchContent>> getSearch();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSearch(SearchContent searchContent);

    @Query("DELETE FROM search")
    void deleteAllSearch();

    @Query("DELETE FROM search WHERE url = :url")
    void deleteSearch(String url);
}
