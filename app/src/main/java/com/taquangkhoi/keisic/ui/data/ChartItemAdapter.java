package com.taquangkhoi.keisic.ui.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taquangkhoi.keisic.R;
import com.taquangkhoi.keisic.myroom.Scrobble;

import de.umass.lastfm.Chart;

public class ChartItemAdapter extends ArrayAdapter<ChartItem> {
    private Context mContext;
    private int mResource;
    private static final String TAG = "ChartItemAdapter";

    public ChartItemAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(mContext).inflate(mResource, null);

        // Ánh xạ thông qua customView (chưa có data)
        ImageView imageView = customView.findViewById(R.id.img_example);
        TextView tvNameSong = customView.findViewById(R.id.tvw_title_chart_item);
        TextView tvScrobble = customView.findViewById(R.id.tvw_scrobble_chart_item);

        ChartItem item = getItem(position); // Lấy đối tượng

        // Lấy dữ liệu từ phần tử với position trong mảng đưa vào từng View
        if (item.getImageId() != 0) {
            imageView.setImageResource(item.getImageId());
        }
        tvNameSong.setText(item.getTitle());
        tvScrobble.setText(String.valueOf(item.getPlaycount()));

        return customView;
    }
}
