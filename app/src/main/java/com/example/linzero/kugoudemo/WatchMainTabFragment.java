package com.example.linzero.kugoudemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Linzero on 2017/04/20.
 */

public class WatchMainTabFragment extends Fragment {
    private WebView webView_listen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kugouWebView();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    webview_listen();
                    break;
            }
        }
    };

    private void webview_listen() {
        webView_listen.goBack();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.tab02, container, false);
        return webView_listen;
    }

    private void kugouWebView() {
        webView_listen = new WebView(getActivity());
        webView_listen.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView_listen.getSettings().setJavaScriptEnabled(true);    //设置webview属性，运行执行js脚本
        webView_listen.loadUrl("http://www.runoob.com/w3cnote/android-tutorial-intro.html"); //调用loadurl方法为webview加入链接

        webView_listen.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView_listen.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }
        });
    }
}
