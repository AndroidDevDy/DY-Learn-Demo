package com.daiy.dylib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.daiy.dylib.Constants;

/**
 * FragmentActivity的一个基类
 * Created by Administrator on 2017/12/4 0004.
 */

public class BaseFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        //if (getSupportActionBar() != null)
        //    getSupportActionBar().hide();

        //点击桌面启动图标，App从后台回到前台时，保持上一次的页面
        if (keepActivityWhenDesktopOpen()) {
            return;
        }

        //用于App被回收时，重新启动App
        backToFirstActivityWhenRecycle(savedInstanceState);

        //创建成功后，加入栈中管理
        ActivityTask.getInstance().add(this);
    }

    @Override
    protected void onDestroy() {
        //当Activity销毁时，一并从栈中移除
        ActivityTask.getInstance().remove(this);
        super.onDestroy();
    }


    /**
     * 当App挂后台被系统回收，再次打开时，
     * 回到firstActivity，并关闭其余的Activity。
     */
    private void backToFirstActivityWhenRecycle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (!getClass().getSimpleName().equals(Constants.firstActivity)) {
                Process.killProcess(Process.myPid());
            }
        }
    }

    /**
     * 点击桌面启动图标，App从后台回到前台时，保持上一次的页面
     * 相应的设置app后台常驻，application标签下设置，android:persistent="true"
     * 并且启动Activity应为默认启动模式,android:launchMode="standard"
     */
    protected boolean keepActivityWhenDesktopOpen() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return true;
        }
        return false;
    }
}
