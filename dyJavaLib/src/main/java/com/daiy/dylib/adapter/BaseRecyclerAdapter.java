package com.daiy.dylib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiaoXiang on 2015/11/4.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected List<T> list;
    private int layoutId;
    protected Context mContext;
    public AdapterRefresh adapterRefresh;

    public BaseRecyclerAdapter(int layoutId, Context context) {
        this.layoutId = layoutId;
        this.mContext = context;
        list = new ArrayList<>();
    }

    public BaseRecyclerAdapter(List<T> list, int layoutId, Context context) {
        this.list = list;
        if (list == null)
            this.list = new ArrayList<>();
        this.layoutId = layoutId;
        this.mContext = context;
    }

    /**
     * ItemClick的回调接口
     *
     * @author zhy
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    /**
     * 设置OnItemClickListener
     *
     * @param mOnItemClickListener OnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setAdapterRefresh(AdapterRefresh adapterRefresh) {
        this.adapterRefresh = adapterRefresh;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutId <= 0) {
            layoutId = android.R.layout.simple_list_item_1;
        }
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });

        }
        T t = list.get(position);
        if (t != null) {
            onBind(position, t, holder);
        }
    }

    /**
     * 获取集合
     *
     * @return
     */
    public List<T> getList() {
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
    public int getItemCount() {
        return list.size();
    }

    /**
     * 绑定视图与数据
     *
     * @param position 当前位置
     * @param t        数据
     * @param holder
     */
    public abstract void onBind(int position, T t, ViewHolder holder);
}
