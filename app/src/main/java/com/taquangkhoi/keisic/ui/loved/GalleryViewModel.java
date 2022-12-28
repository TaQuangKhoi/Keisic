package com.taquangkhoi.keisic.ui.loved;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.myroom.Scrobble;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewModel extends ViewModel {

    private static final String TAG = "GalleryViewModel";
    CallApi callApi;

    private final MutableLiveData<String> mText;
    public MutableLiveData<ArrayList<Scrobble>> scrobblesLovedList;
    List<Scrobble> list;

    public GalleryViewModel() {
        callApi = new CallApi();

        mText = new MutableLiveData<>();
        scrobblesLovedList = new MutableLiveData<>();

        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setScrobblesLovedList() {
        Log.i(TAG, "setScrobblesLovedList ");

        list = callApi.getLovedTrack();

        // turn List to ArrayList
        ArrayList<Scrobble> arrayList = new ArrayList<Scrobble>(list);
        arrayList.addAll(list);

        this.scrobblesLovedList.setValue(arrayList);
    }
}