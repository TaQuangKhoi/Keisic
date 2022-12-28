package com.taquangkhoi.keisic.ui.data;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.taquangkhoi.keisic.R;

public class ChartItemAdapter extends RecyclerView.Adapter<ChartItemAdapter.ViewHolder> {
    private Context context;
    private static final String TAG = "ChartItemAdapter";
    private ArrayList<ChartItem> mChartItems;

    public ChartItemAdapter(Context context, ArrayList<ChartItem> chartItems) {
        this.context = context;
        this.mChartItems = chartItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView title;
        private TextView scrobbles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvw_title_chart_item);
            scrobbles = itemView.findViewById(R.id.tvw_scrobble_chart_item);
            imgIcon = itemView.findViewById(R.id.img_chart_item);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getScrobbles() {
            return scrobbles;
        }

        public void setScrobbles(TextView scrobbles) {
            this.scrobbles = scrobbles;
        }

        public ImageView getImgIcon() {
            return imgIcon;
        }

        public void setImgIcon(ImageView imgIcon) {
            this.imgIcon = imgIcon;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View heroView = inflater.inflate(R.layout.custom_listview_artists, parent, false);

        ViewHolder viewHolder = new ViewHolder(heroView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChartItem item = mChartItems.get(position);

        Log.i(TAG, "onBindViewHolder: " + item.getImageUrl());
        if (item.getImageUrl() != null && !item.getImageUrl().equals("")) {
            Picasso.get().load(item.getImageUrl()).into(holder.getImgIcon());
        }
        holder.title.setText(item.getTitle());
        holder.scrobbles.setText(
                String.valueOf(item.getPlaycount())
        );
    }

    @Override
    public int getItemCount() {
        return mChartItems.size();
    }
}
