package com.example.ouc.demo.weigets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.ouc.demo.R;

/**
 * 自定义 登录 输入 框
 * Created by Administrator on 2016/7/12.
 */
public class LoginEditText extends RelativeLayout implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    private Context context;
    private EditText editText;
    private ImageView drawbleIconView, drawbleDeleteView, drawbleVisableView;
    // 是否 是 密码 输入 框
    private boolean passwordTag;
    // 是否 显示 密码
    private boolean passwordDisplayTag;

    public LoginEditText(Context context) {
        this(context, null);
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        getArrrs(attrs);
    }


    private void init() {
        View viewGroup = LayoutInflater.from(context).inflate(R.layout.view_login_edittext, this);
        editText = (EditText) viewGroup.findViewById(R.id.editText);
        drawbleIconView = (ImageView) viewGroup.findViewById(R.id.drawbleIconView);
        drawbleDeleteView = (ImageView) viewGroup.findViewById(R.id.drawbleDeleteView);
        drawbleVisableView = (ImageView) viewGroup.findViewById(R.id.drawbleVisableView);
        setListener();
    }

    private void setListener() {
        drawbleDeleteView.setOnClickListener(this);
        drawbleVisableView.setOnClickListener(this);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
    }

    private void getArrrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginEditTextStyle);

        passwordTag = typedArray.getBoolean(R.styleable.LoginEditTextStyle_passwordTag, false);

        Drawable deleteDrawable = typedArray.getDrawable(R.styleable.LoginEditTextStyle_deleteDrawable);
        Drawable iconDrawable = typedArray.getDrawable(R.styleable.LoginEditTextStyle_iconDrawable);
        Drawable visibleDrawable = typedArray.getDrawable(R.styleable.LoginEditTextStyle_visibleDrawable);

        showOrHideDrawableView(drawbleDeleteView, deleteDrawable);
        showOrHideDrawableView(drawbleIconView, iconDrawable);


        setIsPassword(passwordTag, visibleDrawable);
        // 先默认 隐藏
        drawbleVisableView.setVisibility(GONE);
        drawbleDeleteView.setVisibility(GONE);
    }

    public void setIsPassword(boolean passwordTag, Drawable visibleDrawable) {
        if (passwordTag) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showOrHideDrawableView(drawbleVisableView, visibleDrawable);
        } else {
            showOrHideDrawableView(drawbleVisableView, null);
        }
    }

    public void setIconView(Drawable drawable) {
        showOrHideDrawableView(drawbleIconView, drawable);
    }


    /**
     * 显示 或 隐藏 图标
     *
     * @param imageView
     * @param drawable
     */
    public void showOrHideDrawableView(ImageView imageView, Drawable drawable) {
        if (drawable == null) {
            imageView.setVisibility(GONE);
        } else {
            imageView.setVisibility(VISIBLE);
            imageView.setImageDrawable(drawable);
        }
    }

    public Editable getEditText() {
        return editText.getText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawbleDeleteView:
                editText.setText("");
                break;
            case R.id.drawbleVisableView:
                if (!passwordDisplayTag) {
                    // 设置 EditText 的 input type  显示 密码
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏 密码
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                passwordDisplayTag = !passwordDisplayTag;
                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()) {
            // 当 输入 字符 长度 不为0 时 显示 删除 按钮
            if (s.length() > 0) {
                drawbleDeleteView.setVisibility(VISIBLE);
                if (passwordTag)
                    drawbleVisableView.setVisibility(VISIBLE);
            } else {
                drawbleDeleteView.setVisibility(GONE);
                drawbleVisableView.setVisibility(GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // 如果 EditText的 焦点 改变了  则相应的 隐藏 显示 功能 按钮
        if (!hasFocus) {
            drawbleDeleteView.setVisibility(GONE);
            drawbleVisableView.setVisibility(GONE);
        } else if (editText.getText().length() > 0) {
            drawbleDeleteView.setVisibility(VISIBLE);
            if (passwordTag)
                drawbleVisableView.setVisibility(VISIBLE);
        }
    }
}
