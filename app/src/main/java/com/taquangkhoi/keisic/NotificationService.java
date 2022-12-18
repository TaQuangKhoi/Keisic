package com.taquangkhoi.keisic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.taquangkhoi.keisic.services.MyListener;
import com.taquangkhoi.keisic.ui.home.HomeViewModel;

public class NotificationService extends NotificationListenerService {
    Context context;
    HomeViewModel homeViewModel;
    private static final String TAG = "NotificationService";
    static MyListener myListener;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        homeViewModel = new HomeViewModel();
        Toast.makeText(this, "Notification Service Created", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onCreate Notification Service");
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
        Toast.makeText(context, "Notification Posted", Toast.LENGTH_LONG).show();
        super.onNotificationPosted(sbn);
        String pack = sbn.getPackageName();

        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        homeViewModel.setText(text);

        Log.i(TAG, "onNotificationPosted: " + pack + " " + title + " " + text);

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);

        sendBroadcast(msgrcv);

        if (myListener != null) {
            myListener.setValue(pack);
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
}
