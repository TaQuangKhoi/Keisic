package com.taquangkhoi.keisic.lastfmwrapper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.taquangkhoi.keisic.BuildConfig;

public class UrlBuilder {
    private static final String TAG = "UrlBuilder";

    private static final String lastFmApiKey = BuildConfig.LAST_FM_API_KEY;

    public static String buildUrlSearchTrack(String songName, @Nullable String artist) {
        Log.i(TAG, "buildUrlSearchTrack: songInfo is " + songName + " - " + artist);
        // turn string to url

        String urlSearch = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=track.search")
                .buildUpon()
                .appendQueryParameter("track", songName)
                .appendQueryParameter("artist", artist != null ? artist.split(", ")[0] : "")
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("format", "json")
                .build().toString();

        Log.i(TAG, "buildUrlSearchTrack urlSearch: " + urlSearch);
        return urlSearch;
    }

    public static String buildUrlGetTrackInfo(String songName, @Nullable String artist) {
        Log.i(TAG, "buildUrlGetTrackInfo: songInfo is " + songName + " - " + artist);
        // turn string to url

        String urlSearch = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=track.getInfo")
                .buildUpon()
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("track", songName)
                .appendQueryParameter("artist", artist != null ? artist.split(", ")[0] : "")
                .appendQueryParameter("format", "json")
                .build().toString();

        Log.i(TAG, "buildUrlGetTrackInfo urlSearch: " + urlSearch);
        return urlSearch;
    }
}
