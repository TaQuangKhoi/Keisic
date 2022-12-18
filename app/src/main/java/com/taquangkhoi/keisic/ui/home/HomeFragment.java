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

import com.taquangkhoi.keisic.databinding.FragmentHomeBinding;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ListView listView;
    HomeViewModel homeViewModel;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Lấy ViewModel từ HomeViewModel
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        textView = binding.textHome;
        listView = binding.listView;

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, homeViewModel.getNotificationsString());

        // Đặt adapter cho ListView
        listView.setAdapter(adapter);

        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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
}