package com.taquangkhoi.keisic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.taquangkhoi.keisic.myroom.KeisicDatabase;
import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.myroom.Song;
import com.taquangkhoi.keisic.services.MyListener;
import com.taquangkhoi.keisic.ui.home.HomeViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import de.umass.lastfm.Caller;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationService extends NotificationListenerService {
    Context context;
    HomeViewModel homeViewModel;
    private static final String TAG = "NotificationService";
    static MyListener myListener;
    Song currentSong;
    private static final String userAgent = "Keisic";
    private static final String lastFmApiKey = BuildConfig.LAST_FM_API_KEY;
    private static final String lastFmApiSecret = BuildConfig.LAST_FM_SHARED_SECRET;
    String[] appToScrobbling;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    String key = lastFmApiKey;      // api key
    String secret = lastFmApiSecret;   // api secret
    String user = "TaQuangKhoi";     // user name
    String password = "..."; // user's password

    final OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        homeViewModel = new HomeViewModel();
        Log.i(TAG, "onCreate : Notification Service Created");

        currentSong = new Song();


        //Session session = Authenticator.getMobileSession(user, password, key, secret);
        //Playlist playlist = Playlist.create("example playlist", "description", session);


        // add String to array
        appToScrobbling = new String[3];

        // open sqlite db
        KeisicDatabase.getInstance(context);
    }


    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Toast.makeText(this, "Notification Service Connected", Toast.LENGTH_LONG).show();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        final String[] response = {null};

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    response[0] = runTest("http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=ad3fac03e78b01b78cf8b67f9d0f1ceb&artist=cher&track=believe&format=json");
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


        String pack = sbn.getPackageName();

        if (currentSong.isEmpty() == false) {
            Log.i(TAG, "Current Song is " + currentSong.getFullInfor());
        }

        Log.i(TAG, "Package name: " + pack);
        if (pack.equals("com.spotify.music") ||
                pack.equals("com.zing.mp3") ||
                pack.equals("ht.nct")

        ) {
            Bundle extras = sbn.getNotification().extras;

            // Kiểm tra xem có bao nhiêu key trong extras
//            extras.keySet().forEach(key -> {
//                Log.i(TAG, "onNotificationPosted: " + key + " : " + extras.get(key));
//            });

            Log.i(TAG, "android.title: " + extras.getCharSequence("android.title"));
            Log.i(TAG, "android.text: " + extras.getCharSequence("android.text"));
            String songNameExtras = extras.getCharSequence("android.title").toString();
            String artistExtras = extras.getCharSequence("android.text").toString();
            String currentName = currentSong.getName();
            String currentArtist = currentSong.getArtist();
            Log.i(TAG, "Current Title: " + currentSong.getName());
            Log.i(TAG, "Current Artist: " + currentSong.getArtist());

            if (currentName.isEmpty() != true
                    && currentArtist.isEmpty() != true
                    && currentName.equals(songNameExtras)
                    && currentArtist.equals(artistExtras)
            ) {
                Log.i(TAG, "onNotificationPosted: Song is the same | " + currentSong.getFullInfor());
                return;
            } else {
                Log.i(TAG, "onNotificationPosted: Song is different | " + currentSong.getFullInfor());
                // get current time and date as String
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String ts = sdf.format(timestamp);
                Log.i(TAG, "Current Time is:  " + ts);

                addSong(songNameExtras, artistExtras, ts);
                currentSong.setName(extras.get("android.title").toString());
                currentSong.setArtist(extras.get("android.text").toString());
                currentSong.setStartTime(new Date());


                Intent msgrcv = new Intent("Msg");
                msgrcv.putExtra("package", pack);
                //msgrcv.putExtra("title", title);
                msgrcv.putExtra("song-name", extras.getCharSequence("android.title").toString());
                msgrcv.putExtra("artist", extras.getCharSequence("android.text").toString());

                sendBroadcast(msgrcv);

                if (myListener != null) {
                    myListener.setValue(pack);
                }
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Toast.makeText(context, "Notification Removed", Toast.LENGTH_LONG).show();
        Log.i("Msg", "Notification Removed");
    }

    public void setListener(MyListener myListener) {
        NotificationService.myListener = myListener;
    }

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("command").equals("clearall")) {
                NotificationService.this.cancelAllNotifications();
            } else if (intent.getStringExtra("command").equals("list")) {
                Intent i1 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event", "=====================");
                sendBroadcast(i1);
                int i = 1;

                for (StatusBarNotification sbn : NotificationService.this.getActiveNotifications()) {
                    Intent i2 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event", i + " " + sbn.getPackageName() + "\n");
                    sendBroadcast(i2);
                    i++;
                }

                Intent i3 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event", "===== Notification List ====");
                sendBroadcast(i3);
            }
        }
    }

    private boolean check(String[] arr, String toCheckValue) {
        // check if the specified element
        // is present in the array or not
        // using Linear Search method
        boolean test = false;
        for (String element : arr) {
            if (element == toCheckValue) {
                test = true;
                break;
            }
        }

        return test;
    }

    private void addSong(String songName, String artist, String currentTime) {
        // add song to scrobbling list
        Scrobble song = new Scrobble(songName, artist, currentTime);
        KeisicDatabase.getInstance(context).scrobbleDao().insert(song);
        Log.i(TAG, "addSong: " + song.toString());
    }

    private String runTest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
