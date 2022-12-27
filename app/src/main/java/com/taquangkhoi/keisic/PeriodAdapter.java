package com.taquangkhoi.keisic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taquangkhoi.keisic.ui.data.ChartItemAdapter;

public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.ViewHolder> {
    private Context context;
    private static final String TAG = "PeriodAdapter";
    private String[] mPeriods = {"Overall", "7 days", "1 month", "3 months", "6 months", "1 year"};

    public PeriodAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvw_title_period);
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }
    }

    @NonNull
    @Override
    public PeriodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View heroView = inflater.inflate(R.layout.simple_list_item_button, parent, false);

        PeriodAdapter.ViewHolder viewHolder = new PeriodAdapter.ViewHolder(heroView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] periods = mPeriods;

        holder.title.setText(periods[position]);
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
