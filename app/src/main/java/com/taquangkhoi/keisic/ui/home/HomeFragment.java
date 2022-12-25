package com.taquangkhoi.keisic.ui.home;

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

import com.taquangkhoi.keisic.R;
import com.taquangkhoi.keisic.ScrobbleAdapter;
import com.taquangkhoi.keisic.databinding.FragmentHomeBinding;
import com.taquangkhoi.keisic.myroom.KeisicDatabase;
import com.taquangkhoi.keisic.myroom.Scrobble;

import java.util.List;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ListView listView;
    HomeViewModel homeViewModel;
    TextView textView;

    ScrobbleAdapter scrobbleAdapter;

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
}