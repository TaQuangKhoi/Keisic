package com.taquangkhoi.keisic;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    // Ánh xạ các view
    private void AnhXa() {
        btn_nghe_nhac = findViewById(R.id.btn_nghe_nhac);
        btn_xem_video = findViewById(R.id.btn_xem_video);
    }
}