package com.daiy.learn.test_rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daiy.dylib.logcat.LogTools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RxJavaExampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxJavaExample1 example1 = new RxJavaExample1();
//        example1.test1();
//        example1.test2();
//        example1.test3();
//        example1.test4();
//        example1.test5();
//        example1.test6();
//        example1.test7();
//        example1.test8();
//        example1.test9();
//        example1.test10();
//        example1.test11();
//        example1.test12();
//        example1.test13();
//        example1.test14();
//        example1.test15();
//        example1.test16();
//        example1.test17();
//        example1.test18();
//        example1.test19();
//        example1.test20();
//        example1.test21();
//        example1.test22();
//        example1.test23();
//        example1.test24();
//        example1.test25();
//        example1.test26();
//        example1.test27();
//        example1.test28();
//        example1.test29();
//        example1.test30();
//        example1.test31();
//        example1.test32();
//        example1.test33();
//        example1.test34();
//        example1.test35();
//        example1.test36();
        example1.test37();

//        reflect_to_run_methods(example1);
    }

    /**
     * 通过反射来运行RxJavaExample1类中的一系列test方法
     *
     * @param example1 实例
     */
    private void reflect_to_run_methods(RxJavaExample1 example1) {
        Method[] methods = RxJavaExample1.class.getDeclaredMethods();
        for (Method method : methods) {
            String s = method.getName();
            if (s.startsWith("test")) {
                LogTools.loge("============:" + s);
                try {
                    method.invoke(example1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
