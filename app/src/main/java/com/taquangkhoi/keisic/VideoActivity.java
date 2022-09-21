package com.taquangkhoi.keisic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    VideoView vv_video;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        vv_video = findViewById(R.id.vv_video);
        mediaController = new MediaController(this);

        vv_video.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video_rhymastic_y6u);

        mediaController.setAnchorView(vv_video);
        vv_video.setMediaController(mediaController);


    }
}