package com.taquangkhoi.keisic.ui.chart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.ui.data.ChartItem;

import java.util.ArrayList;
import java.util.List;

public class ChartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<ArrayList<ChartItem>> chartItemList;

    List<Scrobble> list;

    public ChartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setChartItemAdapter() {

    }
}