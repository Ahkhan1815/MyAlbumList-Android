package com.example.applicationb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Album album);

    @Delete
    void delete(Album album);

    @Update
    void update(Album album);

    @Query("SELECT * FROM albumTable ORDER BY title ASC")
    LiveData<List<Album>> getAlbums();

    
    @Query("SELECT * FROM albumTable WHERE _id = :id LIMIT 1")
    LiveData<Album> getAlbumByID(String id);

    @Query("DELETE FROM albumTable WHERE _id = :id")
    void deleteByID(String id);


}
