package com.taquangkhoi.keisic.myroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongDAO {

    @Query("SELECT * FROM songs")
    List<Song> getAll();

    @Query("SELECT * FROM songs WHERE uid IN (:songIds)")
    List<Song> loadAllByIds(int[] songIds);

    @Insert
    void insertAll(Song... songs);

    @Insert
    void insert(Song song);

    @Delete
    void delete(Song song);
}
