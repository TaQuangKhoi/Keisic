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
        TextView tvNameSong = customView.findViewById(R.id.tvw_title_item);
        TextView tvArtist = customView.findViewById(R.id.tvw_detail_item);
        TextView tvTime = customView.findViewById(R.id.tvw_time_item);

        Scrobble song = getItem(position); // Lấy đối tượng

        // Lấy dữ liệu từ phần tử với position trong mảng đưa vào từng View
        if (song.getImageId() != 0) {
            imageView.setImageResource(song.getImageId());
        }
        tvNameSong.setText(song.getName());
        tvArtist.setText(song.getArtist());
        tvTime.setText(song.getListenTime());

        return customView;
    }

    // Func to return time as minute and second
    private void getMinuteAndSecond(String time) {
        //turn String to time
        int timeInt = Integer.parseInt(time);
    }
}
