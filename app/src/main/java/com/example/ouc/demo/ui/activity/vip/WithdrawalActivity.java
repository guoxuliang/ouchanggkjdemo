package com.example.ouc.demo.ui.activity.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.AuditEntity;
import com.example.ouc.demo.entity.WithdrawalEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.WithdrawalRecordActivity;
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
    private EditText tx_cardNab, tx_Name, tx_address, tx_bankName, tx_phonenub, yx_je,tx_zfbNab,tx_zfbName,tx_zfbPhone,tx_zfbJe;
    private String tx_cardNab_str, tx_Name_str, tx_address_str, tx_bankName_str, tx_phonenub_str,yx_je_str;
    private Button tx_submit;
    private Gson gson = new Gson();
    private String id;
    private int code;
    private String msg;
    private WithdrawalEntity withdrawalEntity;
    private ImageView iv_right;
    private TextView tv_back, tv_content,tv_right,my_zhye;
    private RadioGroup rg_select_tx;
    private RadioButton rb_yhk_tx, rb_zfb_tx;
    private String loggersName;
    private String tx_type="1";
    private LinearLayout zfb_layout,yhk_layout;

    private AuditEntity auditEntity;
    private String data;//判断审核的状态  受理状态  1待处理 2 已处理
    private String zhye,hylevel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        id = getStringSharePreferences("id", "id");
        zhye = getStringSharePreferences("commission", "commission");
        hylevel = getStringSharePreferences("level","level");
        initView();
        initTitle();
        //  TODO 调判断是否审核的接口
        AuditState();
    }
    private void initTitle() {
        tv_right = findViewById(R.id.tv_right);
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
        tv_right.setText("提现记录");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(WithdrawalRecordActivity.class);
            }
        });
    }
    private void initView() {
        yhk_layout = findViewById(R.id.yhk_layout);
        zfb_layout = findViewById(R.id.zfb_layout);
        tx_cardNab = findViewById(R.id.tx_cardNab);
        my_zhye = findViewById(R.id.my_zhye);
        tx_cardNab.setInputType( InputType.TYPE_CLASS_NUMBER);
        tx_Name = findViewById(R.id.tx_Name);
        tx_address = findViewById(R.id.tx_address);
        tx_bankName = findViewById(R.id.tx_bankName);
        tx_phonenub = findViewById(R.id.tx_phonenub);
        tx_phonenub.setInputType( InputType.TYPE_CLASS_NUMBER);

        yx_je = findViewById(R.id.yx_je);
        yx_je.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        tx_submit = findviewByid(R.id.tx_submit);
        if(hylevel.equals("5")){
            tx_submit.setBackgroundColor(R.color.gray);
            tx_submit.setText("体验用户不可提现");
            tx_submit.setEnabled(false);


            showToast("体验用户不可体现");
            return;
        }
        rg_select_tx = (RadioGroup) findViewById(R.id.rg_select_tx);
        rb_yhk_tx = (RadioButton) findViewById(R.id.rb_yhk_tx);
        rb_zfb_tx = (RadioButton) findViewById(R.id.rb_zfb_tx);
        //支付宝提现初始化
        tx_zfbNab = findViewById(R.id.tx_zfbNab);
        tx_zfbName = findViewById(R.id.tx_zfbName);
        tx_zfbPhone= findViewById(R.id.tx_zfbPhone);
        tx_zfbPhone.setInputType( InputType.TYPE_CLASS_NUMBER);

        tx_zfbJe= findViewById(R.id.tx_zfbJe);
        tx_zfbJe.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        rg_select_tx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yhk_tx.getId() == i) {
                    // 广告类型  1图文广告 2 视频广告
                    loggersName = rb_yhk_tx.getText().toString();
                    tx_type = "1";
                    yhk_layout.setVisibility(View.VISIBLE);
                    zfb_layout.setVisibility(View.GONE);
                }
                if (rb_zfb_tx.getId() == i) {
                    // 广告类型  1图文广告 2 视频广告
                    loggersName = rb_zfb_tx.getText().toString();
                    tx_type = "2";
                    yhk_layout.setVisibility(View.GONE);
                    zfb_layout.setVisibility(View.VISIBLE);
                }
                Toast.makeText(WithdrawalActivity.this, "你选择了：" + loggersName, Toast.LENGTH_SHORT).show();
            }
        });


        tx_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tx_type.equals("1")){
                tx_cardNab_str = tx_cardNab.getText().toString().trim();
                tx_Name_str = tx_Name.getText().toString().trim();
                tx_address_str = tx_address.getText().toString().trim();
                tx_bankName_str = tx_bankName.getText().toString().trim();
                tx_phonenub_str = tx_phonenub.getText().toString().trim();
                yx_je_str = yx_je.getText().toString().trim();
                if (tx_cardNab_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "银行卡号不能为空");
                    return;
                }
                if (tx_Name_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "户名不能为空");
                    return;
                }
                if (tx_address_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "地区支行地址不能为空");
                    return;
                }
                if (tx_bankName_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "开户银行不能为空");
                    return;
                }
                if (tx_phonenub_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "电话号码不能为空");
                    return;
                }
                if (yx_je_str.equals("")) {
                    ToastHelper.show(WithdrawalActivity.this, "金额不能为空");
                    return;
                }
                double zhyeint= Double.parseDouble(yx_je_str);
                double zhyebundle= Double.parseDouble(zhye);
                if(zhyeint>zhyebundle){
                    ToastHelper.show(WithdrawalActivity.this, "账户余额不足");
                    return;
                }
                    post_Tx();
                }else if(tx_type.equals("2")){
                    tx_cardNab_str = tx_zfbNab.getText().toString().trim();
                    tx_Name_str=tx_zfbName.getText().toString().trim();
                    tx_phonenub_str=tx_zfbPhone.getText().toString().trim();
                    yx_je_str = tx_zfbJe.getText().toString().trim();
                    if(tx_cardNab_str.equals("")){
                        ToastHelper.show(WithdrawalActivity.this, "支付宝账号不能为空");
                        return;
                    }
                    if(tx_Name_str.equals("")){
                        ToastHelper.show(WithdrawalActivity.this, "真实姓名不能为空");
                        return;
                    }
                    if(tx_phonenub_str.equals("")){
                        ToastHelper.show(WithdrawalActivity.this, "手机号码不能为空");
                        return;
                    }
                    if(yx_je_str.equals("")){
                        ToastHelper.show(WithdrawalActivity.this, "金额不能为空");
                        return;
                    }
                    double zhyeint2= Double.parseDouble(yx_je_str);
                    double zhyebundle2= Double.parseDouble(zhye);
                    if(zhyeint2>zhyebundle2){
                        ToastHelper.show(WithdrawalActivity.this, "账户余额不足");
                        return;
                    }
                    post_Tx();
                }

            }
        });
        if(!zhye.equals("")){
            my_zhye.setText("您的账户余额："+zhye+"元");
        }
    }

    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void post_Tx() {
        try {
            Map<String, String> map = new HashMap<>();
            if(tx_type=="1"){
        map.put("userid", id);
//                map.put("userid", "5");
                map.put("money", yx_je_str);
                map.put("type", tx_type);
                map.put("banknumber", tx_cardNab_str);
                map.put("banknumber", tx_cardNab_str);
                map.put("username", tx_Name_str);
                map.put("bankaddress", tx_address_str);
                map.put("bankname", tx_bankName_str);
                map.put("mobilephone", tx_phonenub_str);
            }else {
//                map.put("userid", "5");
                map.put("userid", id);
                map.put("money", yx_je_str);
                map.put("type", tx_type);
                map.put("alipayaccopunt", tx_cardNab_str);
                map.put("username", tx_Name_str);
                map.put("mobilephone", tx_phonenub_str);
            }
            Log.i("map", "map"+map);
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
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     * 审核状态接口
     */
    private void AuditState() {
        Map<String, String> map = new HashMap<>();
        map.put("userid", id);
//        map.put("userid", "5");
        Log.i("", "");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemPresentRecordController/getRecordtype.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:AuditState" + result);
                    auditEntity = gson.fromJson(result, AuditEntity.class);
                    code = auditEntity.getCode();
                    msg = auditEntity.getMsg();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (code == 200) {
                                ToastHelper.show(WithdrawalActivity.this, msg);
                                data=auditEntity.getData();
//                                if(data.equals("1")){
//                                    tx_submit.setBackgroundColor(R.color.gray);
//                                    tx_submit.setText("审核中...");
//                                    tx_submit.setEnabled(false);
//                                }
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
