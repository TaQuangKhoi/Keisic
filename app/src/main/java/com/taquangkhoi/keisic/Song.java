package com.taquangkhoi.keisic;

public class Song {
    private String name;
    private String artist;
    private String path;
    private int duration;

    public Song(String name, String artist, String path, int duration) {
        this.name = name;
        this.artist = artist;
        this.path = path;
        this.duration = duration;
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
}
