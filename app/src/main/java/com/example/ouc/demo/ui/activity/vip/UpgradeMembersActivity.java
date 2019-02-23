package com.example.ouc.demo.ui.activity.vip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.UpgradeMembersAdapter;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.UpgradeEntity;
import com.example.ouc.demo.entity.UpgradeMembersEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.listener.EndlessRecyclerOnScrollListener;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpgradeMembersActivity extends BaseActivity{
    private TextView tv_back, tv_content;
    private Button btn_vip, btn_bj, btn_cj;
    private UpgradeEntity upgradeEntity;
    private String id,level;
    private Gson gson = new Gson();
    private String money;//金额
    private String body;//升级类型
    private String attach;//附加信息
    //列表
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private UpgradeMembersAdapter upgradeMembersAdapter;
    private List<UpgradeMembersEntity.DataBean> dataListAll = new ArrayList<>();
    private UpgradeMembersEntity upgradeMembersEntity;
    private IWXAPI wxapi;
    String item;
    private ProgersssDialog progersssDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrademembers);
        id = getStringSharePreferences("id", "id");
        level = getStringSharePreferences("level","level");
        Bundle bundle = getIntent().getExtras();   //得到传过来的bundle
        if (bundle != null) {
            item = bundle.getString("item");//拿到选择的状态，办理升级续费
        }
        Log.i("level","level"+level);
        initTitle();
        initViews();
        wxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID,false);
        wxapi.registerApp(Constants.APP_ID);
        UpgradeMembersinterface();//获取列表接口
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // 使用Toolbar替换ActionBar  设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(UpgradeMembersActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                progersssDialog = new ProgersssDialog(UpgradeMembersActivity.this);
                progersssDialog.setCancelable(false);
                //TODO  recyclerView的点击事件
                String total_fee;//金额
                String upgrade;//要升到的等级
                String uptype;//要升级的类型
                String attachs;
//                Toast.makeText(UpgradeMembersActivity.this,"您点击了第"+position+"项",Toast.LENGTH_LONG).show();
                if(level.equals("2") && position==0 && item.equals("办理")){
                            progersssDialog.dismiss();
                            ToastHelper.show(UpgradeMembersActivity.this,"此等级不可办理");
                            return;
                }
                if(level.equals("3") && position==0 && item.equals("办理")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可办理");
                    return;
                }
                if(level.equals("3") && position==1 && item.equals("办理")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可办理");
                    return;
                }
                if(level.equals("4") && position==0 && item.equals("办理")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可办理");
                    return;
                }  if(level.equals("4") && position==1 && item.equals("办理")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可办理");
                    return;
                }
                if(level.equals("4") && position==2 && item.equals("办理")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可办理");
                    return;
                }
                if(level.equals("2") && position==0 && item.equals("升级")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可升级");
                    return;
                }
                if(level.equals("3") && position==0 && item.equals("升级")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可升级");
                    return;
                }
                if(level.equals("3") && position==1 && item.equals("升级")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可升级");
                    return;
                }
                if(level.equals("4") && position==0 && item.equals("升级")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可升级");
                    return;
                }
                if(level.equals("4") && position==1 && item.equals("升级")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可升级");
                    return;
                }
                if(level.equals("4") && position==2 && item.equals("升级")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可升级");
                    return;
                }
//                if(level.equals("2") && position==0 && item.equals("续费")){
//                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可续费");
//                    return;
//                }
                if(level.equals("3") && position==0 && item.equals("续费")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可续费");
                    return;
                }
                if(level.equals("3") && position==1 && item.equals("续费")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可续费");
                    return;
                }
                if(level.equals("4") && position==0 && item.equals("续费")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可续费");
                    return;
                }
                if(level.equals("4") && position==1 && item.equals("续费")){
                    progersssDialog.dismiss();
                    ToastHelper.show(UpgradeMembersActivity.this,"此等级不可续费");
                    return;
                }

                if(level.equals("1")){
                    if(position==0){
                        if(item.equals("办理")){
                            total_fee="49900";
                            upgrade = "2";
                            attachs = id+","+upgrade+","+"1";
                            Bundle bundle=new Bundle();
                            bundle.putString("item",item);
                            bundle.putString("total_fee",total_fee);
                            bundle.putString("attachs",attachs);
                            openActivity(TransactNubActivity.class,bundle);
                            UpgradeMembersActivity.this.finish();
                            return;
                        }
                    }
                    if(position==1){
                        if(item.equals("办理")){
                            total_fee="399000";
                            upgrade = "3";
                            attachs = id+","+upgrade+","+"1";
                            Bundle bundle=new Bundle();
                            bundle.putString("item",item);
                            bundle.putString("total_fee",total_fee);
                            bundle.putString("attachs",attachs);
                            openActivity(TransactNubActivity.class,bundle);
                            return;
                        }
                    }
                    if(position==2){
                        if(item.equals("办理")){
                            total_fee="3990000";
                            upgrade = "4";
                            attachs = id+","+upgrade+","+"1";
                            Bundle bundle=new Bundle();
                            bundle.putString("item",item);
                            bundle.putString("total_fee",total_fee);
                            bundle.putString("attachs",attachs);
                            openActivity(TransactNubActivity.class,bundle);
                            return;
                        }
                    }
                }

                if(level.equals("2")){
                    if(position==0){
                        if(item.equals("办理")){
//                            total_fee="49900";
//                            upgrade = "2";
//                            attachs = id+","+upgrade+","+"1";
//                            Bundle bundle=new Bundle();
//                            bundle.putString("item",item);
//                            bundle.putString("total_fee",total_fee);
//                            bundle.putString("attachs",attachs);
//
//                            openActivity(TransactNubActivity.class,bundle);
//                            UpgradeMembersActivity.this.finish();
                            ToastHelper.show(UpgradeMembersActivity.this,"不可办理");
                        }
                    }
                    if(position==1){
                        if(item.equals("办理")){
                            total_fee="4990000";
                            upgrade = "3";
                            attachs = id+","+upgrade+","+"1";
                            Bundle bundle=new Bundle();
                            bundle.putString("item",item);
                            bundle.putString("total_fee",total_fee);
                            bundle.putString("attachs",attachs);
                            openActivity(TransactNubActivity.class,bundle);
                            return;
                        }
                    }
                    if(position==2){
                        if(item.equals("办理")){
                            total_fee="499000000";
                            upgrade = "4";
                            attachs = id+","+upgrade+","+"1";
                            Bundle bundle=new Bundle();
                            bundle.putString("item",item);
                            bundle.putString("total_fee",total_fee);
                            bundle.putString("attachs",attachs);
                            openActivity(TransactNubActivity.class,bundle);
                            return;
                        }
                    }
                }

                if(level.equals("3")){
                    if(position==0){
                        if(item.equals("办理")){
                            ToastHelper.show(UpgradeMembersActivity.this,"不可办理");
                        }
                    }
                    if(position==1){
                        if(item.equals("办理")){
                            ToastHelper.show(UpgradeMembersActivity.this,"不可办理");
                        }
                    }
                    if(position==2){
                        if(item.equals("办理")){
                            total_fee="499000000";
                            upgrade = "4";
                            attachs = id+","+upgrade+","+"1";
                            Bundle bundle=new Bundle();
                            bundle.putString("item",item);
                            bundle.putString("total_fee",total_fee);
                            bundle.putString("attachs",attachs);
                            openActivity(TransactNubActivity.class,bundle);
                            return;
                        }
                    }
                }

                if(level.equals("4")){
                    if(position==0){
                        if(item.equals("办理")){
                            ToastHelper.show(UpgradeMembersActivity.this,"不可办理");
                        }
                    }
                    if(position==1){
                        if(item.equals("办理")){
                            ToastHelper.show(UpgradeMembersActivity.this,"不可办理");
                        }
                    }
                    if(position==2){
                        if(item.equals("办理")){
                            ToastHelper.show(UpgradeMembersActivity.this,"不可办理");
                        }
                    }
                }


                if(position==0){
                    total_fee="49900";
                    upgrade = "2";
                    if(item.equals("办理")){
                        uptype="1";
                    }else if(item.equals("升级")){
                        uptype="2";
                    }else {
                        uptype="3";
                    }
                    attachs = id+","+upgrade+","+uptype;
                    post_upgrade(id,total_fee,"畅享App-"+item,attachs);
                }
                if(position==1){
                    total_fee="3990000";
                    upgrade = "3";
                    if(item.equals("办理")){
                        uptype="1";
                    }else if(item.equals("升级")){
                        uptype="2";
                    }else {
                        uptype="3";
                    }
                    attachs = id+","+upgrade+","+uptype;
                    post_upgrade(id,total_fee,"畅享App-"+item,attachs);
                }
                if(position==2){
                    total_fee="399000000";
                    upgrade = "4";
                    if(item.equals("办理")){
                        uptype="1";
                    }else if(item.equals("升级")){
                        uptype="2";
                    }else {
                        uptype="3";
                    }
                    attachs = id+","+upgrade+","+uptype;
                    post_upgrade(id,total_fee,"畅享App-"+item,attachs);
                }
            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO  recyclerView的长按事件
                int posotion2=posotion;
            }
        }));
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
    private void initData() {
        if(dataListAll.size()!=0){
            upgradeMembersAdapter = new UpgradeMembersAdapter(dataListAll,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(upgradeMembersAdapter);
            // 模拟获取数据
//        getData();
            // 设置下拉刷新
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    if(dataListAll.size()>15){
                        // 刷新数据
                        dataListAll.clear();
//                getData();
                        upgradeMembersAdapter.notifyDataSetChanged();

                        // 延时1s关闭下拉刷新
                        swipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }, 1000);
                    }else{
                        ToastHelper.show(UpgradeMembersActivity.this,"数据不足");
                        swipeRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }, 1000);
                    }
                }
            });

            // 设置加载更多监听
            recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
                @Override
                public void onLoadMore() {
                    upgradeMembersAdapter.setLoadState(upgradeMembersAdapter.LOADING);
                    if (dataListAll.size() < 52) {
                        // 模拟获取网络数据，延时1s
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                    getData();
                                        upgradeMembersAdapter.setLoadState(upgradeMembersAdapter.LOADING_COMPLETE);
                                    }
                                });
                            }
                        }, 1000);
                    } else {
                        // 显示加载到底的提示
                        upgradeMembersAdapter.setLoadState(upgradeMembersAdapter.LOADING_END);
                    }
                }
            });
        }

    }
    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     * 提现记录接口
     */
    private void UpgradeMembersinterface() {
        Map<String, String> map = new HashMap<>();
//        map.put("userid", "5");
        Log.i("", "");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/sysMemLevelDetailController/getLevelList.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:Record" + result);
                    upgradeMembersEntity = gson.fromJson(result, UpgradeMembersEntity.class);
                    Log.i("upgradeMembersEntity", "upgradeMembersEntity:Record" + upgradeMembersEntity);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (upgradeMembersEntity.getCode() == 200) {
//                                ToastHelper.show(UpgradeMembersActivity.this, upgradeMembersEntity.getMsg());
                                upgradeMembersEntity.getData();
                                dataListAll=upgradeMembersEntity.getData();
                                initData();
                            }else {
                                ToastHelper.show(UpgradeMembersActivity.this, upgradeMembersEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progersssDialog.dismiss();
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
                    progersssDialog.dismiss();
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
                                    progersssDialog.dismiss();
//                                    ToastHelper.show(UpgradeMembersActivity.this, result);
                                    String nonceStr = upgradeEntity.getPageData().getNoncestr();
                                    String prepayid = upgradeEntity.getPageData().getPrepayid();
                                    String timeStamp = upgradeEntity.getPageData().getTimestamp();
                                    String paySign = upgradeEntity.getPageData().getSign();

                                    SortedMap<Object, Object> params = new TreeMap<Object, Object>();
                                    PayReq request = new PayReq();
                                    request.appId = Constants.APP_ID;
                                    request.partnerId =Constants.PARTNER_ID;
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
}
