package com.taquangkhoi.keisic.ui.scrobbles;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.myroom.KeisicDatabase;
import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.notification.MyNotification;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private ArrayList<MyNotification> myNotifications;
    public final MutableLiveData<Integer> count;
    public MutableLiveData<ArrayList<Scrobble>> scrobblesList;

    public static final String TAG = "HomeViewModel";

    List<Scrobble> list;

    CallApi callApi;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        count = new MutableLiveData<>();
        myNotifications = new ArrayList<>();
        scrobblesList = new MutableLiveData<>();
        callApi = new CallApi();

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

    public void setScrobbleAdapter(Context context) throws InterruptedException {
        Log.i(TAG, "setScrobbleAdapter: ");
        list = callApi.getRecentTrack();
        // turn List to ArrayList
        ArrayList<Scrobble> arrayList = new ArrayList<Scrobble>(list);
        arrayList.addAll(list);

        Log.i(TAG, "setScrobbleAdapter: " + list.size());

        this.scrobblesList.setValue(arrayList);
    }

    public void addScrobble(Scrobble scrobble, Context context) throws InterruptedException {
        Log.i(TAG, "addScrobble: " + scrobble.getName() + " " + scrobble.getArtist());
        list = callApi.getRecentTrack();
        ArrayList<Scrobble> arrayList = new ArrayList<>(list);

        this.scrobblesList.setValue(arrayList);
    }

    public LiveData<ArrayList<Scrobble>> getScrobblesList() {
        return scrobblesList;
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

    public LiveData<ArrayList<Scrobble>> getScrobbles() {
        return scrobblesList;
    }

    public List<Scrobble> getSongsFromDB(Context context) {
        return KeisicDatabase.getInstance(context).scrobbleDao().getAll();
    }
}