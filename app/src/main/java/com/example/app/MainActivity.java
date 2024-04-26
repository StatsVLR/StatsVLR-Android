package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends Activity {

    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });

        mWebView = findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        // REMOTE RESOURCE
        mWebView.loadUrl("https://vlr.gg");

        // LOCAL RESOURCE
        // mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            mSwipeRefreshLayout.setRefreshing(false);

            // Load and execute custom JavaScript from a URL
            String statsvlrJS = "(function statsvlrJS() {" +
                    "    var script = document.createElement(`script`);" +
                    "    script.type = `text/javascript`;" +
                    "    script.src = `https://raw.githubusercontent.com/krushna06/Stats-VLR/main/browser.js`;" +
                    "    var head = document.getElementsByTagName(`head`)[0];" +
                    "    if (!head) return;" +
                    "    head.appendChild(script);" +
                    "})();";
            view.evaluateJavascript(statsvlrJS, null);

            String statsvlrCSS = "(function statsvlrCSS() {" +
                    "    var style = document.createElement(`link`);" +
                    "    style.rel = `stylesheet`;" +
                    "    style.href = `https://raw.githubusercontent.com/krushna06/Stats-VLR/main/browser.css`;" +
                    "    var head = document.getElementsByTagName(`head`)[0];" +
                    "    if (!head) return;" +
                    "    head.appendChild(style);" +
                    "})();";
            view.evaluateJavascript(statsvlrCSS, null);
        }
    }
}
