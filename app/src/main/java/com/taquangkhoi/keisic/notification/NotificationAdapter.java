package com.taquangkhoi.keisic.notification;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NotificationAdapter extends ArrayAdapter<MyNotification> {
    // khai báo thuộc tính để có thể truy cập mọi nơi trong class
    private Context context; // Trà về Activity mà ListView hiển thị - this
    private int resource; // loại layout cần được sử dụng

    // Viết lại Constructor
    // Là method cần được ghi đè khi kế thừa ArrayAdapter
    public NotificationAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    // ghi đè getVIew để trả về CustomItemView của ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(context).inflate(resource, null);

        // Ánh xạ thông qua customView (chưa có data)
//        ImageView imageView = customView.findViewById(R.id.ivBus);
//        TextView tvNameBus = customView.findViewById(R.id.tvNameBus);
//        TextView tvRouteBus = customView.findViewById(R.id.tvRouteBus);

//        Buses bus = getItem(position); // Lấy đối tượng

        // Lấy dữ liệu từ phần tử với position trong mảng đưa vào từng View
//        imageView.setImageResource(getItem(position).getIdImage());
//        tvNameBus.setText(getItem(position).getNameBus());
//        tvRouteBus.setText(getItem(position).getRouteBus());

        return customView;
    }
}
