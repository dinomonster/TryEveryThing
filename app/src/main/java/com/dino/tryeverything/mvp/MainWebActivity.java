package com.dino.tryeverything.mvp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("JavascriptInterface")
public class MainWebActivity extends BaseActivity {
    @BindView(R.id.web_progress)
    ProgressBar web_progress;
    @BindView(R.id.studyresstore_web)
    WebView web;

    private String url;
    private String title;
    public static String javascript = "javascript:";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mainweb;
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        setToolbarCentel_Img(true,title,R.drawable.ic_more);
        url = getIntent().getStringExtra("url");
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(false);

        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                view.loadUrl(url);
                return false;
            }
        });

        web.setWebChromeClient(new WebChromeClient());
        web.addJavascriptInterface(new JavaScriptInterface(),"webInterface");
        web.loadUrl(url);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.image)
    public void onClick() {

    }

    class JavaScriptInterface {
        JavaScriptInterface() {
        }

        @JavascriptInterface
        public void back() {
            finish();
        }

    }

    //原生调用javascript方法
    public void doJavascript(String method) {
        web.loadUrl(javascript + method);
    }



    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            onReceivedTitle(view, view.getTitle());
            if (newProgress == 100) {
                web_progress.setVisibility(View.GONE);
//                doJavascript("fun_getTtilePic()");
            } else {
                if (web_progress.getVisibility() == View.GONE)
                    web_progress.setVisibility(View.VISIBLE);
                Log.e("newProgress", "newProgress:" + newProgress);
                web_progress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            MainWebActivity.this.title = title;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("canGoBack", "canGoBack:" + web.canGoBack());
        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack();// 返回前一个页面
//            about_close.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
