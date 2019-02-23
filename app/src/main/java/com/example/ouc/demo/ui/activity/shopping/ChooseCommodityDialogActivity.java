package com.example.ouc.demo.ui.activity.shopping;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.ShoppingCartColorAdapter;
import com.example.ouc.demo.entity.AddShoppingCartEntity;
import com.example.ouc.demo.entity.DeviceEntity;
import com.example.ouc.demo.entity.ShoppingSpecsEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constant;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by an on 2017/6/15.
 */

/**
 * 选择商品样式
 */
public class ChooseCommodityDialogActivity extends Activity implements View.OnClickListener {
    private ImageView commodityImgSmall;
    private TextView commodityMoney;
    private TextView commodityName;
    private TextView select_yanse;

    private LinearLayout attritude;
    private GridView gvAttributeColor;
    /* @ViewInject(R.id.color2)
     Button commodityColor2;*/
    private Button sureAddShopCart, sureAddShopCart2;

    private TextView subtractNumber;    //减少数量
    private TextView addNumber;        //增加数量
    private TextView shopNumber;


    private int commodityNmber;

    private String productid, memberid;
    private static String TAG = "ChooseCommodityDialogActivity";

    private ShoppingCartColorAdapter attributeColorAdapter;
    List<String> listColor;
    ChooseCommodityDialogActivity.InnerBroadcastReceiver receiver;
    private String seledtedSize;
    private String seledtedColor = "";
    private String name = "";
    private String price = "";
    private String img = "";
    private String flag = "";
    private double price_d;
    private double numb;
    private ProgersssDialog progersssDialog;
    private Gson gson = new Gson();
    private ArrayList<ShoppingSpecsEntity.DataBean> dataShopSpecs;
    private ShoppingSpecsEntity shoppingSpecsEntity;
    private String url = Constants.SERVER_BASE_URL + "app/goodsInfo/getAllProductattrByProductid.action?productid=";
    private AddShoppingCartEntity addShoppingCartEntity;

    private String strnum="";
    private String  guiGe="";
    private String pricecount = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productid = bundle.getString("productid");
            memberid = bundle.getString("memberid");
            Log.i("productid", "productid:" + productid);
            flag = bundle.getString("flag");
        }


        progersssDialog = new ProgersssDialog(this);
        initView();
        registerReceiver();
        getShoppingSpecs(url + productid);
        Log.i("productid", "url:" + url + productid);
//        addData();
        subtractNumber.setOnClickListener(this);
        addNumber.setOnClickListener(this);
        attritude.setOnClickListener(this);
        sureAddShopCart.setOnClickListener(this);
        sureAddShopCart2.setOnClickListener(this);
    }

    private void initView() {

        commodityImgSmall = (ImageView) findViewById(R.id.commidity_img_small);
        commodityMoney = (TextView) findViewById(R.id.commodity_money);
        commodityName = (TextView) findViewById(R.id.commodity_name);
        select_yanse = (TextView)findViewById(R.id.select_yanse);
        attritude = (LinearLayout) findViewById(R.id.attritude);

        gvAttributeColor = (GridView) findViewById(R.id.gv_attribute_color);
        sureAddShopCart = (Button) findViewById(R.id.add_shop_cart);
        sureAddShopCart2 = (Button) findViewById(R.id.add_shop_cart2);
        if (flag!=null) {
            sureAddShopCart.setVisibility(View.GONE);
            sureAddShopCart2.setVisibility(View.VISIBLE);
        }else{
            sureAddShopCart.setVisibility(View.VISIBLE);
            sureAddShopCart2.setVisibility(View.GONE);
        }
        subtractNumber = (TextView) findViewById(R.id.subtract_number);
        addNumber = (TextView) findViewById(R.id.add_number);
        shopNumber = (TextView) findViewById(R.id.shop_number);
    }

    private void addData() {
//        listColor = new ArrayList<>();
//        listColor.add("大红");
//        listColor.add("驼色");
//        listColor.add("咖啡");
//        listColor.add("紫色");
//        listColor.add("紫色");
//        listColor.add("紫色");

        if (dataShopSpecs.size() > 0) {
            attributeColorAdapter = new ShoppingCartColorAdapter(ChooseCommodityDialogActivity.this, dataShopSpecs, gvAttributeColor);
            gvAttributeColor.setAdapter(attributeColorAdapter);
        }

    }

    //注册广播接收者
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        //可以添加颜色、尺寸的监听
        filter.addAction(Constant.SHOPPINGCART_COLORADAPTER_SEND_SHOPPINGCART_RECORD_COLOR);
        receiver = new ChooseCommodityDialogActivity.InnerBroadcastReceiver();
        registerReceiver(receiver, filter);
    }

    class InnerBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Constant.SHOPPINGCART_COLORADAPTER_SEND_SHOPPINGCART_RECORD_COLOR.equals(action)) {
                //当前选中的颜色
                seledtedColor = intent.getStringExtra("currentPositionColor");
                name = intent.getStringExtra("name");
                price = intent.getStringExtra("price");
                img = intent.getStringExtra("img");
                commodityName.setText(name);
                commodityMoney.setText("￥" + price);
                Glide.with(context).load(img).into(commodityImgSmall);
                select_yanse.setText(seledtedColor.toString());
//                ToastHelper.show(context, seledtedColor.toString() + "name:" + name + "price:" + price + "img:" + img);
            }
        }
    }


    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.subtract_number://减少数量
                if (commodityNmber <= 1) {
                    return;
                }
                commodityNmber--;
                break;
            case R.id.add_number://增加数量
                commodityNmber++;
                break;

            case R.id.attritude://

                break;
            case R.id.add_shop_cart:// 确定加入
                 strnum = shopNumber.getText().toString().trim();
                  guiGe = seledtedColor.toString();
                 pricecount = "";
                if(!price.equals("")&& !strnum.equals("")){
                    price_d = Double.parseDouble(price);
                    numb= Double.parseDouble(shopNumber.getText().toString().trim());
                    pricecount = String.valueOf(price_d*numb);
                }
                if(!guiGe.equals("")&&!strnum.equals("0")){
                    addShoppingCart();
                }else {
                    ToastHelper.show(ChooseCommodityDialogActivity.this,"请选择规格和数量");
                }

//                makeSureMethod();
                break;
            case R.id.add_shop_cart2:// 立即购买
                strnum = shopNumber.getText().toString().trim();
                  guiGe = seledtedColor.toString();

                if(!price.equals("")&& !strnum.equals("")){
                    price_d = Double.parseDouble(price);
                    numb= Double.parseDouble(shopNumber.getText().toString().trim());
                     pricecount = String.valueOf(price_d*numb);
                }
                if(!guiGe.equals("")&&!strnum.equals("0")){
                    Intent intenttjdd = new Intent(ChooseCommodityDialogActivity.this, PurchaseOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("productid", productid);
                    bundle.putString("img", img);
                    bundle.putString("name", name);
                    bundle.putString("num", shopNumber.getText().toString().trim());
                    bundle.putString("price", pricecount);
                    bundle.putString("yanse", seledtedColor.toString());
                    intenttjdd.putExtras(bundle);
                    startActivity(intenttjdd);
                }else {
                    ToastHelper.show(ChooseCommodityDialogActivity.this,"请选择规格和数量");
                }

                break;
        }

        shopNumber.setText(commodityNmber + "");

    }

    /**
     * 确定
     */
    private void makeSureMethod() {
        String number = shopNumber.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("number", number);
        intent.putExtra("seledtedColor", seledtedColor);
        setResult(11, intent);
        finish();

    }

    /**
     * 接口名：getRecommendedList
     * Get请求   获取商品属性规格接口
     */
    private void getShoppingSpecs(String url) {
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
                    Log.i("resultCodeFragment2", "resultCodeFragment2:" + result);
                    shoppingSpecsEntity = gson.fromJson(result, ShoppingSpecsEntity.class);
                    Type listType2 = new TypeToken<ArrayList<ShoppingSpecsEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    dataShopSpecs = gson.fromJson(gson.toJson(shoppingSpecsEntity.getData()), listType2);

                    Log.i("dataShopSpecs", "dataShopSpecs" + dataShopSpecs);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingSpecsEntity.getCode();
                            if (code == 200) {
                                addData();
                                Toast.makeText(ChooseCommodityDialogActivity.this, "获取数据成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChooseCommodityDialogActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
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

    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：添加购物车
     */
    private void addShoppingCart() {
        Map<String, String> map = new HashMap<>();
        map.put("shopid", productid);
        map.put("name", name);
        map.put("price", price);
        map.put("info", seledtedColor.toString());
        map.put("num", shopNumber.getText().toString().trim());
        map.put("memberid", memberid);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/bas/membershopcar/addMemberShopcar.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    addShoppingCartEntity = gson.fromJson(result, AddShoppingCartEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (addShoppingCartEntity.getCode() == 200) {
                                ToastHelper.show(ChooseCommodityDialogActivity.this, addShoppingCartEntity.getMsg());
                                ChooseCommodityDialogActivity.this.finish();
                            } else {
                                ToastHelper.show(ChooseCommodityDialogActivity.this, addShoppingCartEntity.getMsg());
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
