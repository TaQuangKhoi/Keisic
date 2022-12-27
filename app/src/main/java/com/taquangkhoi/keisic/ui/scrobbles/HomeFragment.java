package com.taquangkhoi.keisic.ui.scrobbles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taquangkhoi.keisic.R;
import com.taquangkhoi.keisic.ScrobbleAdapter;
import com.taquangkhoi.keisic.databinding.FragmentHomeBinding;
import com.taquangkhoi.keisic.lastfmwrapper.CallApi;
import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.services.MyListener;

import java.util.TimerTask;

public class HomeFragment extends Fragment implements MyListener {

    private FragmentHomeBinding binding;

    private ListView listView;
    HomeViewModel homeViewModel;

    private NotificationReceiver nReceiver;

    private static final String TAG = "HomeFragment";

    CallApi callApi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Lấy ViewModel từ HomeViewModel
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        listView = binding.listView;
        callApi = new CallApi();

        homeViewModel.setScrobbleAdapter(getContext());
        homeViewModel.getScrobbles().observe(getViewLifecycleOwner(), scrobbles -> {
            Log.i(TAG, "onChanged: " + scrobbles.size());
            ScrobbleAdapter scrobbleAdapter = new ScrobbleAdapter(getContext(), R.layout.layout_item_scrobble_list_view);
            scrobbleAdapter.addAll(scrobbles);
            listView.setAdapter(scrobbleAdapter);
        });
        //homeViewModel.getScrobbleAdapter().observe(getViewLifecycleOwner(), listView::setAdapter);

        addReceiver();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Scrobble scrobble = (Scrobble) parent.getItemAtPosition(position);
            Log.i(TAG, "onItemClick: " + scrobble.getName());
            showBottomSheetDialog(scrobble);
        });

        return root;
    }

    private void showBottomSheetDialog(Scrobble scrobble) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.scrobble_info);

        TextView textView = bottomSheetDialog.findViewById(R.id.tvw_title_test);

        bottomSheetDialog.show();
    }

    void Counter() {
        // loop each second
        // update the text view
        // if the count is 10, stop the loop

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void setValue(String packageName) {
        Log.d("HomeFragment", "setValue: " + packageName);
    }

    class MyTask extends TimerTask {
        public void run() {
//            homeViewModel.setCount(homeViewModel.getCount().getValue() + 1);
            Log.d("TEST", "Hello, Im timer, running iteration: "+ homeViewModel.getCount().toString());
        }
    }

    /**
     * Thêm dữ liệu vào ListView, sử dụng ScrobbleAdapter
     */
    public void addDataToScrobblesListView() {
        // Bắt đầu thêm data từ SQLite


        // Đặt adapter cho ListView
        //listView.setAdapter(scrobbleAdapter);
    }

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("package");
            String songName = intent.getStringExtra("song-name");
            String artistName = intent.getStringExtra("artist");
            Scrobble scrobble = new Scrobble(songName, artistName);

            homeViewModel.addScrobble(scrobble, getContext());
            homeViewModel.setText(songName + " - " + artistName);

            Log.i(TAG , "NotificationReceiver "+ "onReceive: " + temp + " : " + songName + " - " + artistName);
        }
    }

    private void addReceiver() {
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("Msg");
        getContext().registerReceiver(nReceiver, filter);
    }
}