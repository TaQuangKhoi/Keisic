package com.taquangkhoi.keisic.ui.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
        private TextView title;
        private TextView scrobbles;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvw_title_chart_item);
            scrobbles = itemView.findViewById(R.id.tvw_scrobble_chart_item);
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
