package com.taquangkhoi.keisic.lastfmwrapper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.taquangkhoi.keisic.BuildConfig;

public class UrlBuilder {
    private static final String TAG = "UrlBuilder";

    private static final String lastFmApiKey = BuildConfig.LAST_FM_API_KEY;

    public static String buildSearchTrack(String songName, @Nullable String artist) {
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
                .appendQueryParameter("username", "taquangkhoi")
                .appendQueryParameter("track", songName)
                .appendQueryParameter("artist", artist != null ? artist.split(", ")[0] : "")
                .appendQueryParameter("format", "json")
                .build().toString();

        Log.i(TAG, "buildUrlGetTrackInfo urlSearch: " + urlSearch);
        return urlSearch;
    }

    public static String buildGetRecentTracks(String page) {
        String urlSearch = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks")
                .buildUpon()
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("user", "taquangkhoi")
                .appendQueryParameter("page", page)
                .appendQueryParameter("format", "json")
                .build().toString();

        Log.i(TAG, "buildGetRecentTracks urlSearch: " + urlSearch);
        return urlSearch;
    }

    /**
     * @param username TaQuangKhoi
     * @param period 7day, 1month, 3month, 6month, 12month, overall
     * @return urlSearch
     */
    public static String buildGetTopTracks(String username, String period) {
        String urlSearch = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=user.gettoptracks")
                .buildUpon()
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("user", username)
                .appendQueryParameter("period", period)
                .appendQueryParameter("format", "json")
                .build().toString();

        Log.i(TAG, "buildGetTopTracks urlSearch: " + urlSearch);
        return urlSearch;
    }

    /**
     * @param artist name
     * @return
     */
    public static String buildGetArtistInfo(String artist, String username) {
        String urlSearch = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=artist.getinfo")
                .buildUpon()
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("user", username)
                .appendQueryParameter("artist", artist)
                .appendQueryParameter("format", "json")
                .build().toString();

        Log.i(TAG, "buildGetArtistInfo urlSearch: " + urlSearch);
        return urlSearch;
    }


    /**
     * @param username TaQuangKhoi
     * @param period 7day, 1month, 3month, 6month, 12month, overall
     * @return urlSearch
     */
    public static String buildGetTopArtists(String username, String period) {
        Log.i(TAG, "buildGetTopArtist: username is " + username + " period is " + period);
        String urlSearch = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=user.gettopartists")
                .buildUpon()
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("user", username)
                .appendQueryParameter("period", period)
                .appendQueryParameter("format", "json")
                .build()
                .toString();

        Log.i(TAG, "buildGetArtistTopTracks urlSearch: " + urlSearch);
        return urlSearch;
    }

    /**
     * @param
     */
    public static String buildGetTopAlbums(String username, String period) {
        Log.i(TAG, "buildGetTopAlbums: username is" + username + " period is " + period);
        String urlTopAlbums = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=user.gettopalbums")
                .buildUpon()
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("user", username)
                .appendQueryParameter("period", period)
                .appendQueryParameter("format", "json")
                .build()
                .toString();

        Log.i(TAG, "buildGetTopAlbums urlTopAlbums: " + urlTopAlbums);
        return urlTopAlbums;
    }

    public static String buildGetLovedTracks(String username) {
        Log.i(TAG, "buildGetLovedTracks: username is" + username);
        String urlLovedTracks = Uri.parse("https://ws.audioscrobbler.com/2.0/?method=user.getlovedtracks")
                .buildUpon()
                .appendQueryParameter("api_key", lastFmApiKey)
                .appendQueryParameter("user", username)
                .appendQueryParameter("format", "json")
                .build()
                .toString();

        Log.i(TAG, "buildGetLovedTracks urlLovedTracks: " + urlLovedTracks);
        return urlLovedTracks;
    }
}
