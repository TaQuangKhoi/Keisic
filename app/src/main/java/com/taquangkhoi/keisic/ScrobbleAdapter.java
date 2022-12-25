package com.taquangkhoi.keisic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taquangkhoi.keisic.myroom.Scrobble;
import com.taquangkhoi.keisic.myroom.Song;

public class ScrobbleAdapter extends ArrayAdapter<Scrobble> {
    private Context mContext;
    private int mResource;

    public ScrobbleAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(mContext).inflate(mResource, null);

        // Ánh xạ thông qua customView (chưa có data)
        ImageView imageView = customView.findViewById(R.id.ivwSongArt);
        TextView tvNameBus = customView.findViewById(R.id.tvw_title_item);
        TextView tvRouteBus = customView.findViewById(R.id.tvw_detail_item);

        Scrobble song = getItem(position); // Lấy đối tượng

        // Lấy dữ liệu từ phần tử với position trong mảng đưa vào từng View
        imageView.setImageResource(song.getImageId());
        tvNameBus.setText(song.getName());
        tvRouteBus.setText(song.getArtist());

        return customView;
    }
}
