package com.taquangkhoi.keisic.myroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScrobbleDAO {

    @Query("SELECT * FROM Scrobble")
    List<Scrobble> getAll();

    @Query("SELECT * FROM Scrobble WHERE uid IN (:scrobbleIds)")
    List<Scrobble> loadAllByIds(int[] scrobbleIds);

    @Insert
    void insertAll(Scrobble... scrobbles);

    @Insert
    void insert(Scrobble scrobble);

    @Delete
    void delete(Scrobble scrobble);
}
