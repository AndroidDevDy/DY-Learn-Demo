package com.daiy.learn.databinding;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daiy.learn.BR;
import com.daiy.learn.R;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class DataInfo {
    private String name = "测试-姓名";
    public String test;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableField<String> title = new ObservableField<>("测试一下ObservableField");

    public ObservableList<DataTestInfo> list = new ObservableArrayList<>();

    //item绑定方式一
    public ItemBinding<DataTestInfo> itemBinding = ItemBinding.of(BR.info, R.layout.item_layout);

    //item绑定方式二
    public OnItemBind<DataTestInfo> itemBind = new OnItemBind<DataTestInfo>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, DataTestInfo item) {
            itemBinding.set(BR.info, R.layout.item_layout);
        }
    };

    public void onClick(View view) {
        if (view instanceof TextView) {
            Toast.makeText(view.getContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
