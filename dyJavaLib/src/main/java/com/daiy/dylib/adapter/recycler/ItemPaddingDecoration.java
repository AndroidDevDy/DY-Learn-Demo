package com.daiy.dylib.adapter.recycler;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lsw on 2017/7/28.
 * RecyclerView设置间距
 */

public class ItemPaddingDecoration extends RecyclerView.ItemDecoration {

    private int left, right, top, bottom;

    public ItemPaddingDecoration(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//        if (parent.getChildPosition(view) != 0) {
        outRect.top = top;
        outRect.bottom = bottom;
        outRect.right = right;
        outRect.left = left;
//        }

    }
}
