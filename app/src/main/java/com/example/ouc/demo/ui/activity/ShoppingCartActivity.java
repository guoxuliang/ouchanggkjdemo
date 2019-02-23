package com.example.ouc.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.ShoppingCartAdapter;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.BuyOrderEntity;
import com.example.ouc.demo.entity.GetShoppingCartEntity;
import com.example.ouc.demo.entity.PayParamBean;
import com.example.ouc.demo.entity.RemoveShoppingCartEntity;
import com.example.ouc.demo.entity.ShoppingCartBean;
import com.example.ouc.demo.entity.ShoppingPayEntity;
import com.example.ouc.demo.entity.ShoppingUserInfoEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by an on 2017/6/14.
 * 购物车界面
 *
 */
public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener,ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface {
    private static final String TAG = "ShoppingCartActivity";
    Button btnBack;
    //全选
    CheckBox ckAll;
    //总额
    TextView tvShowPrice;
    //结算
    TextView tvSettlement;
    //编辑
    TextView btnEdit;//tv_edit

    ListView list_shopping_cart;
    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean flag = false;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private boolean mSelect;
    private double totalPrice = 0;// 购买的商品总价
//    private String totalPrice="";
    private int totalCount = 0;// 购买的商品总数量

    private ProgersssDialog progersssDialog;
    private String url = Constants.SERVER_BASE_URL +"app/bas/membershopcar/getMemberShopcar.action?";
    private String memberid;
    private Gson gson = new Gson();
    private ArrayList<GetShoppingCartEntity.DataBean> getShoppingCartData;
    private GetShoppingCartEntity getShoppingCartEntity;//查询购物车实体
    private RemoveShoppingCartEntity removeShoppingCartEntity;//删除购物车实体
    private ShoppingPayEntity shoppingPayEntity;
    private IWXAPI wxapi;

    private String shoppingName,userid,productid,addressid,yanse;
    private int count;
    private String attach="";
    private double price;
    private double alltotal=0.0;

    private String url2 = Constants.SERVER_BASE_URL + "app/bas/appOrderListController/downOrder.action";
    private String url3 = Constants.SERVER_BASE_URL + "app/bas/appOrderListController/wechatAppPay.action";
    private ShoppingUserInfoEntity shoppingUserInfoEntity;
    private String urladdress = Constants.SERVER_BASE_URL +"app/bas/user/selectDefaultAddress.action?userid=";
    private ShoppingCartBean shoppingCartBean;
    private BuyOrderEntity buyOrderEntity;
    private List<BuyOrderEntity.AppOrderListBean> orderlistData;
    private PayParamBean payParamBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shopping_cart_activity);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            memberid = bundle.getString("memberid");
        }
        initView();
        userid = getStringSharePreferences("id","id");
        getShoppingUserInfo(urladdress+userid);
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        progersssDialog = new ProgersssDialog(this);
        getShoppingCart(url+"memberid="+memberid+"&pageindex="+0+"&pagenum="+10);
        Log.i("url","url:::===="+url+"memberid="+memberid+"&pageindex="+0+"&pagenum="+10);
        wxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        wxapi.registerApp(Constants.APP_ID);
    }
    private void initView() {
        btnBack= (Button) findViewById(R.id.btn_back);
        ckAll= (CheckBox) findViewById(R.id.ck_all);
        tvShowPrice= (TextView) findViewById(R.id.tv_show_price);
        tvSettlement= (TextView) findViewById(R.id.tv_settlement);
        btnEdit= (TextView) findViewById(R.id.bt_header_right);
        list_shopping_cart= (ListView) findViewById(R.id.list_shopping_cart);

        btnEdit.setOnClickListener(this);
        ckAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        initData();
    }
    //初始化数据
    protected void initData() {

//        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName("上档次的T桖");
//            shoppingCartBean.setDressSize(20);
//            shoppingCartBean.setId(i);
//            shoppingCartBean.setPrice(30.6);
//            shoppingCartBean.setCount(1);
//            shoppingCartBean.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
//        }
//        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName("瑞士正品夜光男女士手表情侣精钢带男表防水石英学生非天王星机械");
//            shoppingCartBean.setAttribute("黑白色");
//            shoppingCartBean.setPrice(89);
//            shoppingCartBean.setId(i+2);
//            shoppingCartBean.setCount(3);
//            shoppingCartBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/2160089910/TB2M_NSbB0kpuFjSsppXXcGTXXa_!!2160089910.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
//        }
        shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        list_shopping_cart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(getShoppingCartData);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.ck_all:
                if (getShoppingCartData.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < getShoppingCartData.size(); i++) {
                            getShoppingCartData.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < getShoppingCartData.size(); i++) {
                            getShoppingCartData.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.bt_header_right:
                flag = !flag;
                if (flag) {
                    btnEdit.setText("完成");
                    shoppingCartAdapter.isShow(false);
                } else {
                    btnEdit.setText("编辑");
                    shoppingCartAdapter.isShow(true);
                }
                break;
            case R.id.tv_settlement: //结算
                lementOnder();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    /**
     * 结算订单、支付
     */
    private void lementOnder() {
        //选中的需要提交的商品清单
        for (GetShoppingCartEntity.DataBean bean:getShoppingCartData ){
            boolean choosed = bean.isChoosed();
            if (choosed){
                 shoppingName = bean.getName();
                 productid = bean.getShopid();
//                 addressid = String.valueOf(bean.getId());
                 yanse = bean.getInfo();
                 count = bean.getNum();
                 price = bean.getPrice();
                 attach =userid+",2,"+productid+","+addressid+","+yanse+"*"+count;

                 alltotal = price*count;
                 Log.i("alltotal::","alltotal::"+alltotal);

//                ToastHelper.show(this,"总价："+"shoppingName:"+shoppingName+"userid:"+userid+"productid:"+productid+"addressid:"+addressid+"yanse:"+yanse+"count:"+count+"");
                shoppingCartBean = new ShoppingCartBean();
                shoppingCartBean.setMoney(String.valueOf(alltotal));
                shoppingCartBean.setShopid(productid);
                shoppingCartBean.setType("2");
                shoppingCartBean.setMemberid(userid);
                shoppingCartBean.setNum(String.valueOf(count));
                shoppingCartBean.setAddressid(addressid);
                Log.i("addressid2", "addressid2" + addressid);
                shoppingCartBeanList.add(shoppingCartBean);
            }
        }
//        ToastHelper.show(this,"总价："+""+shoppingName+totalPrice);
        Log.i(TAG,"shoppingCartBeanList"+shoppingCartBeanList.size());
        if(shoppingCartBeanList!=null){
            gwcOrdershoppingPay(shoppingCartBeanList,url2);
        }




        //跳转到支付界面
    }
    /**
     * 单选
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        getShoppingCartData.get(position).setChoosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 遍历list集合
     * @return
     */
    private boolean isAllCheck() {

        for (GetShoppingCartEntity.DataBean group : getShoppingCartData) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }
    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
//        totalPrice = 0;
        for (int i = 0; i < getShoppingCartData.size(); i++) {
            GetShoppingCartEntity.DataBean shoppingCartBean = getShoppingCartData.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
//                double dprice= Double.parseDouble(shoppingCartBean.getPrice());
//                double dnum = shoppingCartBean.getNum();
//                totalPrice += dprice*dnum;
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getNum();
//                totalPrice = alltotaltext;
            }
        }
        if(totalCount!=0){
            tvShowPrice.setText("合计:" + totalPrice);
        }else {
            totalPrice = 0.0;
            tvShowPrice.setText("合计:" + totalPrice);
        }
        tvSettlement.setText("结算(" + totalCount + ")");
    }
    /**
     * 增加
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        GetShoppingCartEntity.DataBean shoppingCartBean = getShoppingCartData.get(position);
        int currentCount = shoppingCartBean.getNum();
        currentCount++;
        shoppingCartBean.setNum(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 删减
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        GetShoppingCartEntity.DataBean shoppingCartBean = getShoppingCartData.get(position);
        int currentCount = shoppingCartBean.getNum();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setNum(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 删除
     * @param position
     */
    @Override
    public void childDelete(int position) {
        int id;
        Log.i("getShoppingCartData","getShoppingCartData=="+getShoppingCartData.size());
        try{
            if(getShoppingCartData.size()!=0){
                id=getShoppingCartData.get(position).getId();
                Log.i("id","id"+id);
                removeShoppingCart(String.valueOf(id));
                getShoppingCartData.remove(position);
                shoppingCartAdapter.notifyDataSetChanged();
                statistics();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("e", "e" + e);
        }


    }


    /**
     * 接口名：getRecommendedList
     * Get请求   获取购物车
     */
    private void getShoppingCart(String url) {
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
                    Log.i("shoppingCart", "shoppingCart:" + result);
                    getShoppingCartEntity = gson.fromJson(result, GetShoppingCartEntity.class);
                    Type listType2 = new TypeToken<ArrayList<GetShoppingCartEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    getShoppingCartData = gson.fromJson(gson.toJson(getShoppingCartEntity.getData()), listType2);

                    Log.i("getShoppingCartData", "getShoppingCartData" + getShoppingCartData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = getShoppingCartEntity.getCode();
                            if (code==200) {
                                initData();
                                Toast.makeText(ShoppingCartActivity.this,"获取数据成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ShoppingCartActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
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
     * 接口名：getShoppingUserInfo
     * Get请求   获取默认地址信息
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingUserInfoEntity.getCode();
                            if (code==200) {
                                 addressid = shoppingUserInfoEntity.getData().getId()+"";
                                Log.i("addressid", "addressid" + addressid);
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
     * 参数三：删除购物车
     */
    private void removeShoppingCart(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/bas/membershopcar/delMemberShopcar.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    removeShoppingCartEntity = gson.fromJson(result, RemoveShoppingCartEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (removeShoppingCartEntity.getCode() == 200) {
                                ToastHelper.show(ShoppingCartActivity.this, removeShoppingCartEntity.getMsg());
                            }else{
                                ToastHelper.show(ShoppingCartActivity.this, removeShoppingCartEntity.getMsg());
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
     * 接口名：shoppingPay
     * Get请求   商品下单请求接口
     */
    private void gwcOrdershoppingPay(Object paramBean,String url) {

        String param = new Gson().toJson(paramBean);
        Log.i("param","param::"+param.toString());
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), param);
        Log.i("body","body::"+body.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {

                    }

                    @Override
                    public void onResponse(final Call call, final Response response) {
                        try {
                            String result = response.body().string();
                            Log.i("resultData","resultData::"+result);
                                    buyOrderEntity = gson.fromJson(result, BuyOrderEntity.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (buyOrderEntity.getCode() == 0) {
                                        ToastHelper.show(ShoppingCartActivity.this, buyOrderEntity.getMsg());
                                        orderlistData =buyOrderEntity.getAppOrderList();
                                        double payTotal = totalPrice*100;
                                        Log.i("payTotal","payTotal===:::"+payTotal);
                                        payParamBean = new PayParamBean();
                                        payParamBean.setBody(shoppingName);
                                        payParamBean.setAttach("2");
                                        payParamBean.setTotal_fee((int) payTotal);
                                        payParamBean.setAppOrder(orderlistData);
                                        gwcshoppingPay(payParamBean,url3);
                                    }else{
                                        ToastHelper.show(ShoppingCartActivity.this, buyOrderEntity.getMsg());
                                    }
                                }
                            });

                        }catch (Exception e){

                        }

                    }
                    });
                }


    /**
     * 接口名：shoppingPay
     * Get请求   商品支付请求接口
     */
    private void gwcshoppingPay(Object paramBean,String url) {
        String param = gson.toJson(paramBean);
        Log.i("gwcshoppingPay","gwcshoppingPay::"+param.toString());
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), param);
        Log.i("bodyPay","bodyPay::"+body.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
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
}
