package com.example.ouc.demo.ui.activity.vip;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.ChangePwdEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.MainActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.MD5Util;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.utils.Tools;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AboutWeActivity extends BaseActivity {
    private TextView tv_back, tv_content;
    private ImageView iv_right;
    private Button tv_question1,tv_question2,tv_question3,tv_question4,tv_question5,tv_question6;
    private TextView tv_answer1,tv_answer2,tv_answer3,tv_answer4,tv_answer5,tv_answer6,line_zx,versionCode;

    private boolean isBtn1=false;
    private boolean isBtn2=false;
    private boolean isBtn3=false;
    private boolean isBtn4=false;
    private boolean isBtn5=false;
    private boolean isBtn6=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutwe);
        initTitle();
        initViews();
    }

    private void initViews() {
        tv_question1 =(Button) findViewById(R.id.tv_question1);
        tv_question2 = (Button)findViewById(R.id.tv_question2);
        tv_question3 =(Button) findViewById(R.id.tv_question3);
        tv_question4 =(Button) findViewById(R.id.tv_question4);
        tv_question5 =(Button) findViewById(R.id.tv_question5);
        tv_question6 =(Button) findViewById(R.id.tv_question6);
        line_zx = (TextView) findViewById(R.id.line_zx);
        tv_answer1 =(TextView) findViewById(R.id.tv_answer1);
        tv_answer2 = (TextView)findViewById(R.id.tv_answer2);
        tv_answer3 =(TextView) findViewById(R.id.tv_answer3);
        tv_answer4 =(TextView) findViewById(R.id.tv_answer4);
        tv_answer5 =(TextView) findViewById(R.id.tv_answer5);
        tv_answer6 =(TextView) findViewById(R.id.tv_answer6);
        versionCode = (TextView) findViewById(R.id.versionCode);
        Log.i("","版本:"+Tools.getVersion(AboutWeActivity.this));
        String versionCodeStr= String.valueOf(Tools.getVersion(AboutWeActivity.this));
        versionCode.setText("版本:"+versionCodeStr+".0.0");
        versionCode.setTextColor(R.color.black);
        tv_question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBtn1==false){
                    tv_answer1.setVisibility(View.VISIBLE);
                    isBtn1=true;
                }else {
                    tv_answer1.setVisibility(View.GONE);
                    isBtn1=false;
                }

            }
        });
        tv_question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBtn2==false){
                    tv_answer2.setVisibility(View.VISIBLE);
                    isBtn2=true;
                }else {
                    tv_answer2.setVisibility(View.GONE);
                    isBtn2=false;
                }
            }
        });
        tv_question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBtn3==false){
                    tv_answer3.setVisibility(View.VISIBLE);
                    isBtn3=true;
                }else {
                    tv_answer3.setVisibility(View.GONE);
                    isBtn3=false;
                }
            }
        });
        tv_question4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBtn4==false){
                    tv_answer4.setVisibility(View.VISIBLE);
                    isBtn4=true;
                }else {
                    tv_answer4.setVisibility(View.GONE);
                    isBtn4=false;
                }
            }
        });
        tv_question5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBtn5==false){
                    tv_answer5.setVisibility(View.VISIBLE);
                    isBtn5=true;
                }else {
                    tv_answer5.setVisibility(View.GONE);
                    isBtn5=false;
                }
            }
        });
        tv_question6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBtn6==false){
                    tv_answer6.setVisibility(View.VISIBLE);
                    isBtn6=true;
                }else {
                    tv_answer6.setVisibility(View.GONE);
                    isBtn6=false;
                }
            }
        });
        line_zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    ToastHelper.show(AboutWeActivity.this,"检查到您手机没有安装微信，请安装后使用该功能");
                }
            }
        });

    }
    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right= findViewById(R.id.iv_right);
        iv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutWeActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("帮助&反馈");
    }

}
