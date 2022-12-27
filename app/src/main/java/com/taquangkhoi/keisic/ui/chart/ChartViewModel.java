package com.taquangkhoi.keisic.ui.chart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.ui.data.ChartItem;

import java.util.ArrayList;
import java.util.List;

import de.umass.lastfm.Chart;

public class ChartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<ArrayList<ChartItem>> chartItemList;
    private static final String TAG = "ChartViewModel";

    List<ChartItem> list;
    CallApi callApi;

    public ChartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        chartItemList = new MutableLiveData<>();
        list = new ArrayList<>();

        callApi = new CallApi();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setChartItemAdapter() throws InterruptedException {
        Log.i(TAG, "setChartItemAdapter: ");
        list = callApi.getTopArtist();
        ArrayList<ChartItem> chartItems = new ArrayList<>();
        for (ChartItem chartItem : list) {
            chartItems.add(chartItem);
        }
        this.chartItemList.setValue(chartItems);
        Log.i(TAG, "setChartItemAdapter: " + list.size());
    }

    public LiveData<ArrayList<ChartItem>> getChartItemList() {
        Log.i(TAG, "getChartItemList: " + chartItemList.getValue().size());
        return chartItemList;
    }
}