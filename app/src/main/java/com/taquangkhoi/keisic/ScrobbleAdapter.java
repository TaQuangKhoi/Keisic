package com.taquangkhoi.keisic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taquangkhoi.keisic.myroom.Scrobble;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScrobbleAdapter extends ArrayAdapter<Scrobble> {
    private Context mContext;
    private int mResource;
    private final String TAG = "ScrobbleAdapter";

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
        tvTime.setText(getMinuteAndSecond(song.getListenTime()));

        return customView;
    }

    // Func to return time as minute and second
    private String getMinuteAndSecond(String time) {
        //turn String to time
        try {
            String oldPartten  = "yyyy-MM-dd HH:mm:ss.SSS";
            String newPartten = "dd MMM yyyy, HH:mm";
            SimpleDateFormat dateFormat = new SimpleDateFormat(newPartten);
            Date parsedDate = dateFormat.parse(time);

            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            long diff = currentTimestamp.getTime() - timestamp.getTime();
            Log.i(TAG, "getMinuteAndSecond: diff " + diff + " timestamp " + timestamp + " current " + currentTimestamp);

            //turn time to minute and second
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = (diff / (60 * 60 * 1000) % 24) - 7;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.i(TAG, "getMinuteAndSecond: diffDays " + diffDays + " diffHours " + diffHours + " diffMinutes " + diffMinutes + " diffSeconds " + diffSeconds);
            if (diffDays > 0) {
                return diffDays + " ngày trước";
            } else if (diffHours > 0) {
                return diffHours + " giờ trước";
            } else if (diffMinutes > 0) {
                return diffMinutes + " phút trước";
            } else if (diffSeconds > 0) {
                return diffSeconds + " giây trước";
            } else {
                return "Vừa xong";
            }
        } catch(Exception e) { //this generic but you can control another types of exception
            Log.i(TAG, "getMinuteAndSecond Error: " + e.getMessage());
            // look the origin of excption
        }
        return "Now Playing";
    }
}
