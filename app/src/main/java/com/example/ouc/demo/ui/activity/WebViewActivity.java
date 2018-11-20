package com.example.ouc.demo.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;

public class WebViewActivity extends BaseActivity {
    private WebView webView;

    private ImageView iv_right;
    private TextView tv_back, tv_content;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Bundle bundles=getIntent().getExtras();   //得到传过来的bundle
        if(bundles!=null){
            url = bundles.getString("bannerurl");
        }

        initTitle();
        initViews();
    }
    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right= findViewById(R.id.iv_right);
        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
        iv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("广告机宣传");
    }
    private void initViews() {
        webView = findviewByid(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if(url!=null){
            webView.loadUrl(url);
        }else {
            webView.loadUrl("http://kgj.ockeji.com/index1.html");
        }

    }
}
