package com.example.ouc.demo.weigets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;

import com.example.ouc.demo.R;

/**
 * Created by NanFeiLong on 2017/4/16.
 */
public class MyCloseDialog extends Dialog implements View.OnClickListener {
    private ImageView img_back;
    private Context mContext;

    public MyCloseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_closeicon_layout);
        img_back = (ImageView) findViewById(R.id.close);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                MyCloseDialog.this.dismiss();
                break;
            default:
                break;
        }
    }
}