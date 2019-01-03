package com.daiy.dylib.photoview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.ArrayList;

/**
 * 查看大图
 */
public class BigImagePagerActivity extends AppCompatActivity {
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private int position = 0;

    private ViewPager viewPager;

    public static void openActivity(Activity activity, int position, ArrayList<String> list) {
        Intent intent = new Intent(activity, BigImagePagerActivity.class);
        intent.putStringArrayListExtra("list", list);
        intent.putExtra("position", position);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = new ViewPager(this);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(viewPager);
        position = getIntent().getIntExtra("position", 0);
        ArrayList<String> dataList = getIntent().getStringArrayListExtra("list");
        if (dataList != null) {
            imageUrlList.addAll(dataList);
        }

        ImagePagerAdapter imageAdapter = new ImagePagerAdapter();
        viewPager.setAdapter(imageAdapter);

        if (imageUrlList.size() > position)
            viewPager.setCurrentItem(position);
    }

    class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageUrlList.size();
        }

        //判断是否是否为同一张图片，这里返回方法中的两个参数做比较就可以
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //设置viewpage内部东西的方法，如果viewpage内没有子空间滑动产生不了动画效果
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView imageView = new PhotoView(BigImagePagerActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundColor(Color.BLACK);
            container.addView(imageView);

            Glide.with(BigImagePagerActivity.this)
                    .load(TextUtils.isEmpty(imageUrlList.get(position)) ? "" :
                            imageUrlList.get(position))
                    .apply(new RequestOptions().error(new ColorDrawable(Color.WHITE)))
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //最后要返回的是控件本身
            return imageView;
        }

        //因为它默认是看三张图片，第四张图片的时候就会报错，还有就是不要返回父类的作用
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
