package com.example.ouc.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;

public class DeliveryActivity extends BaseActivity{
    private ImageView iv_right;
    private TextView tv_back, tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        initTitle();
    }
    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right=findviewByid(R.id.iv_right);
        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
        iv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("广告投放");
    }
}
