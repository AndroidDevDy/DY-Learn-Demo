package com.daiy.learn.photo_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daiy.dylib.photoview.BigImagePagerActivity;
import com.daiy.dylib.pictureview.HelpTopicImageBean;
import com.daiy.dylib.pictureview.PictureViewFra;

import java.util.ArrayList;

public class TestImageScaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> list = new ArrayList<>();
        list.add("http://img.zcool.cn/community/019c2958a2b760a801219c77a9d27f.jpg");
        list.add("http://img03.tooopen.com/uploadfile/downs/images/20110714/sy_20110714135215645030.jpg");
        list.add("http://img.sccnn.com/bimg/338/24556.jpg");
        list.add("http://img.zcool.cn/community/01b88659bb7903a801212fb7949ee0.jpg@2o.jpg");
//        BigImagePagerActivity.openActivity(this, 1, list);

        ArrayList<HelpTopicImageBean> beans = new ArrayList<>();
        for (String url : list) {
            beans.add(new HelpTopicImageBean(url, ""));
        }
        PictureViewFra.openActivity(this, 1, beans);
    }

}
