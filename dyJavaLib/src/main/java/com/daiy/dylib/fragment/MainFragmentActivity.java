package com.daiy.dylib.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daiy.dylib.Constants;
import com.daiy.dylib.R;
import com.daiy.dylib.activity.ActivityTask;
import com.daiy.dylib.activity.BaseFragmentActivity;

import java.util.List;

/**
 * 文件名：MainFragmentActivity
 * 描述：用于fragment切换结构的界面框架
 * 作者：DaiYue
 * 时间：2016/6/3
 */
public class MainFragmentActivity extends BaseFragmentActivity {

    private List<BaseFragment> fragments;//用于切换的fragment
    private int layoutId;//用于承载fragment的控件id
    private RadioGroup radioGroup;//用于切换的按钮组
    private List<Integer> ids;//用于切换的按钮id
    private FragmentManager fragmentManager;//fragment的管理者
    private FragmentTransaction fragmentTransaction;//fragment事件
    private long lastExitTime;//上一次点击退出app的时间戳
    private BaseFragment currentFragment;//当前显示的fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init();
        //由继承的子类来实现,初始化
    }

    /**
     * 初始化
     *
     * @param fragments  用于切换的fragment
     * @param layoutId   用于存放fragment的frameLayout的id
     * @param radioGroup 按钮组
     * @param ids        按钮id
     */
    public void init(List<BaseFragment> fragments, int layoutId, RadioGroup radioGroup, List<Integer> ids) {
        //赋值
        this.fragments = fragments;
        this.layoutId = layoutId;
        this.radioGroup = radioGroup;
        this.ids = ids;
        if (fragments.size() == 0 || ids.size() == 0 || fragments.size() != ids.size()) return;
        //绑定按键监听
        this.radioGroup.setOnCheckedChangeListener(new RBtnCheckedChangeListener());
        //设置主页面默认显示第一个fragment
        currentFragment = fragments.get(0);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(this.layoutId, currentFragment, "1");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //3秒内重复点击2次退出
            if ((System.currentTimeMillis() - lastExitTime) < 3000) {
                finish();
                ActivityTask.getInstance().exit();
            } else {
                lastExitTime = System.currentTimeMillis();
                Toast.makeText(this, R.string.exit_tip, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 方法名：RBtnCheckedChangeListener
     * 描述：继承RadioGroup.OnCheckedChangeListener,监听radioGroup用于切换fragment页面
     */
    private class RBtnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            fragmentManager = getSupportFragmentManager();//得到fragment管理者
            fragmentTransaction = fragmentManager.beginTransaction();//开启事件

            for (int i = 0; i < ids.size(); i++) {
                if (checkedId == ids.get(i)) {
                    currentFragment = fragments.get(i);
                    if (TextUtils.isEmpty(currentFragment.getTag())) {
                        fragmentTransaction.add(layoutId, currentFragment, String.valueOf(i));//显示并显示-主页
                    } else {
                        fragmentTransaction.show(currentFragment);//显示-主页
                    }
                } else {
                    if (!TextUtils.isEmpty(fragments.get(i).getTag())) {
                        fragmentTransaction.hide(fragments.get(i));
                        fragments.get(i).leave();
                    }
                }
            }
            fragmentTransaction.commit();//提交事件
            currentFragment.back();
        }
    }
}
