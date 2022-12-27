package com.taquangkhoi.keisic.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.taquangkhoi.keisic.databinding.FragmentChartBinding;
import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.ui.data.ChartItem;

import java.util.List;

public class ChartFragment extends Fragment {

    private @NonNull FragmentChartBinding binding;
    ListView lvArtists, lvSongs, lvAlbums;
    CallApi callApi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChartViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ChartViewModel.class);

        binding = FragmentChartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        lvAlbums = binding.lvAlbums;
        lvArtists = binding.lvArtists;
        lvSongs = binding.lvSongs;

        callApi = new CallApi();

        try {
            List<ChartItem> list= callApi.getRecentTrack();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //final TextView textView = binding.textSlideshow;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}