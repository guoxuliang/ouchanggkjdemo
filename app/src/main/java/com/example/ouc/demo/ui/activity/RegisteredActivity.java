package com.example.ouc.demo.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.CodeEntity;
import com.example.ouc.demo.entity.RegistEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.vip.AboutWeActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.MD5Util;
import com.example.ouc.demo.utils.PhoneFormatCheckUtils;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jiguang.share.android.api.Platform;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisteredActivity extends BaseActivity {
    private TextView tv_back, tv_content,tv_right;

    private EditText et_phone, et_yz, et_u, et_p, et_pqr,et_tjm;
    private Button submit, btnCode;

    //============
    private String mobilePhone;    //true		用户手机号
    private String username;    //true	String	用户名
    private String password;    //true	String	密码
    private String password_qr;    //true	String	密码
    private String verCode;    //true	String	验证码
    private String personNo;    //true	String	推荐码
    private String level = "1";    //true	String	用户等级，默认为1，普通会员
    private String ways = "0";    //true	String	注册方式，默认为0，平台注册
    private String status = "1";   //	true	String	用户状态，默认为1，启用状态
    private String yzcode = "1";

    private Gson gson = new Gson();
    private RegistEntity registEntity;
    private CodeEntity codeEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initTitle();
        initViews();
    }

    private void initViews() {
        et_phone = findViewById(R.id.et_phone);
        et_yz = findViewById(R.id.et_yz);
        et_u = findViewById(R.id.et_u);
        et_p = findViewById(R.id.et_p);
        et_pqr = findViewById(R.id.et_pqr);
        submit = findViewById(R.id.submit);
        btnCode = findViewById(R.id.btnCode);
        et_tjm = findViewById(R.id.et_tjm);
//        btnCode.setEnabled(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobilePhone = et_phone.getText().toString().trim();
                username = et_u.getText().toString().trim();
                password = et_p.getText().toString().trim();
                password_qr = et_pqr.getText().toString().trim();
                personNo = et_tjm.getText().toString().trim();
                if (mobilePhone.equals("")) {
                    ToastHelper.show(RegisteredActivity.this, "请输入手机号");
                    return;
                }
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(mobilePhone) != true) {
                    ToastHelper.show(RegisteredActivity.this, "手机号码格式不对");
                    return;
                }
                if (username.equals("")) {
                    ToastHelper.show(RegisteredActivity.this, "请输入用户名");
                    return;
                }
                if (password == null || password.equals("")) {
                    ToastHelper.show(RegisteredActivity.this, "请输入密码");
                    return;
                }
                if(personNo==null || personNo.equals("")){
                    ToastHelper.show(RegisteredActivity.this, "请输入验证码");
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisteredActivity.this, "密码长度不能小于6位", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() >= 18) {
                    Toast.makeText(RegisteredActivity.this, "密码过长", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.equals(password_qr)) {
                    ToastHelper.show(RegisteredActivity.this, "两次输入密码不一致");
                    return;
                }
                if (verCode.equals("")) {
                    ToastHelper.show(RegisteredActivity.this, "验证码不能为空");
                    return;
                }
                postSbubmit();//提交注册

            }
        });
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobilePhone = et_phone.getText().toString().trim();
                if (mobilePhone.equals("")) {
                    ToastHelper.show(RegisteredActivity.this, "请输入手机号");
                    return;
                }
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(mobilePhone) != true) {
                    ToastHelper.show(RegisteredActivity.this, "手机号码格式不对");
                    return;
                }
                btnCode.setBackgroundColor(Color.GRAY);
                btnCode.setEnabled(false);
                timer.start();
                getCode();//获取验证码
            }
        });
    }

    /**
     * 倒计时
     */

    private TextView vertifyView;
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(final long millisUntilFinished) {
            btnCode.setText("倒计时:"+millisUntilFinished/1000);
            final String time= String.valueOf(millisUntilFinished/1000);
            if((millisUntilFinished/1000)==1){
                btnCode.setBackgroundColor(Color.parseColor("#ff0000"));
                btnCode.setText("倒计时:0秒");
                btnCode.setEnabled(true);
            }

        }

        @Override
        public void onFinish() {
//            vertifyView.setEnabled(true);
//            vertifyView.setText("获取验证码");
        }
    };


    @SuppressLint("ResourceAsColor")
    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("帮助");
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisteredActivity.this.finish();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(AboutWeActivity.class);
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("会员注册");
    }

    /**
     * postSbubmit  请求注册申请
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void postSbubmit() {
        Map<String, String> map = new HashMap<>();
        map.put("mobilePhone", mobilePhone);
        map.put("username", username);
        map.put("personNo", personNo);
        map.put("password", MD5Util.MD5(password));
        //TODO   此处应该加入验证码  字段
        map.put("level", "1");
        map.put("ways", "0");
        map.put("status", "1");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemloginController/register.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    registEntity = gson.fromJson(result, RegistEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastHelper.show(RegisteredActivity.this, "" + registEntity.getMsg());
                            int code = registEntity.getCode();
                            if (code == 200) {
                                setStringSharedPreferences("", mobilePhone, mobilePhone);
                                String commendNo = registEntity.getData().getCommendNo();
                                setStringSharedPreferences("commendNo",commendNo,commendNo);
                                RegisteredActivity.this.finish();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 获取验证码
     * getRegistCode
     *
     */
    private void getCode() {
        HttpUtils.doGet(Constants.SERVER_BASE_URL + "system/sys/sendMsgController/getResult.action?" + "phone=" + mobilePhone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.show(RegisteredActivity.this, "ERROR:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    codeEntity = gson.fromJson(result, CodeEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (codeEntity.getCode()==200) {
                                verCode = codeEntity.getData().toString().trim();
                                ToastHelper.show(RegisteredActivity.this, codeEntity.getMsg());
                            } else {
                                ToastHelper.show(RegisteredActivity.this, codeEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
