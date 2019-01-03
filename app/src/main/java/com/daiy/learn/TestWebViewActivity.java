package com.daiy.learn;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TestWebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);

        initWeb(webView);

//        webView.loadUrl("file:///android_asset/www/test.html");
        webView.loadUrl("http://www.baidu.com");
    }

    /**
     * 初始化webview
     *
     * @param webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWeb(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //支持App内部javascript交互

        webView.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.getSettings().setLoadWithOverviewMode(true);

        //设置可以支持缩放

        webView.getSettings().setSupportZoom(true);

        //扩大比例的缩放

        webView.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具

        webView.getSettings().setBuiltInZoomControls(true);
    }
}
