package com.taquangkhoi.keisic.ui.chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taquangkhoi.keisic.R;
import com.taquangkhoi.keisic.databinding.FragmentChartBinding;
import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.ui.data.ChartItem;
import com.taquangkhoi.keisic.ui.data.ChartItemAdapter;

import java.util.List;

public class ChartFragment extends Fragment {

    private @NonNull FragmentChartBinding binding;
    ListView lvArtists, lvSongs, lvAlbums;
    RecyclerView rcvArtists, rcvSongs, rcvAlbums;
    CallApi callApi;
    ChartViewModel chartViewModel;
    private static final String TAG = "ChartFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chartViewModel =
                new ViewModelProvider(this).get(ChartViewModel.class);

        binding = FragmentChartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        lvAlbums = binding.lvAlbums;
        rcvArtists = binding.rcvArtists;
        lvSongs = binding.lvSongs;

        callApi = new CallApi();

        try {
            chartViewModel.setChartItemAdapter();
            Log.i(TAG, "onCreateView: setChartItemAdapter");
        } catch (InterruptedException e) {
            Log.i(TAG, "onCreateView: setChartItemAdapter failed " + e.getMessage());
            e.printStackTrace();
        }
        chartViewModel.getChartItemList().observe(getViewLifecycleOwner(), chartItems -> {
            Log.i(TAG, "onChanged data: " + chartItems.size());
            ChartItemAdapter adapter = new ChartItemAdapter(getContext(), chartItems);
            rcvArtists.setAdapter(adapter);
            rcvArtists.setLayoutManager(
                    new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
            );
            Log.i(TAG, "onChanged: " + adapter.getItemCount());
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}