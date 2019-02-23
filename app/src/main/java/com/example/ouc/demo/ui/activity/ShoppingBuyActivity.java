package com.example.ouc.demo.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.QueryGoodsInfoEntity;
import com.example.ouc.demo.entity.SubmitCustomerInfoEntity;
import com.example.ouc.demo.entity.UpgradeEntity;
import com.example.ouc.demo.http.HttpUtils;
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
import java.util.SortedMap;
import java.util.TreeMap;

import cn.com.cesgroup.numpickerview.NumberPickerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShoppingBuyActivity extends BaseActivity {
    private TextView tv_back, tv_content, tv_right;
    private TextView textview, textview1, textview4, textview5, tv_price, tv_sptitle, tv_kucun;
    private ImageView image_head;
    private EditText textview2, textview3;
    private String phoneNuber = "029-89624174";
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    private String taskid, userid, num;
    private double num_d, price_d;
    int num_int, quantity_int;
    private NumberPickerView numberPickerView1;
    private QueryGoodsInfoEntity queryGoodsInfoEntity;
    private SubmitCustomerInfoEntity submitCustomerInfoEntity;
    private Gson gson = new Gson();
    private List<QueryGoodsInfoEntity> data = new ArrayList<>();
    private String title, cover, price, quantity;
    private String address, detail_add, con_info, type, way;
    private Button tx_submitBuy;
    private String alltotal_str;
    private UpgradeEntity upgradeEntity;
    private IWXAPI wxapi;
    private TextView alltotal;
    private ProgersssDialog progersssDialog;
    private int numberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingbuy);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            userid = bundle2.getString("id");
            taskid = bundle2.getString("taskid");
        }
        wxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        wxapi.registerApp(Constants.APP_ID);
        initTitle();
        initViews();
        QueryGoodsInfo();
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("客服");
        tv_right.setVisibility(View.GONE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO  调用打电话
//                startCallPhone(phoneNuber);
                call("tel:" + phoneNuber);
            }
        });
//        tv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingBuyActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("订单购买");
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     * 判断是否有某项权限
     *
     * @param string_permission 权限
     * @param request_code      请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission, int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }

    /**
     * 检查权限后的回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show();
                } else {//成功
                    call("tel:" + phoneNuber);
                }
                break;
        }
    }

    /**
     * 拨打电话（直接拨打）
     *
     * @param telPhone 电话
     */
    public void call(String telPhone) {
        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(telPhone));
            startActivity(intent);
        }
    }

    private void initViews() {
        alltotal = findviewByid(R.id.alltotal);
        tv_kucun = findviewByid(R.id.tv_kucun);
        tx_submitBuy = findviewByid(R.id.tx_submitBuy);
        image_head = findviewByid(R.id.image_head);//头像
        tv_sptitle = findviewByid(R.id.tv_sptitle);//标题
        tv_price = findViewById(R.id.tv_price);//价格
        textview = findviewByid(R.id.textview);//选择的省市信息
        textview2 = findViewById(R.id.textview2);//详细地址
        textview3 = findViewById(R.id.textview3);//联系方式
        tx_submitBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 提交用户信息和微信支付
                address = textview.getText().toString().trim();//省市县地址
                detail_add = textview2.getText().toString().trim();//详细地址
                con_info = textview3.getText().toString().trim();//联系方式
                num = String.valueOf(numberPickerView1.getNumText());
                num_int = numberPickerView1.getNumText();
                num_d = numberPickerView1.getNumText();
                type = "1";
                way = "1";
                if (userid.equals("")) {
                    ToastHelper.show(ShoppingBuyActivity.this, "");
                    return;
                }
                if (taskid.equals("")) {
                    ToastHelper.show(ShoppingBuyActivity.this, "");
                    return;
                }
                if (address.equals("")) {
                    ToastHelper.show(ShoppingBuyActivity.this, "请选择地址");
                    return;
                }
                if (detail_add.equals("")) {
                    ToastHelper.show(ShoppingBuyActivity.this, "请输入详细地址");
                    return;
                }
                if (con_info.equals("")) {
                    ToastHelper.show(ShoppingBuyActivity.this, "请输入联系方式");
                    return;
                }
                if (con_info.length() != 11) {
                    ToastHelper.show(ShoppingBuyActivity.this, "格式不对");
                    return;
                }
                if (type.equals("")) {
                    return;
                }
                if (way.equals("")) {
                    return;
                }
                SubmitCustomerInfo(userid, taskid, address, detail_add, con_info, type, way, num);


            }
        });
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAddress();
            }
        });
        numberPickerView1 = (NumberPickerView) findViewById(R.id.purchase_num1);
        numberText = numberPickerView1.getNumText();
        Log.i("numberText", "numberText" + numberText);
        numberPickerView1.setMaxValue(20)
                .setCurrentInventory(150)
                .setMinDefaultNum(1)
                .setCurrentNum(1)
                .setmOnClickInputListener(new NumberPickerView.OnClickInputListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {
                        Toast.makeText(ShoppingBuyActivity.this, "亲，库存严重不足", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningMinInput(int minValue) {
                        Toast.makeText(ShoppingBuyActivity.this, "亲，真的不能再减了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningMaxInput(int maxValue) {
                        Toast.makeText(ShoppingBuyActivity.this, "亲,你想撑死宝宝吗?", Toast.LENGTH_SHORT).show();
                    }
                });

        numberPickerView1.setmOnClickChangeListener(new NumberPickerView.OnClickChangeListener() {
            @Override
            public void Click(View v) {
                switch (v.getId()) {
                    case cn.com.cesgroup.numpickerview.R.id.button_add:
                        num_d = numberPickerView1.getNumText();
                        Log.i("num_d", "num_d" + num_d);
                        if (num_d == 1) {
                            alltotal.setText("合计：￥" + price_d);
                        }
                        alltotal_str = String.valueOf(price_d * num_d);
                        alltotal.setText("合计：￥" + alltotal_str);
                        break;
                    case cn.com.cesgroup.numpickerview.R.id.button_sub:
                        double num_d_jian = numberPickerView1.getNumText();
                        Log.i("num_d", "num_d" + num_d_jian);
                        if (num_d_jian == 1) {
                            alltotal.setText("合计：￥" + price_d);
                        }
                        alltotal_str = String.valueOf(price_d * num_d_jian);
                        alltotal.setText("合计：￥" + alltotal_str);
                        break;
                }
            }
        });
    }

    /**
     * 选择地址
     */
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(ShoppingBuyActivity.this)
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
                textview.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    /**
     * get请求  完成任务接口
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    /**
     * 接口名：getRecommended
     * Get请求  推荐任务列表请求接口
     */
    private void QueryGoodsInfo() {
        String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/getTaskInfo.action?id=" + taskid;
        Log.i("url", "url" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:getRecommended：：：" + result);
                    queryGoodsInfoEntity = gson.fromJson(result, QueryGoodsInfoEntity.class);
//                        Type listType2 = new TypeToken<ArrayList<RecommendedEntity.DataBean>>() {
//                        }.getType();//TypeToken内的泛型就是Json数据中的类型
//                        data = gson.fromJson(gson.toJson(queryGoodsInfoEntity.getData()), listType2);
                    Log.i("666", "eeee" + queryGoodsInfoEntity);
                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void run() {

                            if (queryGoodsInfoEntity.getCode() == 200) {
                                quantity = queryGoodsInfoEntity.getData().getQuantity();
                                quantity_int = Integer.parseInt(queryGoodsInfoEntity.getData().getQuantity());
                                price_d = queryGoodsInfoEntity.getData().getPrice();
                                price = String.valueOf(queryGoodsInfoEntity.getData().getPrice());
                                title = queryGoodsInfoEntity.getData().getTitle();
                                tv_sptitle.setText(queryGoodsInfoEntity.getData().getTitle());
                                tv_price.setText("单价:￥" + queryGoodsInfoEntity.getData().getPrice());
                                alltotal.setText("总价:￥" + queryGoodsInfoEntity.getData().getPrice());
                                tv_kucun.setText("库存:" + quantity);
                                if (Util.isOnMainThread()&& !isFinishing() && !isDestroyed()) {
                                    Glide.with(ShoppingBuyActivity.this).load(queryGoodsInfoEntity.getData().getCover()).into(image_head);
                                }

                                if (quantity.equals("0")) {
                                    tx_submitBuy.setBackgroundColor(R.color.gray2);
                                    tx_submitBuy.setEnabled(false);
                                    ToastHelper.show(ShoppingBuyActivity.this, "亲，没货啦");
                                }

                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("eeee", "eeee" + e);
                }
            }
        });
    }

    /**
     * Post请求  完成任务接口
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void SubmitCustomerInfo(final String userid, final String taskid, final String address, final String detail_add, final String con_info, final String type, final String way, final String num) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("taskid", taskid);
        map.put("address", address);
        map.put("detail_add", detail_add);
        map.put("con_info", con_info);
        map.put("type", type);
        map.put("way", way);
        map.put("num", num);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/sysMemBuyShopController/insertOrder.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    submitCustomerInfoEntity = gson.fromJson(result, SubmitCustomerInfoEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (submitCustomerInfoEntity.getCode() == 200) {
                                ToastHelper.show(ShoppingBuyActivity.this, submitCustomerInfoEntity.getMsg());
                                if (title.equals("")) {
                                    ToastHelper.show(ShoppingBuyActivity.this, "描述不能为空");
                                    return;
                                }
                                if (price.equals("")) {
                                    ToastHelper.show(ShoppingBuyActivity.this, "价格不能为空");
                                    return;
                                }

                                int total = (int) ((price_d * 100) * num_d);
                                String total_str = String.valueOf(total);
                                if (num_int > quantity_int) {
                                    ToastHelper.show(ShoppingBuyActivity.this, "商品严重不足");
                                    return;
                                }
                                WeChatPay(total_str, title, userid + "," + taskid + "," + num);
                            } else {
                                ToastHelper.show(ShoppingBuyActivity.this, submitCustomerInfoEntity.getMsg());
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
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     * 微信支付---获取订单id接口
     */
    public void WeChatPay(String total, String body, String attach) {
        progersssDialog = new ProgersssDialog(ShoppingBuyActivity.this);
        progersssDialog.setCancelable(false);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("money", total);//金额为int类型
            map.put("body", body);//商品描述  商品名称
            map.put("attach", attach);//拼接当前会员id，当前任务id
            Log.i("", "");


            HttpUtils.doPost(Constants.SERVER_BASE_URL + "wechat/auth/wxauthController/AppjsPay.action", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("dfsd", "dsfsd" + e);
                    progersssDialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        progersssDialog.dismiss();
                        final String result = response.body().string();
                        Log.i("resultpost_upgrade", "result:post_upgrade" + result);
                        upgradeEntity = gson.fromJson(result, UpgradeEntity.class);
                        Log.i("post_upgrade", "post_upgrade" + upgradeEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (upgradeEntity.getCode() == 0) {
//                                    ToastHelper.show(UpgradeMembersActivity.this, result);
                                    String nonceStr = upgradeEntity.getPageData().getNoncestr();
                                    String prepayid = upgradeEntity.getPageData().getPrepayid();
                                    String timeStamp = upgradeEntity.getPageData().getTimestamp();
                                    String paySign = upgradeEntity.getPageData().getSign();

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

    @Override
    protected void onRestart() {
        super.onRestart();
//        QueryGoodsInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Util.isOnMainThread() && !isFinishing() && !isDestroyed()) {
                Glide.with(this).pauseRequests();
            }
        } else {
            if (Util.isOnMainThread() && !isFinishing()) {
                Glide.with(this).pauseRequests();
            }
        }
    }
}
