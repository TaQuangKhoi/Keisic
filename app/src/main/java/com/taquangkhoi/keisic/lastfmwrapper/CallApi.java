package com.taquangkhoi.keisic.lastfmwrapper;

import static java.lang.Long.parseLong;

import android.os.Bundle;
import android.util.Log;

import com.taquangkhoi.keisic.ui.data.ChartItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
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
                    response[0] = runTest(UrlBuilder.buildUrlSearchTrack(songName, artist));
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

    public List<ChartItem> getRecentTrack() throws InterruptedException {
        List<ChartItem> chartItemList = new ArrayList<>();
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
                        Log.i(TAG, "getRecentTrack run: artist " + artistName + " song " + songName);
                        //String songUrl = track.getString("url");
                        //String songDuration = track.getString("playcount");
                        //String songImage = track.getJSONArray("image").getJSONObject(2).getString("#text");
                        //chartItemList.add(new ChartItem(0, songName, Long.parseLong(songDuration)));
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
}
