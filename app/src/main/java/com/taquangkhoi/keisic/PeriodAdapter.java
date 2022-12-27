package com.taquangkhoi.keisic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taquangkhoi.keisic.ui.chart.ChartViewModel;

public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.ViewHolder> {
    private Context context;
    private static final String TAG = "PeriodAdapter";
    private String[] mPeriodsKey = {"overall", "7day", "1month", "3month", "6month", "12month"};
    private String[] mPeriodsValue = {"Overall", "7 days", "1 month", "3 months", "6 months", "12 months"};
    private ChartViewModel chartViewModel;

    public PeriodAdapter(Context context, ChartViewModel chartViewModel) {
        this.context = context;
        this.chartViewModel = chartViewModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private TextView title;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvw_title_period);
            itemView.setOnClickListener(this);
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        //Tạo setter cho biến itemClickListenenr
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            try {
                itemClickListener.onClick(v, getAdapterPosition(), false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            try {
                itemClickListener.onClick(v, getAdapterPosition(), true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
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
        String[] periodsKey = mPeriodsKey;
        String[] periodsValue = mPeriodsValue;

        holder.title.setText(periodsValue[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) throws InterruptedException {
                if (isLongClick) {
                    //Do nothing
                    Toast.makeText(context, "Long click", Toast.LENGTH_SHORT).show();
                } else {
                    chartViewModel.setPeriod(periodsKey[position]);
                    chartViewModel.setChartItemList_Albums();
                    chartViewModel.setChartItemList_Artists();
                    chartViewModel.setChartItemList_Tracks();
                    Toast.makeText(context, chartViewModel.getPeriod().getValue(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
