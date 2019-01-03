package com.daiy.dylib.pictureview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context context;

    private GalleryPositionListener positionListener;

    private ArrayList<HelpTopicImageBean> helpTopicImage = new ArrayList<HelpTopicImageBean>();
    private int position = 0;


    public void setData(List<Integer> data) {
        notifyDataSetChanged();
    }

    public GalleryAdapter(Context context, ArrayList<HelpTopicImageBean> helpTopicImage, int position) {
        this.context = context;
        this.helpTopicImage = helpTopicImage;
        this.position = position;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount() {
        return helpTopicImage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyImageView view = new MyImageView(context);
        view.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        ImageLoader.getInstance().displayImage(helpTopicImage.get(position).getImgUrl(), view);
        positionListener.movePosition(position);
        return view;
    }

    public void getPositionListener(GalleryPositionListener positionListener) {
        this.positionListener = positionListener;
    }

    public interface GalleryPositionListener {
        public void movePosition(int index);
    }
}
