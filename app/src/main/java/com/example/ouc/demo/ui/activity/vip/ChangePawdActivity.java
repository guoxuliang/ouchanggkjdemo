package com.example.ouc.demo.ui.activity.vip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.ChangePwdEntity;
import com.example.ouc.demo.entity.LoginEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.MainActivity;
import com.example.ouc.demo.ui.activity.LoginActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.MD5Util;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePawdActivity extends BaseActivity {
    /**
     * 修改密码
     */
    private ImageView iv_right;
    private TextView tv_back, tv_content;
    private Gson gosn=new Gson();
    private EditText new_pwd,old_pwd;
    private Button submit_changepwd;
    private String id,new_pwd_str,old_pwd_str;
    private ChangePwdEntity changePwdEntity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepawd);
        id=getStringSharePreferences("id","id");
       Log.i("{{{","旧密码:"+MD5Util.MD5("")) ;
        Log.i("{{{","新密码:"+MD5Util.MD5("123456"));
        initTitle();
        initViews();
    }

    private void initViews() {
        new_pwd = findviewByid(R.id.new_pwd);
        old_pwd = findviewByid(R.id.old_pwd);
        submit_changepwd = findviewByid(R.id.submit_changepwd);
        submit_changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO  提交修改密码接口
                if(id!=null){
                    new_pwd_str = new_pwd.getText().toString().trim();
                    old_pwd_str = old_pwd.getText().toString().trim();
                    postChangePwd();
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
                ChangePawdActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("修改密码");
    }

    private void postChangePwd(){
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("new_pwd",MD5Util.MD5(new_pwd_str));
        map.put("old_pwd",MD5Util.MD5(old_pwd_str));

        HttpUtils.doPost(Constants.SERVER_BASE_URL+"system/sys/SysMemUserController/updatePassword.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result=response.body().string();
                    Log.i("result", "result:" + result);
                     changePwdEntity=gosn.fromJson(result,ChangePwdEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (changePwdEntity.getCode()==200){
                                ToastHelper.show(ChangePawdActivity.this,changePwdEntity.getMsg());
                                ChangePawdActivity.this.finish();
                            }else {
                                ToastHelper.show(ChangePawdActivity.this,changePwdEntity.getMsg());
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
