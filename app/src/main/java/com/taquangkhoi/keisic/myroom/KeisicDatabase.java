package com.taquangkhoi.keisic.myroom;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Song.class, Scrobble.class},
        version = 3,
        autoMigrations = {
                @AutoMigration(from = 2, to = 3)
        }
)
public abstract class KeisicDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "keisic.db";
    private static KeisicDatabase sInstance;


    public static synchronized KeisicDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            KeisicDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public abstract SongDAO songDao();
}
