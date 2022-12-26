package com.taquangkhoi.keisic.myroom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "scrobbles")
public class Scrobble {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "artist")
    private String artist;

    @ColumnInfo(name = "path")
    private String path;

    @ColumnInfo(name = "duration")
    private int duration;

    @ColumnInfo(name = "startTime")
    private String startTime;

    @ColumnInfo(name = "endTime")
    private String endTime;

    @Ignore
    private int imageId;

    public Scrobble(String name, String artist, String path, int duration, String startTime, String endTime) {
        this.name = name;
        this.artist = artist;
        this.path = path;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Scrobble() {
        this.name = "";
        this.artist = "";
        this.path = "";
        this.duration = 0;
        this.startTime = "";
        this.endTime = "";
    }


    public Scrobble(String name, String artist, String startTime) {
        this.name = name;
        this.artist = artist;
        this.startTime = startTime;
    }

    public Scrobble(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }

    public int getDuration() {
        return duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String toString() {
        return "Scrobble: " + this.name + " by " + this.artist + " from " + this.startTime + " to " + this.endTime;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
