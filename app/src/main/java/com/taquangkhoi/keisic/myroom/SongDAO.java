package com.taquangkhoi.keisic.myroom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongDAO {
    public static final String TABLE_NAME = "song";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_IS_SCROBBLED = "is_scrobbled";
    public static final String COLUMN_IS_LOVED = "is_loved";
    public static final String COLUMN_IS_BANNED = "is_banned";
    public static final String COLUMN_IS_SKIPPED = "is_skipped";
    public static final String COLUMN_IS_PLAYED = "is_played";
    public static final String COLUMN_IS_RECORDED = "is_recorded";
    public static final String COLUMN_IS_RECORDED_PLAYED = "is_recorded_played";
    public static final String COLUMN_IS_RECORDED_SKIPPED = "is_recorded_skipped";
    public static final String COLUMN_IS_RECORDED_BANNED = "is_recorded_banned";
    public static final String COLUMN_IS_RECORDED_LOVED = "is_recorded_loved";
    public static final String COLUMN_IS_RECORDED_SCROBBLED = "is_recorded_scrobbled";
    public static final String COLUMN_IS_RECORDED_PLAYED_SCROBBLED = "is_recorded_played_scrobbled";
    public static final String COLUMN_IS_RECORDED_PLAYED_LOVED = "is_recorded_played_loved";
    public static final String COLUMN_IS_RECORDED_PLAYED_BANNED = "is_recorded_played_banned";
    public static final String COLUMN_IS_RECORDED_PLAYED_SKIPPED = "is_recorded_played_skipped";
    public static final String COLUMN_IS_RECORDED_SKIPPED_SCROBBLED = "is_recorded_skipped_scrobbled";
    public static final String COLUMN_IS_RECORDED_SKIPPED_LOVED = "is_recorded_skipped_loved";
    public static final String COLUMN_IS_RECORDED_SKIPPED_BANNED = "is_recorded_skipped_banned";
    public static final String COLUMN_IS_RECORDED_BANNED_SCROBBLED = "is_record";

    @Query("SELECT * FROM song")
    List<Song> getAll();

    @Query("SELECT * FROM song WHERE uid IN (:songIds)")
    List<Song> loadAllByIds(int[] songIds);

    @Insert
    void insertAll(Song... songs);

    @Delete
    void delete(Song song);
}
