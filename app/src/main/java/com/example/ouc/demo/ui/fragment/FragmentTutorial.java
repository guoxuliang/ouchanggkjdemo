package com.example.ouc.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseFragment;

public class FragmentTutorial extends BaseFragment {
    private View v;
    private TextView tv_back, tv_content;
    private WebView fragmentshopweb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmentshop2, null);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle();
        initViews();
    }
    private void initTitle() {
        tv_back = v.findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        tv_content = v.findViewById(R.id.tv_title);
        tv_content.setText("视频教程");
    }

    private void initViews() {
        fragmentshopweb=v.findViewById(R.id.fragmentshopweb);
        fragmentshopweb.loadUrl("http://adver.ockeji.com");
        //支持App内部javascript交互
        fragmentshopweb.getSettings().setJavaScriptEnabled(true);
        //自适应屏幕
        fragmentshopweb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        fragmentshopweb.getSettings().setLoadWithOverviewMode(true);
        //设置可以支持缩放
        fragmentshopweb.getSettings().setSupportZoom(true);

        //扩大比例的缩放
        fragmentshopweb.getSettings().setUseWideViewPort(true);
        //设置是否出现缩放工具
        fragmentshopweb.getSettings().setBuiltInZoomControls(true);

        //声明WebSettings子类
        WebSettings webSettings = fragmentshopweb.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
//        webSettings.setJavaScriptEnabled(true);
//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);//加载出来一片空白


        //优先使用缓存:
        fragmentshopweb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        //不使用缓存:
        fragmentshopweb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        fragmentshopweb.setWebChromeClient(new WebChromeClient());
        fragmentshopweb.setWebViewClient(new WebViewClient());
    }


}
