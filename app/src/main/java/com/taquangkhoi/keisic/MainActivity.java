package com.taquangkhoi.keisic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_nghe_nhac;
    Button btn_xem_video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        Chay();
    }

    // Chạy các chức năng
    private void Chay() {
        btn_nghe_nhac.setOnClickListener(v -> {
            // Chuyển sang màn hình nghe nhạc
            Intent intent = new Intent(MainActivity.this, NgheNhacActivity.class);
            startActivity(intent);
        });
        btn_xem_video.setOnClickListener(v -> {
            // Chuyển sang màn hình xem video
            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
            startActivity(intent);
        });
    }

    // Ánh xạ các view
    private void AnhXa() {
        btn_nghe_nhac = findViewById(R.id.btn_nghe_nhac);
        btn_xem_video = findViewById(R.id.btn_xem_video);
    }
}