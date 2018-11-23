package com.example.ouc.demo.ui.activity.vip;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.UpgradeEntity;
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

public class UpgradeMembersActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back, tv_content;
    private Button btn_vip, btn_bj, btn_cj;
    private UpgradeEntity upgradeEntity;
    private String id;
    private Gson gson = new Gson();

    private String money;//金额
    private String body;//升级类型
    private String attach;//附加信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrademembers);
        id = getStringSharePreferences("id", "id");
        initTitle();
        initViews();
    }

    private void initViews() {
        btn_vip = findviewByid(R.id.btn_vip);//vip会员
        btn_bj = findviewByid(R.id.btn_bj);//白金会员
        btn_cj = findviewByid(R.id.btn_cj);//超级会员
        btn_vip.setOnClickListener(this);
        btn_bj.setOnClickListener(this);
        btn_cj.setOnClickListener(this);
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpgradeMembersActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("会员升级");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_vip:
                //TODO 升级vip会员
                if (id == null) {
                    return;
                }
                if (money == null) {
                    ToastHelper.show(this, "金额不能为空");
                    return;
                }
                if (body == null) {
                    return;
                }
                if (attach == null) {
                    return;
                }
                money = "49999.00";
//                办卡，升级，续费
                body =btn_vip.getText().toString().trim();
                attach = id+","+"2"+","+"4";//2超级，3 白金 4 代理
                post_upgrade(id,money,body,attach);
                break;
            case R.id.btn_bj:
                //TODO 升级白金会员

                break;
            case R.id.btn_cj:
                //TODO 升级超级会员

                break;
        }
    }

    public void post_upgrade(String id, String total_fee, String body, String attach) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("money", money);//金额为int类型
            map.put("body", body);//升级   续费   办卡 （传入汉字即可）
            map.put("attach", attach);//eg(（等级+id+类型）  attach,     “2，99,1”)
            Log.i("", "");

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "wechat/auth/wxauthController/AppjsPay.action", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("dfsd", "dsfsd" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("post_isSM", "post_isSM" + result);
                        upgradeEntity = gson.fromJson(result, UpgradeEntity.class);
                        Log.i("post_isSM", "post_isSM" + upgradeEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                if (upgradeEntity.getCode() == 200) {
//                                    ToastHelper.show(this, upgradeEntity.getMsg());
//                                    }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
