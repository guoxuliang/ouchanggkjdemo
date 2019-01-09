package com.example.ouc.demo.ui.activity.vip;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.GetPhoneNumberEntity;
import com.example.ouc.demo.entity.SubmitInfoEntity;
import com.example.ouc.demo.entity.UpgradeEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.ShoppingBuyActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TransactNubActivity extends BaseActivity{
    private String id,level,level_str;
    private String total_fee,item,attachs;
    private TextView tv_back, tv_content;
//    private ProgersssDialog progersssDialog;
    private UpgradeEntity upgradeEntity;
    private GetPhoneNumberEntity getPhoneNumberEntity;
    private SubmitInfoEntity submitInfoEntity;
    private Gson gson = new Gson();
    private IWXAPI wxapi;
    List<GetPhoneNumberEntity.DataBean> getPhoneData=new ArrayList<GetPhoneNumberEntity.DataBean>();
    private Button wxSubmit;

    private String[] cates=new String[5];
    private ArrayAdapter<String> adapter;
    private String phone;
    private TextView dengji,total;
    private Spinner sp;
    private EditText newname,newidCard,newmobilephone,newaddress;
    private TextView bignewaddress;
    private String mobilephone_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID,false);
        wxapi.registerApp(Constants.APP_ID);
        id = getStringSharePreferences("id", "id");
        level = getStringSharePreferences("level","level");
        setContentView(R.layout.activity_transact);
        initTitle();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            total_fee = bundle.getString("total_fee");
            item = bundle.getString("item");
            attachs = bundle.getString("attachs");
        }
        initViews();
        getPhoneNumber();
    }

    private void initViews() {
        dengji = findviewByid(R.id.dengji);
        total = findviewByid(R.id.total);
        if(level.equals("1")){
            return;
        }
        if(level.equals("2")){
            level_str="超级会员";
        }
        if(level.equals("3")){
            level_str="白金会员";
        }
        if(level.equals("4")){
            level_str="VIP代理";
        }
        dengji.setText(level_str);
        total.setText(total_fee);

        bignewaddress = findviewByid(R.id.bignewaddress);
        newname = findViewById(R.id.newname);
        newidCard = findViewById(R.id.newidCard);
        newmobilephone = findViewById(R.id.newmobilephone);
        newaddress = findViewById(R.id.newaddress);
        sp = findViewById(R.id.sp);
        sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        bignewaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAddress();
            }
        });
        wxSubmit = findViewById(R.id.wxSubmit);
        wxSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progersssDialog = new ProgersssDialog(TransactNubActivity.this);
//                progersssDialog.setCancelable(false);
                String name_str=newname.getText().toString().trim();
                String newidCard_str = newidCard.getText().toString().trim();
                String newmobilephone_str = newmobilephone.getText().toString().trim();
                String newaddress_str = newaddress.getText().toString().trim();
                String bignewaddress_str = bignewaddress.getText().toString().trim();
                if(id.equals("")){
    ToastHelper.show(TransactNubActivity.this,"用户id不能为空");
    return;
}
                if(name_str.equals("")){
                    ToastHelper.show(TransactNubActivity.this,"姓名不能为空");
                    return;
                }
                if(newidCard_str.equals("")){
                    ToastHelper.show(TransactNubActivity.this,"身份证不能为空");
                    return;
                }
                if(newmobilephone_str.equals("")){
                    ToastHelper.show(TransactNubActivity.this,"手机号不能为空");
                    return;
                }
                if(newaddress_str.equals("")){
                    ToastHelper.show(TransactNubActivity.this,"详细地址不能为空");
                    return;
                }
                if(bignewaddress_str.equals("")){
                    ToastHelper.show(TransactNubActivity.this,"地址不能为空");
                    return;
                }
                      String ddress = bignewaddress_str+newaddress_str;
                    submitInfo(id,name_str,newidCard_str,mobilephone_str,ddress,newmobilephone_str);
//                    ToastHelper.show(TransactNubActivity.this,"请选择办理的号码");


            }
        });

    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactNubActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("会员升级");
    }

    /**
     * 获取选择的手机号码
     */

    public void getPhoneNumber() {
        try {
            Map<String, String> map = new HashMap<>();
            Log.i("", "");


            HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/selectFivePhone.action", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("dfsd", "dsfsd" + e);
//                    progersssDialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        final String result = response.body().string();
                        Log.i("getPhoneNumber", "result:getPhoneNumber" + result);
                        getPhoneNumberEntity = gson.fromJson(result, GetPhoneNumberEntity.class);
                        Log.i("getPhoneNumber", "getPhoneNumber" + getPhoneNumberEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getPhoneNumberEntity.getCode() == 200) {
//                                    progersssDialog.dismiss();
                                    getPhoneData=getPhoneNumberEntity.getData();
                                    Log.i("getPhoneData","getPhoneData:"+getPhoneData);
                                    for(int i=0;i<=getPhoneData.size()-1;i++){
                                        cates[i]=getPhoneData.get(i).getPhone();

                                    }
                                    Log.i("数组的值:","数组的值:"+cates[0]+","+cates[1]+","+cates[2]+","+cates[3]+","+cates[4]);
                                    adapter = new ArrayAdapter<String>(TransactNubActivity.this,android.R.layout.simple_spinner_item, cates);
                                    //设置下拉列表的风格
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    sp.setAdapter(adapter);
                                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            mobilephone_str=adapter.getItem(i);
                                            ToastHelper.show(TransactNubActivity.this,"您选中了："+adapter.getItem(i));
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
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

    /**
     * 提交选中的号码及客户信息
     */
    public void submitInfo(final String id,final String name,final String idCard,final String mobilephone,final String address,final String phone) {
        try {
            Map<String, String> map = new HashMap<>();
//            map.put("id", id);
//            map.put("name", "账单");
//            map.put("idCard", "610122199902121234");
//            map.put("mobilephone", "13820080828");
//            map.put("address", "陕西省西安市新城区五路口民安大厦A座746");
//            map.put("phone", "19928751953");
            map.put("id", id);
            map.put("name", name);
            map.put("idCard", idCard);
            map.put("mobilephone", mobilephone);
            map.put("address", address);
            map.put("phone", phone);
            Log.i("", "");


            HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/insertPhoneInfo.action", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("dfsd", "dsfsd" + e);
//                    progersssDialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        final String result = response.body().string();
                        Log.i("submitInfo", "result:submitInfo" + result);
                        submitInfoEntity = gson.fromJson(result, SubmitInfoEntity.class);
                        Log.i("submitInfo", "submitInfo" + submitInfoEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (submitInfoEntity.getCode() == 200) {
//                                    progersssDialog.dismiss();
                                    ToastHelper.show(TransactNubActivity.this,"提交成功");
                                    post_upgrade(id,total_fee,"畅享App-"+item,attachs);
                                }
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
    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     * 微信支付---获取订单id接口
     */
    public void post_upgrade(String id, String total_fee, String body, String attach) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("money", total_fee);//金额为int类型
            map.put("body", body);//2升级   3续费   1办卡 （传入汉字即可）
            map.put("attach", attach);//eg(（id+类型+等级【要升的等级】）  attach,     “1，2,3”)
            Log.i("", "");

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "wechat/auth/wxauthController/AppjsPay.action", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("dfsd", "dsfsd" + e);
//                    progersssDialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        final String result = response.body().string();
                        Log.i("resultpost_upgrade", "result:post_upgrade" + result);
                        upgradeEntity = gson.fromJson(result, UpgradeEntity.class);
                        Log.i("post_upgrade", "post_upgrade" + upgradeEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (upgradeEntity.getCode() == 0) {
//                                    progersssDialog.dismiss();
                                    String nonceStr = upgradeEntity.getPageData().getNoncestr();
                                    String prepayid = upgradeEntity.getPageData().getPrepayid();
                                    String timeStamp = upgradeEntity.getPageData().getTimestamp();
                                    String paySign = upgradeEntity.getPageData().getSign();

                                    PayReq request = new PayReq();
                                    request.appId = Constants.APP_ID;
                                    request.partnerId =Constants.PARTNER_ID;
                                    request.prepayId = prepayid;
                                    request.packageValue = Constants.PACKAGE_VALUE;
                                    request.nonceStr = nonceStr;
                                    request.timeStamp = timeStamp;
                                    request.sign = paySign;
                                    wxapi.sendReq(request);
                                }
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

    /**
     * 选择地址
     */
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(TransactNubActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("陕西省")
                .city("西安市")
                .district("新城区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(20)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                bignewaddress.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }
}
