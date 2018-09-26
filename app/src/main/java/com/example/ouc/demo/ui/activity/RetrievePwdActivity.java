package com.example.ouc.demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.CodeEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.MD5Util;
import com.example.ouc.demo.utils.PhoneFormatCheckUtils;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 修改密码
 */
public class RetrievePwdActivity extends AppCompatActivity {
    private TextView tv_back,tv_content;
    private EditText et_phone_find,et_yz_find,et_p_find,et_pqr_find;
    private Button submit_find,btn_getcode;

    private String phone;	//true	String	会员手机号
    private String  new_pwd;	//true	String	会员新密码
    private String  new_pwd_T;	//true	String	会员新密码
    private String verCode;    //true	String	验证码
    private Gson gson = new Gson();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievepwd);
        initTitle();
        initViews();
    }

    private void initViews() {
        et_phone_find=findViewById(R.id.et_phone_find);
        et_yz_find=findViewById(R.id.et_yz_find);
        et_p_find=findViewById(R.id.et_p_find);
        et_pqr_find=findViewById(R.id.et_pqr_find);
        submit_find=findViewById(R.id.submit_find);
        btn_getcode=findViewById(R.id.btn_getcode);
        submit_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=et_phone_find.getText().toString().trim();
                et_yz_find.getText().toString().trim();
                new_pwd=et_p_find.getText().toString().trim();
                new_pwd_T=et_pqr_find.getText().toString().trim();
                if (phone.equals("")) {
                    ToastHelper.show(RetrievePwdActivity.this, "请输入手机号");
                    return;
                }
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone) != true) {
                    ToastHelper.show(RetrievePwdActivity.this, "手机号码格式不对");
                    return;
                }
                if (new_pwd.equals("")) {
                    ToastHelper.show(RetrievePwdActivity.this, "请输入密码");
                    return;
                }
                if(new_pwd.length()<6){
                    Toast.makeText(RetrievePwdActivity.this,"密码长度不能小于6位",Toast.LENGTH_LONG).show();
                    return;
                }
                if(new_pwd.length()>=18){
                    Toast.makeText(RetrievePwdActivity.this,"密码过长",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!new_pwd.equals(new_pwd_T)) {
                    ToastHelper.show(RetrievePwdActivity.this, "两次输入密码不一致");
                    return;
                }
                post();
            }
        });
        btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone=et_phone_find.getText().toString().trim();
                if (phone.equals("")) {
                    ToastHelper.show(RetrievePwdActivity.this, "请输入手机号");
                    return;
                }
                if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone) != true) {
                    ToastHelper.show(RetrievePwdActivity.this, "手机号码格式不对");
                    return;
                }
                getCode();
            }
        });
    }

    private void initTitle() {
        tv_back=findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrievePwdActivity.this.finish();
            }
        });
        tv_content=findViewById(R.id.tv_title);
        tv_content.setText("忘记密码");
    }


    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void post(){
        Map<String,String> map = new HashMap<>();
        map.put("phone",MD5Util.MD5(phone));
        map.put("new_pwd", MD5Util.MD5(new_pwd));

        HttpUtils.doPost(Constants.SERVER_BASE_URL+"system/sys/SysMemUserController/forgetPassword.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            ToastHelper.show(RetrievePwdActivity.this,"ERROR:"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result=response.body().string();
                    Log.i("result", "result:" + result);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 获取验证码
     */

    private void getCode() {
        /**
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        HttpUtils.doGet(Constants.SERVER_BASE_URL + "system/sys/sendMsgController/getResult.action?" + "phone=" + phone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.show(RetrievePwdActivity.this, "ERROR:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    CodeEntity codeEntity = gson.fromJson(result, CodeEntity.class);
                    verCode = codeEntity.getData().toString().trim();
                    ToastHelper.show(RetrievePwdActivity.this, codeEntity.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }
}
