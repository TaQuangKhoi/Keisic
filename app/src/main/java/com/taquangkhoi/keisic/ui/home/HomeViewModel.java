package com.taquangkhoi.keisic.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taquangkhoi.keisic.notification.MyNotification;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private ArrayList<MyNotification> myNotifications;
    public final MutableLiveData<Integer> count;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        count = new MutableLiveData<>();
        myNotifications = new ArrayList<>();


        myNotifications.add(new MyNotification("1", "1", "1"));
        myNotifications.add(new MyNotification("2", "2", "2"));
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

    public ArrayList<MyNotification> getNotifications() {
        return myNotifications;
    }

    public ArrayList<String> getNotificationsString() {
        ArrayList<String> strings = new ArrayList<>();
        for (MyNotification myNotification : myNotifications) {
            strings.add(myNotification.getTitle());
        }
        return strings;
    }
}