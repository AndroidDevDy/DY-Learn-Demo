package com.daiy.dylib.fragment;

import android.support.v4.app.Fragment;

/**
 * 文件名：BaseFragment
 * 描述：自定义的MyFragment，对Fragment进行了扩展，增加了两个方法leave()和back()，用于处理离开和返回两种状态
 * 作者：DaiYue
 * 时间：2016/6/3
 */
public class BaseFragment extends Fragment implements OnLeaveOrBackListener {
    /**
     * 方法名：leave()
     * 描述：Fragment离开时调用
     */
    @Override
    public void leave() {

    }

    /**
     * 方法名：back()
     * 描述：Fragment返回时调用
     */
    @Override
    public void back() {

    }

}
