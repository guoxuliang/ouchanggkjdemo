package com.example.ouc.demo.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.InterestedBuyersEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InterestedBuyersActivity extends BaseActivity {
    private TextView tv_back, tv_content;
    private ImageView iv_right;
    private EditText buyersname,buyersphone;
    private Spinner buyerstype;
    private Button buyerssubmit;
    private Gson gson = new Gson();
    private String  type,type2="1";
    private String BuyersUserName,BuyersUserPhone,BuyersUserType;
    private InterestedBuyersEntity interestedBuyersEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interestedbuyers);
        initTitle();
        initViews();
        initDtata();
    }

    private void initDtata() {
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("课程教学");
        spinners.add("美妆教学");
        spinners.add("书籍");
        //设置ArrayAdapter内置的item样式-这里是单行显示样式
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners);
        //这里设置的是Spinner的样式 ， 输入 simple_之后会提示有4人，如果专属spinner的话应该是俩种，在特殊情况可自己定义样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //设置Adapter了
        buyerstype.setAdapter(adapter);
        //监听Spinner的操作
        buyerstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //选取时候的操作
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mTv.setText(adapter.getItem(position));//获取选中的item
                type = adapter.getItem(position);
                if(type=="课程教学"){
                    type2="1";
                }else if(type=="美妆教学"){
                    type2="2";
                }else if(type=="书籍"){
                    type2="3";
                }
                Log.i("===","type:"+type+"type2："+type2);

            }

            //没被选取时的操作
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                mTv.setText("No anything");
            }
        });


        buyerssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //TODO
                BuyersUserName = buyersname.getText().toString().trim();
                BuyersUserPhone = buyersphone.getText().toString().trim();
                BuyersUserType = type2;
                if(BuyersUserName.equals("")){
                    ToastHelper.show(InterestedBuyersActivity.this,"姓名不能为空");
                    return;
                }
                if(BuyersUserPhone.equals("")){
                    ToastHelper.show(InterestedBuyersActivity.this,"电话不能为空");
                    return;
                }
                InterestedBuyers(BuyersUserPhone,BuyersUserName,BuyersUserType,"1");
            }
        });
    }

    private void initViews() {
        buyersname = findviewByid(R.id.buyersname);
        buyersphone = findviewByid(R.id.buyersphone);
        buyersphone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        buyerstype = findviewByid(R.id.buyerstype);
        buyerssubmit = findviewByid(R.id.buyerssubmit);
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right= findViewById(R.id.iv_right);
        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
        iv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterestedBuyersActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("意向客户");
    }

    /**
     * Post请求  完成任务接口
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void InterestedBuyers(String phone,String username,String type,String way){
        Map<String,String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("username", username);
        map.put("type", type);
        map.put("way", way);

        HttpUtils.doPost(Constants.SERVER_BASE_URL+"system/sys/SysMemTempUserController/saveTempInfo.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result=response.body().string();
                    Log.i("result", "result:" + result);
                    interestedBuyersEntity=gson.fromJson(result,InterestedBuyersEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (interestedBuyersEntity.getCode()==200){
                                ToastHelper.show(InterestedBuyersActivity.this,interestedBuyersEntity.getMsg());
                                InterestedBuyersActivity.this.finish();
                            }else {
                                ToastHelper.show(InterestedBuyersActivity.this,interestedBuyersEntity.getMsg());
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
