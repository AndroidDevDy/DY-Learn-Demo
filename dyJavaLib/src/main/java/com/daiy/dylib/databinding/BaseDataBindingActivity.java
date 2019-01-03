package com.daiy.dylib.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseDataBindingActivity<T extends BaseDataBindingInfo> extends AppCompatActivity {
    public ViewDataBinding binding;
    public T info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, setLayoutId());
        binding.setVariable(setVariableId(), info);

    }

    public abstract int setLayoutId();

    public abstract int setVariableId();//example:com.daiy.dylib.BR.info
}
