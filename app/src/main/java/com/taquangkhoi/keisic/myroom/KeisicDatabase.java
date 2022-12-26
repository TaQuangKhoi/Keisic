package com.taquangkhoi.keisic.myroom;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.RenameColumn;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {Song.class, Scrobble.class},
        version = 7,
        autoMigrations = {
                @AutoMigration(
                        from = 1,
                        to = 2
                ),
                @AutoMigration(
                        from = 3,
                        to = 4,
                        spec = KeisicDatabase.Migration3To4.class
                ),
                @AutoMigration(from = 4, to = 5),
                @AutoMigration(from = 5, to = 6),
                @AutoMigration( // 26-Dec-2022 11:39 AM
                        from = 6,
                        to = 7,
                        spec = KeisicDatabase.Migration6To7.class
                )
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

    @RenameColumn(
            tableName = "scrobbles",
            fromColumnName = "startTime",
            toColumnName = "listen_time"
    )
    static class Migration6To7 implements AutoMigrationSpec {
    }
}
