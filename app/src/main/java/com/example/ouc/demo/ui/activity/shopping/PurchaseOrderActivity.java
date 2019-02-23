package com.example.ouc.demo.ui.activity.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.RemoveShoppingCartEntity;
import com.example.ouc.demo.entity.ShoppingPayEntity;
import com.example.ouc.demo.entity.ShoppingUserInfoEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.ShoppingCartActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PurchaseOrderActivity extends BaseActivity implements View.OnClickListener {
    private TextView usershrName, usershrPhone, usershrAddress;
    private TextView spName, spPrice, spNum, spcount;
    private ImageView spimg;
    private Button submitorder;
    private TextView tv_title,tv_right;
    private ImageView iv_left;
    private Gson gson = new Gson();
    private ProgersssDialog progersssDialog;
    private ShoppingUserInfoEntity shoppingUserInfoEntity;
private String url = Constants.SERVER_BASE_URL +"app/bas/user/selectDefaultAddress.action?userid=";
private String img,name,num,price,productid,yanse;
private double price_d;
private ShoppingPayEntity shoppingPayEntity;

    private IWXAPI wxapi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaseorder);
        initTitle();
        initViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productid = bundle.getString("productid");
            img = bundle.getString("img");
            name = bundle.getString("name");
            num = bundle.getString("num");
            price = bundle.getString("price");
            price_d = Double.parseDouble(price);
            yanse = bundle.getString("yanse");
            setData();
        }
        wxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        wxapi.registerApp(Constants.APP_ID);
        progersssDialog = new ProgersssDialog(this);
        String userid = getStringSharePreferences("id","id");
        getShoppingUserInfo(url+userid);
    }

    private void setData() {
        spName.setText(name);
        spPrice.setText("￥:"+price);
        spNum.setText("x"+num);
        Glide.with(this).load(img).into(spimg);
//        double priceint = Double.parseDouble(price);
//        double numint = Double.parseDouble(num);
//        int count = (int) (priceint*numint);
        spcount.setText("￥:"+price+"");
    }
    private void initTitle() {
        iv_left  = findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("提交订单");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("获取订单管理");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 获取订单管理
                Intent intent=new Intent(PurchaseOrderActivity.this,OrderListActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initViews() {
        usershrName = findViewById(R.id.usershrName);
        usershrPhone = findViewById(R.id.usershrPhone);
        usershrAddress = findViewById(R.id.usershrAddress);
        spName = findViewById(R.id.spName);
        spPrice = findViewById(R.id.spPrice);
        spNum = findViewById(R.id.spNum);
        spcount = findViewById(R.id.spcount);
        spimg = findViewById(R.id.spimg);
        submitorder = findViewById(R.id.submitorder);
        submitorder.setOnClickListener(this);
        usershrAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitorder:
                 //TODO  调用支付接口
               String  body = spName.getText().toString().trim();
               String userid = getStringSharePreferences("id","id");
                String addressid = shoppingUserInfoEntity.getData().getId()+"";
               String attach =userid+",2"+productid+","+addressid+","+yanse+"*"+num;
                int total = (int) (price_d * 100);
//               String total_fee = price;
                String total_fee = String.valueOf(total);
                shoppingPay(body,attach,total_fee);
                break;
            case R.id.usershrAddress:
                //TODO  用户地址
                Intent intent=new Intent(this,AddressListActivity.class);
                startActivity(intent);
                break;
        }
    }
    /**
     * 接口名：shoppingPay
     * Get请求   商品支付请求接口
     */
    private void shoppingPay(String body,String attach,String total_fee) {
        Map<String, String> map = new HashMap<>();
        map.put("body", body);
        map.put("attach", attach);
        map.put("total_fee", total_fee);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/uline/pay/wechatAppPay.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    shoppingPayEntity = gson.fromJson(result, ShoppingPayEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (shoppingPayEntity.getCode() == 0) {
                                String nonceStr = shoppingPayEntity.getPageData().getNonceStr();
                                String prepayid = shoppingPayEntity.getPageData().getPrepayid();
                                String timeStamp = shoppingPayEntity.getPageData().getTimeStamp();
                                String paySign = shoppingPayEntity.getPageData().getPaySign();

                                SortedMap<Object, Object> params = new TreeMap<Object, Object>();
                                PayReq request = new PayReq();
                                request.appId = Constants.APP_ID;
                                request.partnerId = Constants.PARTNER_ID;
                                request.prepayId = prepayid;
                                request.packageValue = Constants.PACKAGE_VALUE;
                                request.nonceStr = nonceStr;
                                request.timeStamp = timeStamp;
                                request.sign = paySign;
//                                    ToastHelper.show(UpgradeMembersActivity.this,"获取订单中...");
                                wxapi.sendReq(request);
                            }else{
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 接口名：getShoppingUserInfo
     * Get请求   获取商品列表请求接口
     */
    private void getShoppingUserInfo(String url) {


        Log.i("url", "url:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    progersssDialog.dismiss();
                    Log.i("shoppingqueryListEntity", "shoppingqueryListEntity:" + result);
                    shoppingUserInfoEntity = gson.fromJson(result, ShoppingUserInfoEntity.class);
//                    Type listType2 = new TypeToken<ArrayList<ShoppingqueryListEntity.DataBean>>() {
//                    }.getType();//TypeToken内的泛型就是Json数据中的类型
//                    detailShopList = gson.fromJson(gson.toJson(shoppingGoodsDetailEntity.getData()), listType2);

//                    Log.i("queryShopList", "queryShopList" + detailShopList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingUserInfoEntity.getCode();
                            if (code==200) {
                              String city = shoppingUserInfoEntity.getData().getCity();
                                String detailed_address = shoppingUserInfoEntity.getData().getDetailed_address()+"";
                                String province = shoppingUserInfoEntity.getData().getProvince()+"";
                                String district = shoppingUserInfoEntity.getData().getDistrict()+"";
                                String delivename = shoppingUserInfoEntity.getData().getDelivename()+"";
                                String phone = shoppingUserInfoEntity.getData().getPhone()+"";
                                String id = shoppingUserInfoEntity.getData().getId()+"";
                                String isdefault = shoppingUserInfoEntity.getData().getIsdefault()+"";
                                String userid = shoppingUserInfoEntity.getData().getUserid()+"";

                                usershrName.setText("收货人:"+delivename);
                                usershrPhone.setText(phone);
                                usershrAddress.setText("收货地址:"+province+city+district+detailed_address);
                            }else if(code==405){
                               ToastHelper.show(PurchaseOrderActivity.this,"请设置默认地址");
                                final AlertDialog.Builder normalDialog =
                                        new AlertDialog.Builder(PurchaseOrderActivity.this);
                                normalDialog.setIcon(R.drawable.icon_dialog);
                                normalDialog.setTitle("");
                                normalDialog.setMessage("请设置默认地址");
                                normalDialog.setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //...To-do
                                               Intent intent = new Intent(PurchaseOrderActivity.this,AddressListActivity.class);
                                               startActivity(intent);
                                               finish();
                                            }
                                        });
                                normalDialog.setNegativeButton("取消",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //...To-do
                                                PurchaseOrderActivity.this.finish();
                                            }
                                        });
                                // 显示
                                normalDialog.show();

                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("e", "e" + e);
                }

            }
        });
    }


}
