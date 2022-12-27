package com.taquangkhoi.keisic.lastfmwrapper;

import static java.lang.Long.parseLong;

import android.os.Bundle;
import android.util.Log;

import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.ui.data.ChartItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CallApi {
    private static final String TAG = "CallApi";
    final OkHttpClient client = new OkHttpClient();

    private String runTest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void searchTrack(String songName, String artist) {
        final String[] response = {null};
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    response[0] = runTest(UrlBuilder.buildSearchTrack(songName, artist));
                    // parse json
                    JSONObject obj = new JSONObject(response[0]);
                    Log.i(TAG, "run: " + obj.toString());
                    String songName = obj.getJSONObject("track").getString("name");
                    String songDuration = obj.getJSONObject("track").getString("duration");
                    Log.i(TAG, "run: song anme " + songName);
                    Log.i(TAG, "run: song duration " + songDuration);
                    Log.i(TAG, "onNotificationPosted test Okhttp: " + response[0]);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public Bundle getTrackInfo(String songName, String artist) throws InterruptedException {
        final String[] response = {null};
        Bundle bundle = new Bundle();
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    response[0] = runTest(UrlBuilder.buildUrlGetTrackInfo(songName, artist));
                    // parse json
                    JSONObject obj = new JSONObject(response[0]);
                    Log.i(TAG, "getTrackInfo run: json " + obj.toString());

                    String songName = obj.getJSONObject("track").getString("name");
                    bundle.putString("song-name", songName);

                    String songDuration = obj.getJSONObject("track").getString("duration");
                    bundle.putString("song-duration", songDuration);

                    String songUrl = obj.getJSONObject("track").getString("url");
                    bundle.putString("song-url", songUrl);

                    String songPlaycount = obj.getJSONObject("track").getString("playcount");
                    bundle.putString("song-playcount", songPlaycount);

                    String songListeners = obj.getJSONObject("track").getString("listeners");
                    bundle.putString("song-listeners", songListeners);

                    String userPlaycount = obj.getJSONObject("track").getString("userplaycount");
                    bundle.putString("user-playcount", userPlaycount);

                    Log.i(TAG, "getTrackInfo run: bundle " + bundle.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
        Log.i(TAG, "getTrackInfo after thread: bundle " + bundle.toString());
        return bundle;
    }

    public List<Scrobble> getRecentTrack() throws InterruptedException {
        Log.i(TAG, "getRecentTrack: start");
        List<Scrobble> chartItemList = new ArrayList<>();
        final String[] response = {null};

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    response[0] = runTest(UrlBuilder.buildGetRecentTracks());
                    // parse json
                    JSONObject obj = new JSONObject(response[0]);

                    JSONArray trackArray = obj.getJSONObject("recenttracks").getJSONArray("track");
                    for (int i = 0; i < trackArray.length(); i++) {
                        JSONObject track = trackArray.getJSONObject(i);
                        Log.i(TAG, "getRecentTrack run: track " + track.toString());

                        String songName = track.getString("name");
                        String artistName = track.getJSONObject("artist").getString("#text");

                        String listenTime = "";
                        track.isNull("date");

                        if (track.isNull("date")) {
                            Log.i(TAG, "getRecentTrack run: now playing " + songName + " " + artistName);
                            Log.i(TAG, "getRecentTrack run: nowplaying " + track.getJSONObject("@attr").getString("nowplaying"));
                            listenTime = "now playing";
                        } else {
                            Log.i(TAG, "getRecentTrack run: not playing " + songName + " " + artistName);
                            listenTime = track.getJSONObject("date").getString("#text");
                        }
                        Log.i(TAG, "getRecentTrack run: listen time " + listenTime);

                        Log.i(TAG, "getRecentTrack run: artist " + artistName + " song " + songName);

                        chartItemList.add(new Scrobble(songName, artistName, listenTime));
                    }
                    Log.i(TAG, "getRecentTrack run: chartItemList " + chartItemList.size());
                    Log.i(TAG, "getTrackInfo run: json " + obj.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();

        return chartItemList;
    }

    public Bundle getArtistInfo(String artistName) throws InterruptedException {
        final String[] response = {null};
        Bundle bundle = new Bundle();
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    response[0] = runTest(UrlBuilder.buildGetArtistInfo(artistName, "TaQuangKhoi"));
                    // parse json
                    JSONObject obj = new JSONObject(response[0]);
                    Log.i(TAG, "getArtistInfo run: json " + obj.toString());

                    String artistName = obj.getJSONObject("artist").getString("name");
                    bundle.putString("artist-name", artistName);

                    String artistUrl = obj.getJSONObject("artist").getString("url");
                    bundle.putString("artist-url", artistUrl);

                    String artistPlaycount = obj.getJSONObject("artist").getJSONObject("stats").getString("playcount");
                    bundle.putString("artist-playcount", artistPlaycount);

                    String artistListeners = obj.getJSONObject("artist").getJSONObject("stats").getString("listeners");
                    bundle.putString("artist-listeners", artistListeners);

                    String artistImage = obj.getJSONObject("artist").getJSONArray("image").getJSONObject(2).getString("#text");
                    bundle.putString("artist-image", artistImage);

                    String artistUserPlaycount = obj.getJSONObject("artist").getJSONObject("stats").getString("userplaycount");
                    bundle.putString("artist-userplaycount", artistUserPlaycount);

                    Log.i(TAG, "getArtistInfo run: bundle " + bundle.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
        Log.i(TAG, "getArtistInfo after thread: bundle " + bundle);
        return bundle;
    }

    public List<ChartItem> getTopArtist() throws InterruptedException {
        Log.i(TAG, "getTopArtist: start");
        List<ChartItem> chartItemList = new ArrayList<>();
        final String[] response = {null};

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    response[0] = runTest(UrlBuilder.buildGetTopArtist("TaQuangKhoi", "7day"));
                    // parse json
                    JSONObject obj = new JSONObject(response[0]);
                    Log.i(TAG, "getTopArtist run: json " + obj);

                    JSONArray trackArray = obj.getJSONObject("topartists").getJSONArray("artist");
                    for (int i = 0; i < trackArray.length(); i++) {
                        JSONObject track = trackArray.getJSONObject(i);
                        Log.i(TAG, "getTopArtist run: track " + track.toString());

                        String artistName = track.getString("name");
                        String artistPlaycount = track.getString("playcount");

                        Log.i(TAG, "getTopArtist run: artist " + artistName + " playcount " + artistPlaycount);

                        chartItemList.add(new ChartItem(0, artistName, Long.parseLong(artistPlaycount)));
                    }
                    Log.i(TAG, "getTopArtist run: chartItemList " + chartItemList.size());
                    Log.i(TAG, "getTopArtist run: json " + obj.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();

        return chartItemList;
    }
}
