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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.myroom.KeisicDatabase;
import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.myroom.Song;
import com.taquangkhoi.keisic.services.MyListener;
import com.taquangkhoi.keisic.ui.scrobbles.HomeViewModel;

import java.sql.Timestamp;
import java.util.Date;

public class NotificationService extends NotificationListenerService {
    Context context;
    HomeViewModel homeViewModel;
    private static final String TAG = "NotificationService";
    static MyListener myListener;
    Song currentSong;
    private static final String userAgent = "Keisic";
    private static final String lastFmApiSecret = BuildConfig.LAST_FM_SHARED_SECRET;
    String[] appToScrobbling;

    CallApi callApi = new CallApi();


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("scrobbles");

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

        String pack = sbn.getPackageName();

        if (!currentSong.isEmpty()) {
            Log.i(TAG, "Current Song is " + currentSong.getFullInfor());
        }

        Log.i(TAG, "Package name: " + pack);
        if (pack.equals("com.spotify.music")) {
            Bundle extras = sbn.getNotification().extras;
            stopSelf();

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
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String ts = timestamp.toString();
                Log.i(TAG, "Current Time is:  " + ts);
                Bundle bundle = null;
                try {
                    bundle = callApi.getTrackInfo(songNameExtras, artistExtras);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addSongToDB(songNameExtras, artistExtras, ts, bundle.getString("song-url"));
                addSongToFirbase(songNameExtras, artistExtras, ts, bundle.getString("song-url"));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
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

    private void addSongToDB(String songName, String artist, String currentTime, String url) {
        // add song to scrobbling list
        Scrobble song = new Scrobble(songName, artist, currentTime, url);
        KeisicDatabase.getInstance(context).scrobbleDao().insert(song);
        Log.i(TAG, "addSongToDB: " + song);
    }

    private void addSongToFirbase(String songName, String artist, String currentTime, String url) {
        // add song to scrobbling list
        Scrobble song = new Scrobble(songName, artist, currentTime, url);
        myRef.push().setValue(song);
        Log.i(TAG, "addSongToFirbase: " + song);
    }

}
