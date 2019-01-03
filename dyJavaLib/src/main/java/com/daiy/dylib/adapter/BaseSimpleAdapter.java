package com.daiy.dylib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter的基类
 * 用于简略常规方法
 *
 * @param <T>
 * @author lsw
 */
public abstract class BaseSimpleAdapter<T> extends android.widget.BaseAdapter {
    protected ArrayList<T> list;
    protected Context context;
    protected LayoutInflater inflater;
    protected AdapterRefresh adapterRefresh;

    public void setAdapterRefresh(AdapterRefresh adapterRefresh) {
        this.adapterRefresh = adapterRefresh;
    }

    public BaseSimpleAdapter(Context context) {
        this.context = context;
        list = new ArrayList<T>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseSimpleAdapter(Context context, ArrayList<T> data) {
        this.context = context;
        if (data != null) {
            list = data;
        } else {
            list = new ArrayList<T>();
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 获取集合
     *
     * @return
     */
    public ArrayList<T> getList() {
        return this.list;
    }

    /**
     * 重新给集合填充数据
     *
     * @param mList
     */
    public synchronized void setData(List<? extends T> mList) {
        this.list.clear();
        notifyDataSetChanged();
        if (mList != null) {
            this.list.addAll(mList);
        }
        notifyDataSetChanged();
    }

    /**
     * 批量添加数据
     *
     * @param mList
     */
    public void addData(List<? extends T> mList) {
        if (mList != null) {
            this.list.addAll(mList);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加一项记录
     *
     * @param item
     */
    public void add(T item) {
        this.list.add(item);
        notifyDataSetChanged();
    }

    /**
     * 移除一项
     *
     * @param position
     */
    public synchronized void remove(int position) {
        int size = list.size();
        if (position < size) {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        BaseHolder<T> holder = null;
        if (contentView == null) {
            if (getViewTypeCount() > 1) {
                if (getLayoutResource(position) > 0) {
                    //布局加载
                    contentView = inflater.inflate(getLayoutResource(position), null);
                } else {
                    //自定义视图加载
                    contentView = getCustomerView();
                }
            } else {
                if (getLayoutResource() > 0) {
                    //布局加载
                    contentView = inflater.inflate(getLayoutResource(), null);
                } else {
                    //自定义视图加载
                    contentView = getCustomerView();
                }
            }

            holder = getHolder();
            holder.bindViews(contentView);
            contentView.setTag(holder);
        } else {
            holder = (BaseHolder<T>) contentView.getTag();
        }
        holder.bindData(getItem(position), position);
        return contentView;
    }

    /**
     * 用户需要返回实现BaseHolder的holder
     *
     * @return
     */
    protected abstract BaseHolder<T> getHolder();

    /**
     * cell项的布局文件资源
     * 如果需要使用自定义视图 @getCustomerView 当前返回为0
     *
     * @return
     */
    protected abstract int getLayoutResource();

    protected int getLayoutResource(int position) {
        return 0;
    }


    /**
     * 弥补不用布局文件中加载视图
     * 子类需要复写
     *
     * @return
     */
    protected View getCustomerView() {
        return null;
    }
}
