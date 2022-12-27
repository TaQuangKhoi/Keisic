package com.taquangkhoi.keisic.ui.data;

import androidx.annotation.Nullable;

public class ChartItem {
    private int imageId;
    private String imageUrl;
    private String title;
    private String artist;
    private long playcount;

    public ChartItem(@Nullable int image,String imageUrl, String title, long playcount) {
        if (image != 0) {
            this.imageId = image;
        }
        this.imageUrl = imageUrl;
        this.title = title;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
