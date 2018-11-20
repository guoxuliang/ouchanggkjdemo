package com.example.ouc.demo.weigets;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class BottomWebView extends WebView {
    public ScrollInterface mScrollInterface;
    public BottomWebView(Context context) {
        super(context);
    }
    public BottomWebView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }



    public BottomWebView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    @Override

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {



        super.onScrollChanged(l, t, oldl, oldt);



        mScrollInterface.onSChanged(l, t, oldl, oldt);



    }



    public void setOnCustomScroolChangeListener(ScrollInterface scrollInterface) {



        this.mScrollInterface = scrollInterface;



    }



    public interface ScrollInterface {



        public void onSChanged(int l, int t, int oldl, int oldt);



    }



}

