package com.taquangkhoi.keisic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.taquangkhoi.keisic.ui.home.HomeViewModel;

public class NotificationService extends NotificationListenerService {
    Context context;
    HomeViewModel homeViewModel;
    private static final String TAG = "NotificationService";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        homeViewModel = new HomeViewModel();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        String pack = sbn.getPackageName();

        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        homeViewModel.setText(text);

        Log.i("Package", pack);
        Log.i("Title", title);
        Log.i("Text", text);

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);

        sendBroadcast(msgrcv);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.i("Msg", "Notification Removed");
    }
}
