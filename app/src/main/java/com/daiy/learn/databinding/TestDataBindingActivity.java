package com.daiy.learn.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daiy.learn.R;

public class TestDataBindingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_binding);
        DataInfo dataInfo = new DataInfo();

        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setInfo(dataInfo);
        //可绑定多个
        binding.setOther(new DataTestInfo());

        dataInfo.setName("修改显示内容");
        dataInfo.title.set("测试完成");
        dataInfo.list.add(new DataTestInfo());
        dataInfo.list.add(new DataTestInfo());
        dataInfo.list.add(new DataTestInfo());
    }
}
