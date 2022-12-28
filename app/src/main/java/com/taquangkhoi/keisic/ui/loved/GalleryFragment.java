package com.taquangkhoi.keisic.ui.loved;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.taquangkhoi.keisic.R;
import com.taquangkhoi.keisic.ScrobbleAdapter;
import com.taquangkhoi.keisic.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";

    private FragmentGalleryBinding binding;
    ListView listView;
    GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.listView;

        galleryViewModel.setScrobblesLovedList();

        galleryViewModel.scrobblesLovedList.observe(getViewLifecycleOwner(), scrobbles -> {
            Log.i(TAG, "observe: " + scrobbles.size());

            ScrobbleAdapter scrobbleAdapter = new ScrobbleAdapter(getContext(), R.layout.custom_listview_favorite);
            scrobbleAdapter.addAll(scrobbles);

            listView.setAdapter(scrobbleAdapter);
        });

        //final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}