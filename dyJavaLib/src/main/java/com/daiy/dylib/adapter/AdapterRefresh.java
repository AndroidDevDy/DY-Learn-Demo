package com.daiy.dylib.adapter;

/**
 * 回调
 *
 * @author lsw
 */
public interface AdapterRefresh {
    /**
     * Adapter刷新界面的回调方法
     *
     * @param position
     * @param taskid
     */
    void onRefreshAdapter(int position, int taskid);
}
