package com.taquangkhoi.keisic;

import java.util.Date;

class Song {
    private String name;
    private String artist;
    private String path;
    private int duration;
    private Date startTime;

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
