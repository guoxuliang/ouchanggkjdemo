package com.example.ouc.demo.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.LoginEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.MainActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.MD5Util;
import com.example.ouc.demo.utils.PhoneFormatCheckUtils;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.ClearEditText;
import com.example.ouc.demo.weigets.EyeEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ClearEditText etU;
    private EyeEditText etP;
    private Button btn;
    private Intent intent;
    private TextView registered, forgot;

    private String phone;    //true	String	会员手机号
    private String password;    //true	String	会员密码
    private Gson gosn = new Gson();

    private int code, id;
    private String msg, status, name, level, mobilePhone, headImg, commendNo, personNo, username, createTime, email, cardFaceImg, ways, openId, beginTime, cardNumber, cardConImg, referee;
    private String is_login="0";
    private LoginEntity loginEntity;
    private long endtime;
    private String endtime2;
//    private String msgError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        is_login = getStringSharePreferences("is_login", "is_login");
        initView();
    }

//    private void LoginDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Look at this dialog!")
//                .setCancelable(false)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        try {
//                            java.lang.reflect.Field field = builder.getClass().getSuperclass().getDeclaredField("mShowing");
//                            field.setAccessible(true);
//                            field.set(builder, false);
//                            System.exit(0);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//
//    }

    private void initView() {
        etU = findViewById(R.id.etU);
        etP = findViewById(R.id.etP);
        registered = findViewById(R.id.registered);
        forgot = findViewById(R.id.forgot);
        btn = findViewById(R.id.submit);
        forgot.setOnClickListener(this);
        registered.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered:
                intent = new Intent(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent);
                break;
            case R.id.forgot:
                intent = new Intent(LoginActivity.this, RetrievePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.submit:
                phone = etU.getText().toString().trim();
                password = etP.getText().toString().trim();
                if (phone.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (PhoneFormatCheckUtils.isMobileNO(phone) != true) {
                    ToastHelper.show(LoginActivity.this, "手机号码格式不对");
                    return;
                }

                if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "密码长度不能小于6位", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() >= 18) {
                    Toast.makeText(LoginActivity.this, "密码过长", Toast.LENGTH_LONG).show();
                    return;
                }
//                is_login = getStringSharePreferences("is_login", "is_login");
//                if (is_login.equals("1")) {
//                    LoginDialog();
//                    return;
//                }
                post();

                break;
        }
    }

    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void post() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", MD5Util.MD5(password));
        Log.i("phone", "phone:" + MD5Util.MD5(phone) + "password:" + MD5Util.MD5(password));

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemloginController/login.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    loginEntity = gosn.fromJson(result, LoginEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (loginEntity.getCode() == 200) {
                                msg = loginEntity.getMsg();
                                status = loginEntity.getData().getStatus();
                                is_login = loginEntity.getData().isIs_login();
                                id = loginEntity.getData().getId();
                                name = String.valueOf(loginEntity.getData().getName());
                                level = String.valueOf(loginEntity.getData().getLevel());//会员等级
                                endtime = loginEntity.getData().getEndTime();//会员结束时间
                                String endtimestr= String.valueOf(endtime);
                                if(!endtimestr.equals("0")){
                                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                                    endtime2=sdf.format(endtime);
                                }

                                mobilePhone = String.valueOf(loginEntity.getData().getMobilePhone());//手机号
                                headImg = String.valueOf(loginEntity.getData().getHeadImg());//用户头像
                                commendNo = String.valueOf(loginEntity.getData().getCommendNo());//推荐码
                                personNo = String.valueOf(loginEntity.getData().getPersonNo());//邀请码
                                username = String.valueOf(loginEntity.getData().getUsername());//昵称
                                Log.i("is_login", "is_login:is_login:is_login:" + is_login);
                                setStringSharedPreferences("is_login", "is_login", is_login);
                                setStringSharedPreferences("id", "id", String.valueOf(id));
                                setStringSharedPreferences("name", "name", name);
                                setStringSharedPreferences("level", "level", level);
                                setStringSharedPreferences("endtime2", "endtime2", endtime2);
                                setStringSharedPreferences("mobilePhone", "mobilePhone", mobilePhone);
                                setStringSharedPreferences("headImg", "headImg", headImg);
                                setStringSharedPreferences("commendNo", "commendNo", commendNo);
                                setStringSharedPreferences("personNo", "personNo", personNo);


                                ToastHelper.show(LoginActivity.this, loginEntity.getMsg());
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else if(loginEntity.getCode() == 405){
                                ToastHelper.show(LoginActivity.this, loginEntity.getMsg());
                                String msgError=loginEntity.getMsg();
                                showCustomizeDialog(msgError);
                            }else {
                                ToastHelper.show(LoginActivity.this, loginEntity.getMsg());
                            }
                        }
                    });

                    Log.i("====", "保存的数据：" + getStringSharePreferences("is_login", "is_login"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void showCustomizeDialog(String msgError){
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(LoginActivity.this);
        final View dialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_customize,null);
        customizeDialog.setTitle("错误信息");
        customizeDialog.setView(dialogView);
        TextView edit_text =(TextView)dialogView.findViewById(R.id.edit_text);
        edit_text.setText("您的账号已在其他设备上登录,不能重复登录哦!");
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //获取EditView中的输入内容

                    }
                });
        customizeDialog.setCancelable(false);
        customizeDialog.show();
    }

}
