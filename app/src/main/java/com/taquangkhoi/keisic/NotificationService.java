package com.taquangkhoi.keisic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.taquangkhoi.keisic.services.MyListener;
import com.taquangkhoi.keisic.ui.home.HomeViewModel;

import java.util.Date;

public class NotificationService extends NotificationListenerService {
    Context context;
    HomeViewModel homeViewModel;
    private static final String TAG = "NotificationService";
    static MyListener myListener;
    Song currentSong;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        homeViewModel = new HomeViewModel();
        Toast.makeText(this, "Notification Service Created", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onCreate Notification Service");
        currentSong = new Song();
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
//        extras.keySet().forEach(key -> {
//            Log.i(TAG, "onNotificationPosted: " + key + " : " + extras.get(key));
//        });

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
                currentSong.setName(extras.get("android.title").toString());
                currentSong.setArtist(extras.get("android.text").toString());
                currentSong.setStartTime(new Date());

                String text = extras.getCharSequence("android.text").toString();

                Intent msgrcv = new Intent("Msg");
                msgrcv.putExtra("package", pack);
                //msgrcv.putExtra("title", title);
                msgrcv.putExtra("text", text);

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


}
