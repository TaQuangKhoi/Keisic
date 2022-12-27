package com.taquangkhoi.keisic.ui.chart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.ui.data.ChartItem;

import java.util.ArrayList;
import java.util.List;

public class ChartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<ArrayList<ChartItem>> chartItemList_Artists;
    public MutableLiveData<ArrayList<ChartItem>> chartItemList_Tracks;
    public MutableLiveData<ArrayList<ChartItem>> chartItemList_Albums;
    private static final String TAG = "ChartViewModel";

    List<ChartItem> listArtists;
    List<ChartItem> listTracks;
    List<ChartItem> listAlbums;

    CallApi callApi;

    private MutableLiveData<String> period;

    public ChartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");

        period = new MutableLiveData<>();
        period.setValue("7day");

        chartItemList_Artists = new MutableLiveData<>();
        chartItemList_Albums = new MutableLiveData<>();
        chartItemList_Tracks = new MutableLiveData<>();

        listArtists = new ArrayList<>();
        listTracks = new ArrayList<>();
        listAlbums = new ArrayList<>();

        callApi = new CallApi();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setChartItemAdapter_Artists() throws InterruptedException {
        Log.i(TAG, "setChartItemAdapter: ");
        listArtists = callApi.getTopArtist(period.getValue());
        ArrayList<ChartItem> chartItems = new ArrayList<>();
        for (ChartItem chartItem : listArtists) {
            chartItems.add(chartItem);
        }
        this.chartItemList_Artists.setValue(chartItems);
        Log.i(TAG, "setChartItemAdapter: " + listArtists.size());
    }

    public LiveData<ArrayList<ChartItem>> getChartItemList_Artists() {
        Log.i(TAG, "getChartItemList: " + chartItemList_Artists.getValue().size());
        return chartItemList_Artists;
    }

    public void setChartItemAdapter_Tracks() throws InterruptedException {
        Log.i(TAG, "setChartItemAdapter_Tracks: start ");
        listTracks = callApi.getTopTracks(period.getValue());
        ArrayList<ChartItem> chartItems = new ArrayList<>();
        for (ChartItem chartItem : listTracks) {
            chartItems.add(chartItem);
        }
        this.chartItemList_Tracks.setValue(chartItems);
        Log.i(TAG, "setChartItemAdapter: " + listArtists.size());
    }

    public LiveData<ArrayList<ChartItem>> getChartItemList_Tracks() {
        Log.i(TAG, "getChartItemList: " + chartItemList_Tracks.getValue().size());
        return chartItemList_Tracks;
    }

    public void setChartItemAdapter_Albums() throws InterruptedException {
        Log.i(TAG, "setChartItemAdapter_Albums");
    }

    public void setChartItemList_Albums() throws InterruptedException {
        Log.i(TAG, "setChartItemList_Albums: start");
        listAlbums = callApi.getTopAlbums(period.getValue());
        ArrayList<ChartItem> chartItems = new ArrayList<>();
        for (ChartItem chartItem : listAlbums) {
            chartItems.add(chartItem);
        }
        this.chartItemList_Albums.setValue(chartItems);
        Log.i(TAG, "setChartItemList_Albums: " + listAlbums.size());
    }

    public LiveData<ArrayList<ChartItem>> getChartItemList_Albums() {
        Log.i(TAG, "getChartItemList: " + chartItemList_Albums.getValue().size());
        return chartItemList_Albums;
    }

    public LiveData<String> getPeriod() {
        return period;
    }

    public String getPeriodValue() {
        return period.getValue();
    }

    public void setPeriod(String period) {
        this.period.setValue(period);
    }
}