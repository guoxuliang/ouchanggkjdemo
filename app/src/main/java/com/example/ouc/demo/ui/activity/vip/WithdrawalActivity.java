package com.example.ouc.demo.ui.activity.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.WithdrawalEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WithdrawalActivity extends BaseActivity {
    private EditText tx_cardNab, tx_Name, tx_address, tx_bankName, tx_phonenub, yx_je;
    private String tx_cardNab_str, tx_Name_str, tx_address_str, tx_bankName_str, tx_phonenub_str, yx_je_str;
    private Button tx_submit;
    private Gson gson = new Gson();
    private String id;
    private int code;
    private String msg;
    private WithdrawalEntity withdrawalEntity;
    private ImageView iv_right;
    private TextView tv_back, tv_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        id = getStringSharePreferences("id", "id");
        initView();
        initTitle();
    }
    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right= findViewById(R.id.iv_right);
        iv_right.setVisibility(View.GONE);
//        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
//        iv_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 点击分享
//            }
//        });
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithdrawalActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("我的提现");
    }
    private void initView() {
        tx_cardNab = findViewById(R.id.tx_cardNab);
        tx_cardNab.setInputType( InputType.TYPE_CLASS_NUMBER);
        tx_Name = findViewById(R.id.tx_Name);
        tx_address = findViewById(R.id.tx_address);
        tx_bankName = findViewById(R.id.tx_bankName);
        tx_phonenub = findViewById(R.id.tx_phonenub);
        tx_phonenub.setInputType( InputType.TYPE_CLASS_NUMBER);
        yx_je = findViewById(R.id.yx_je);
        yx_je.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tx_submit = findviewByid(R.id.tx_submit);
        tx_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx_cardNab_str = tx_cardNab.getText().toString().trim();
                tx_Name_str = tx_Name.getText().toString().trim();
                tx_address_str = tx_address.getText().toString().trim();
                tx_bankName_str = tx_bankName.getText().toString().trim();
                tx_phonenub_str = tx_phonenub.getText().toString().trim();
                yx_je_str = yx_je.getText().toString().trim();
                if (tx_cardNab_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "");
                    return;
                }
                if (tx_Name_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "");
                    return;
                }
                if (tx_address_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "");
                    return;
                }
                if (tx_bankName_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "");
                    return;
                }
                if (tx_phonenub_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "请输入电话号码");
                    return;
                }
                if (yx_je.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "金额不能为空");
                    return;
                }
                post_Tx();
            }
        });
    }

    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void post_Tx() {
        Map<String, String> map = new HashMap<>();
        map.put("userid", id);
        map.put("money", yx_je_str);
        map.put("type", "1");
        map.put("banknumber", tx_cardNab_str);
        map.put("username", tx_Name_str);
        map.put("bankaddress", tx_address_str);
        map.put("bankname", tx_bankName_str);
        map.put("alipayaccopunt", "");
        map.put("mobilephone", tx_phonenub_str);

        Log.i("", "");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/insertPresentrecord.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    withdrawalEntity = gson.fromJson(result, WithdrawalEntity.class);
                    code = withdrawalEntity.getCode();
                    msg = withdrawalEntity.getMsg();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (code == 200) {
                                ToastHelper.show(WithdrawalActivity.this, msg);
                                WithdrawalActivity.this.finish();
                            }else {
                                ToastHelper.show(WithdrawalActivity.this, msg);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
