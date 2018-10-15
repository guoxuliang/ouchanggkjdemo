package com.example.ouc.demo.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.MyOrderAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.MyOrderEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdvertFragment1 extends BaseFragment {
    private View view;
private TextView nodata,tv_content;
//    private ProgersssDialog progersssDialog;
    private String contentAll;

    private WebView webView;

    private String html="<head><meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\"><meta content=\"telephone=no\" name=\"format-detection\"></head><P><FONT size=5>【一汽-大众诸暨元通】 —— 全心，全服务</FONT><SPAN style=\"COLOR: #000000\"><!--StartFragment --></P><DIV><FONT size=5>    9月27-29日，诸暨元通一汽-大众全系现车秋季城市广场金秋温情大酬宾啦！对一汽-大众大众汽车感兴趣的朋友亦可带着家人，邀上朋友，结伴来城市广场一汽-大众展位，我们期待您的光临</FONT><FONT size=5>！</FONT></DIV><DIV><FONT size=5>展位位置：</FONT></DIV><DIV><IMG style=\"WIDTH: 653px; HEIGHT: 388px\" height=673 src=\"/pro/iXs_Editor/uploadfile/20130925102136428.jpg\" width=1117 border=0></DIV><DIV> </DIV><DIV><FONT size=5>梦想随行，国庆献礼，诸暨元通一汽大众金秋温情大酬宾</FONT></DIV><DIV><FONT size=5></FONT> </DIV><DIV><IMG src=\"/pro/iXs_Editor/uploadfile/20130925102251145.jpg\" border=0></DIV><DIV><FONT size=4></FONT> </DIV><DIV><FONT size=4>愿我们真心优质的服务，真挚诚恳的让利，让您尽享购车之旅的那份愉悦与享受！<BR>我们的使命是让更多的人拥有一汽-大众品牌轿车！<BR></FONT><FONT size=4><STRONG><FONT color=#ff0000>汽车之路，元通相伴，汽车生活，元通服务<BR></FONT></STRONG>享受更多优惠，尽在诸暨元通一汽-大众4S店（原海越汽车）！<BR>一汽－大众授权经销商：诸暨元通汽车有限公司<BR>4S店地址：诸暨市环城西路167号（老办证中心旁）<BR>销售热线：0575-87105555<BR>预约热线：0575-87105501<BR>24小时救援热线：13857521919</FONT></DIV><P align=left><IMG src=\"http://car.zhuji.net/pro/iXs_Editor/uploadfile/20130903090510258.jpg\" border=0></P><DIV> </DIV></SPAN>";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.advertfragment1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        progersssDialog = new ProgersssDialog(getActivity());
        contentAll = getStringSharePreferences("content","content");
        Log.i("contentAll","contentAll:"+contentAll);
        initViews();

    }

    private void initViews() {
        nodata = view.findViewById(R.id.nodata);
        webView= view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.loadDataWithBaseURL("", contentAll, "text/html", "UTF-8", "");
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webView.requestFocus();

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //设置点击网页里面的链接还是在当前的webview里跳转
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
