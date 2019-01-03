package com.daiy.learn.video;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.daiy.dylib.application.LeakCanaryApplication;
import com.daiy.dylib.logcat.LogTools;
import com.daiy.learn.R;
import com.squareup.leakcanary.LeakReference;

import java.lang.ref.WeakReference;

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    public final String videoUrl = "http://rbv01.ku6.com/omtSn0z_PTREtneb3GRtGg.mp4";
    private FrameLayout frameLayout;
    private WeakReference<VideoActivity> weakReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        weakReference = new WeakReference<>(this);
        init();
    }

    private void init() {
        frameLayout = (FrameLayout) findViewById(R.id.button).getParent();
        videoView = new VideoView(weakReference.get());
        frameLayout.addView(videoView,0);
        videoView.setVideoPath(videoUrl);
        videoView.setMediaController(new MediaController(this));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
                int width = frameLayout.getWidth();
                LogTools.loge("width:" + width);
                int widthN = width - 50;
                LogTools.loge("widthN:" + widthN);
                layoutParams.width = widthN;
                frameLayout.requestLayout();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
                int width = frameLayout.getMeasuredWidth();
                LogTools.loge("width:" + width);
                int widthN = width + 50;
                LogTools.loge("widthN:" + widthN);
                layoutParams.width = widthN;
                frameLayout.requestLayout();
            }
        });
    }

    @Override
    protected void onDestroy() {
        frameLayout.removeView(videoView);
        videoView.stopPlayback();
        videoView = null;
        weakReference = null;
        frameLayout = null;
        super.onDestroy();
    }
}
