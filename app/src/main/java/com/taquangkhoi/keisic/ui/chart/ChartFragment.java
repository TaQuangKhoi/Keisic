package com.taquangkhoi.keisic.ui.chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taquangkhoi.keisic.PeriodAdapter;
import com.taquangkhoi.keisic.databinding.FragmentChartBinding;
import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.ui.data.ChartItemAdapter;

import java.util.ArrayList;

public class ChartFragment extends Fragment {

    private @NonNull FragmentChartBinding binding;
    RecyclerView rcvArtists, rcvSongs, rcvAlbums, rcvPeriod;
    TextView txtTotalArtists, txtTotalSongs, txtTotalAlbums;
    CallApi callApi;
    ChartViewModel chartViewModel;
    private static final String TAG = "ChartFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chartViewModel =
                new ViewModelProvider(this).get(ChartViewModel.class);

        binding = FragmentChartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rcvArtists = binding.rcvArtists;
        rcvAlbums = binding.rcvAlbums;
        rcvSongs = binding.rcvSongs;
        rcvPeriod = binding.rcvPeriod;

        txtTotalArtists = binding.tvwArtistTotal;
        txtTotalSongs = binding.tvwTrackTotal;
        txtTotalAlbums = binding.tvwAlbumTotal;

        callApi = new CallApi();

        addPeriodListView();

        addChartItemArtists();
        addChartItemTracks();
        addChartItemAlbums();

        return root;
    }

    public void addChartItemArtists() {


        try {
            chartViewModel.setChartItemList_Artists();
            Log.i(TAG, "onCreateView: setChartItemAdapter_Artists ");
        } catch (InterruptedException e) {
            Log.i(TAG, "onCreateView: setChartItemAdapter_Artists failed " + e.getMessage());
            e.printStackTrace();
        }

        chartViewModel.getChartItemList_Artists().observe(getViewLifecycleOwner(), chartItems -> {
            Log.i(TAG, "addChartItemArtists onChanged data: " + chartItems.size());
            ChartItemAdapter adapter = new ChartItemAdapter(getContext(), chartItems);
            rcvArtists.setAdapter(adapter);
            rcvArtists.setLayoutManager(
                    new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
            );
            Log.i(TAG, "addChartItemArtists onChanged end: " + adapter.getItemCount());
        });

        chartViewModel.getTotalTopArtists().observe(getViewLifecycleOwner(), integer -> {
            txtTotalArtists.setText(integer + " Artists");
        });
    }

    public void addChartItemTracks() {

        try {
            chartViewModel.setChartItemList_Tracks();
            Log.i(TAG, "onCreateView: setChartItemAdapter_Tracks");
        } catch (InterruptedException e) {
            Log.i(TAG, "onCreateView: setChartItemAdapter_Tracks failed " + e.getMessage());
            e.printStackTrace();
        }

        chartViewModel.getChartItemList_Tracks().observe(getViewLifecycleOwner(), chartItems -> {
            Log.i(TAG, "onChanged data: " + chartItems.size());
            ChartItemAdapter adapter = new ChartItemAdapter(getContext(), chartItems);
            rcvSongs.setAdapter(adapter);
            rcvSongs.setLayoutManager(
                    new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
            );
            Log.i(TAG, "onChanged: " + adapter.getItemCount());
        });

        chartViewModel.getTotalTopTracks().observe(getViewLifecycleOwner(), integer -> {
            txtTotalSongs.setText(integer + " Songs");
        });
    }

    public void addChartItemAlbums() {


        try {
            chartViewModel.setChartItemList_Albums();
            Log.i(TAG, "onCreateView: setChartItemAdapter_Albums");
        } catch (InterruptedException e) {
            Log.i(TAG, "onCreateView: setChartItemAdapter_Albums failed " + e.getMessage());
            e.printStackTrace();
        }

        chartViewModel.getChartItemList_Albums().observe(getViewLifecycleOwner(), chartItems -> {
            Log.i(TAG, "onChanged data: " + chartItems.size());
            ChartItemAdapter adapter = new ChartItemAdapter(getContext(), chartItems);
            rcvAlbums.setAdapter(adapter);
            rcvAlbums.setLayoutManager(
                    new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
            );
            Log.i(TAG, "onChanged: " + adapter.getItemCount());
        });

        chartViewModel.getTotalTopAlbums().observe(getViewLifecycleOwner(), integer -> {
            txtTotalAlbums.setText(integer + " Albums");
        });
    }

    public void addPeriodListView() {
        ArrayList<String> periods = new ArrayList<>();
        periods.add("Overall");
        periods.add("7 days");
        periods.add("1 month");
        periods.add("3 months");
        periods.add("6 months");
        periods.add("1 year");

        rcvPeriod.setAdapter(new PeriodAdapter(getContext(), chartViewModel));
        rcvPeriod.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}