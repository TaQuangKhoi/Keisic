package com.taquangkhoi.keisic.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.taquangkhoi.keisic.MainActivity;
import com.taquangkhoi.keisic.R;
import com.taquangkhoi.keisic.ScrobbleAdapter;
import com.taquangkhoi.keisic.databinding.FragmentHomeBinding;
import com.taquangkhoi.keisic.myroom.KeisicDatabase;
import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.services.MyListener;

import java.util.List;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements MyListener {

    private FragmentHomeBinding binding;

    private ListView listView;
    HomeViewModel homeViewModel;
    TextView textView;

    ScrobbleAdapter scrobbleAdapter;
    private NotificationReceiver nReceiver;

    private static final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Lấy ViewModel từ HomeViewModel
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        textView = binding.textHome;
        listView = binding.listView;
        scrobbleAdapter = new ScrobbleAdapter(getContext(), R.layout.layout_item_scrobble_list_view);

        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        addDataToScrobblesListView();

        addReceiver();

        return root;
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
        List<Scrobble> list = KeisicDatabase.getInstance(getContext()).scrobbleDao().getAll();
        for (Scrobble scrobble : list) {
            scrobbleAdapter.add(scrobble);
        }

        // Đặt adapter cho ListView
        listView.setAdapter(scrobbleAdapter);
    }

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("package");
            String songName = intent.getStringExtra("song-name");
            String artistName = intent.getStringExtra("artist");
            scrobbleAdapter.add(new Scrobble(songName, artistName));
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