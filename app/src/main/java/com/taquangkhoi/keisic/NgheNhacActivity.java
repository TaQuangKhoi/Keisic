package com.taquangkhoi.keisic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class NgheNhacActivity extends AppCompatActivity {
    ImageView iv_play_stop;
    ImageView iv_next;
    ImageView iv_previous;
    SeekBar sb_song;
    TextView tv_current_duration;
    TextView tv_duration;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nghe_nhac);

        Control();
        Events();
    }

    // Chạy các sự kiện
    private void Events() {
        RunSeekBar();
        SetTotalDuration();
        RealTimeUpdate();
        iv_play_stop.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                iv_play_stop.setImageResource(R.drawable.ic_play_24);
            } else {
                mediaPlayer.start();
                iv_play_stop.setImageResource(R.drawable.ic_pause_24);
            }
        });
    }

    // Chứa các ánh xạ
    private void Control() {
        iv_play_stop = findViewById(R.id.iv_play_stop);
        iv_next = findViewById(R.id.iv_next);
        iv_previous = findViewById(R.id.iv_previous);
        sb_song = findViewById(R.id.sb_song);
        tv_current_duration = findViewById(R.id.tv_current_duration);
        tv_duration = findViewById(R.id.tv_duration);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rhymastic_y6u);

    }

    private void SetTotalDuration() {
        int duration = mediaPlayer.getDuration();
        tv_duration.setText(String.format("%02d:%02d", duration / 1000 / 60, duration / 1000 % 60));
    }

    private void RunSeekBar() {
        sb_song.setMax(mediaPlayer.getDuration());
        sb_song.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    tv_current_duration.setText(GetCurrentDuration());
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // Cập nhật thời gian thực TextView Current Duration và progress của SeekBar
    private void RealTimeUpdate() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_current_duration.setText(GetCurrentDuration());
                sb_song.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    // Lấy giờ dạng String
    private String GetCurrentDuration() {
        int currentDuration = mediaPlayer.getCurrentPosition();
        int MINUTE_CURRENT_DURATION = currentDuration / 1000 / 60;
        int SECOND_CURRENT_DURATION = currentDuration / 1000 % 60;
        return String.format("%02d:%02d",
                MINUTE_CURRENT_DURATION, SECOND_CURRENT_DURATION);
    }
}