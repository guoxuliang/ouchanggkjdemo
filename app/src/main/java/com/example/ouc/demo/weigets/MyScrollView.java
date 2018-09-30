package com.example.ouc.demo.weigets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //不拦截，继续分发下去
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
