package com.example.ouc.demo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.weigets.ImageLoadingDialog;

public class ImageShowerPhotoActivity extends BaseActivity {
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower);
        initViews();

    }

    private void initViews() {
        iv = (ImageView) findviewByid(R.id.iv);

        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.show();
        // 两秒后关闭后dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000 * 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return true;
    }

}
