package com.taquangkhoi.keisic.myroom;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "songs")
public class Song {
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

    @Ignore
    private Date startTime;

    @ColumnInfo(name = "listen_time")
    private Long listenTime;

    public Long getListenTime() {
        return listenTime;
    }

    public void setListenTime(Long listenTime) {
        this.listenTime = listenTime;
    }

    public Song(String name, String artist, String path, int duration, Date startTime) {
        this.name = name;
        this.artist = artist;
        this.path = path;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Song() {
        this.name = "";
        this.artist = "";
        this.path = "";
        this.duration = 0;
        this.startTime = new Date();
    }

    public Song(String name, String artist) {
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

    public Date getStartTime() {
        return startTime;
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

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getFullInfor() {
        return "Song{" + "name=" + name + "," +
                "artist=" + artist + "," +
                "path=" + path + "," +
                "duration=" + duration + "," +
                "startTime=" + startTime + '}';
    }

    public boolean isEmpty() {
        return name.isEmpty() && artist.isEmpty() && path.isEmpty();
    }
}
