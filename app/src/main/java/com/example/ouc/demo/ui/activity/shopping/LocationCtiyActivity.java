package com.example.ouc.demo.ui.activity.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.weigets.MyLetterView;

public class LocationCtiyActivity extends AppCompatActivity {
    private MyLetterView myLetterView;
    private TextView tvDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationctiy);
        myLetterView=(MyLetterView) findViewById(R.id.my_letterview);
        tvDialog=(TextView) findViewById(R.id.tv_dialog);
        //将中间展示字母的TextView传递到myLetterView中并在其中控制它的显示与隐藏
        myLetterView.setTextView(tvDialog);
        //注册MyLetterView中监听(跟setOnClickListener这种系统默认写好的监听一样只不过这里是我们自己写的)
        myLetterView.setOnSlidingListener(new MyLetterView.OnSlidingListener() {

            @Override
            public void sliding(String str) {
                tvDialog.setText(str);
            }
        });
    }
}