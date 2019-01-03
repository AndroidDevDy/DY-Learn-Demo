package com.daiy.dylib.pictureview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.daiy.dylib.R;

import java.util.ArrayList;

public class PictureViewFra extends Activity {
    private PicGallery gallery;
    private boolean mTweetShow = false; // 弹层是否显示


    // 屏幕宽度
    public static int screenWidth;
    // 屏幕高度
    public static int screenHeight;

    private GalleryAdapter mAdapter;

    private ArrayList<HelpTopicImageBean> helpTopicImage = new ArrayList<HelpTopicImageBean>();
    private int position = 0;
    // private ProgressDialog mProgress;

    private TextView tv_img_count, tv_topic_title;


    public static void openActivity(Activity activity, int position, ArrayList<HelpTopicImageBean> list) {
        Intent intent = new Intent(activity, PictureViewFra.class);
        intent.putExtra("helpTopicImage", list);
        intent.putExtra("position", position);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_view);

        getIntentData();

        tv_img_count = (TextView) findViewById(R.id.tv_img_count);
        tv_topic_title = (TextView) findViewById(R.id.tv_topic_title);

        gallery = (PicGallery) findViewById(R.id.pic_gallery);
        gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
        gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
        gallery.setDetector(new GestureDetector(this,
                new MySimpleGesture()));
        mAdapter = new GalleryAdapter(this, helpTopicImage, position);
        gallery.setAdapter(mAdapter);
        gallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                return false;
            }
        });

        mAdapter.getPositionListener(new GalleryAdapter.GalleryPositionListener() {
            @Override
            public void movePosition(int index) {
                Log.d("helpTopicImage--> ", " " + index);
                tv_img_count.setText((index + 1) + "/" + helpTopicImage.size());
                tv_topic_title.setText(helpTopicImage.get(index).getImgInfo());
            }
        });

        // mAdapter.setData(dataResult);
        initViews();
        // mProgress = ProgressDialog.show(getActivity(),
        // null,getActivity().getString(R.string.loading));
    }

    private void getIntentData() {
        Intent intent = getIntent();
        helpTopicImage = (ArrayList<HelpTopicImageBean>) intent.getSerializableExtra("helpTopicImage");
        position = intent.getIntExtra("position", 0);
    }

    private void initViews() {

        screenWidth = getWindow().getWindowManager().getDefaultDisplay()
                .getWidth();
        screenHeight = getWindow().getWindowManager().getDefaultDisplay()
                .getHeight();

    }

    private class MySimpleGesture extends GestureDetector.SimpleOnGestureListener {
        // 按两下的第二下Touch down时触发
        public boolean onDoubleTap(MotionEvent e) {

            View view = gallery.getSelectedView();
            if (view instanceof MyImageView) {
                MyImageView imageView = (MyImageView) view;
                if (imageView.getScale() > imageView.getMiniZoom()) {
                    imageView.zoomTo(imageView.getMiniZoom());
                } else {
                    imageView.zoomTo(imageView.getMaxZoom());
                }

            } else {

            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // Logger.LOG("onSingleTapConfirmed",
            // "onSingleTapConfirmed excute");
            mTweetShow = !mTweetShow;
            tv_topic_title.setVisibility(mTweetShow ? View.VISIBLE
                    : View.INVISIBLE);
            tv_img_count.setVisibility(mTweetShow ? View.VISIBLE
                    : View.INVISIBLE);
            return true;
        }
    }

}
