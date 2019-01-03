package com.daiy.dylib.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * fragment的适配器
 * Created by Administrator on 2017/12/18 0018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> fragments;

    public FragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
