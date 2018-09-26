package com.example.ouc.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.ui.MainActivity;

public class WebMoreActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etU,etP;
    private Button btn;
    private Intent intent;
    private TextView registered,forgot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etU=findViewById(R.id.etU);
        etP=findViewById(R.id.etP);
        registered=findViewById(R.id.registered);
        forgot=findViewById(R.id.forgot);
        btn=findViewById(R.id.submit);

        forgot.setOnClickListener(this);
        registered.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registered:
                intent=new Intent(WebMoreActivity.this, RegisteredActivity.class);
                startActivity(intent);
                break;
            case R.id.forgot:
                intent=new Intent(WebMoreActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.submit:
                if(etU!=null&&etU.length()==16){
                    Toast.makeText(WebMoreActivity.this,"请输入用户名",Toast.LENGTH_LONG).show();
                    return;
                }
                if(etP==null){
                    Toast.makeText(WebMoreActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
                    return;
                }
                intent=new Intent(WebMoreActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
