package com.taquangkhoi.keisic.myroom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {Song.class, Scrobble.class},
        version = 4,
        autoMigrations = {
                @AutoMigration(from = 3, to = 4, spec = KeisicDatabase.Migration3To4.class)
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

    public abstract ScrobbleDAO scrobbleDao();

    @DeleteColumn(
            tableName = "songs",
            columnName = "listen_time"
    )
    static class Migration3To4 implements AutoMigrationSpec {
    }
}
