package com.taquangkhoi.keisic.ui.home;

import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taquangkhoi.keisic.notification.Notification;
import com.taquangkhoi.keisic.notification.NotificationAdapter;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private ArrayList<Notification> notifications;
    public final MutableLiveData<Integer> count;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        count = new MutableLiveData<>();
        notifications = new ArrayList<>();


        notifications.add(new Notification("1", "1", "1"));
        notifications.add(new Notification("2", "2", "2"));
        mText.setValue("This is home fragment " + count.getValue());
        count.setValue(0);
    }

    public LiveData<String> getText() {
        mText.setValue("This is home fragment: " + count.getValue());
        return mText;
    }

    public void setText(String mText) {
        this.mText.setValue(mText);
    }

    public LiveData<Integer> getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count.setValue(count);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public ArrayList<String> getNotificationsString() {
        ArrayList<String> strings = new ArrayList<>();
        for (Notification notification : notifications) {
            strings.add(notification.getTitle());
        }
        return strings;
    }
}