package com.daiy.learn.stationbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.daiy.dylib.stationBar.StationBarUtils;
import com.daiy.learn.R;

public class TestStationBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.default_icon);
        setContentView(imageView);
        //全屏沉侵式
//        Objects.requireNonNull(getSupportActionBar()).hide();
//        StationBarUtils.fullScreen(this);

        //设置状态栏颜色
//        StationBarUtils.setStationBar(this, Color.RED);
        //设置状态栏透明
//        StationBarUtils.translucentStatusBar(this, true);
        //设置状态栏文字黑色
        StationBarUtils.setStationBarTextBlack(this, Color.WHITE);
    }
}
