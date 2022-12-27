package com.taquangkhoi.keisic.ui.data;

public class ChartItem {
    private int imageId;
    private String title;
    private String artist;
    private long playcount;

    public ChartItem(int image, String title, String artist, long playcount) {
        this.imageId = image;
        this.title = title;
        this.artist = artist;
        this.playcount = playcount;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getPlaycount() {
        return playcount;
    }

    public void setPlaycount(long playcount) {
        this.playcount = playcount;
    }
}
