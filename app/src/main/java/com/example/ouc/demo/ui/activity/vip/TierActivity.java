package com.example.ouc.demo.ui.activity.vip;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.TierAdapter;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.TierEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.listener.EndlessRecyclerOnScrollListener;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TierActivity extends BaseActivity {
    private TextView tv_back, tv_content;

    private RadioGroup rg_select_sxj;
    private RadioButton rb_sj, rb_xj;
    private String loggersName;
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView recycler_view;
    private TierEntity tierEntity;
    private List<TierEntity.DataBean> dataAll = new ArrayList<>();
    private Gson gson = new Gson();
    private TierAdapter tierAdapter;
    private String url;
    private String type="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tier);
        initTitle();
        initViews();
        url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/getTopUser.action";
        RecordTier(url);
    }

    private void initViews() {
        rg_select_sxj = (RadioGroup) findViewById(R.id.rg_select_sxj);
        rb_sj = (RadioButton) findViewById(R.id.rb_sj);
        rb_xj = (RadioButton) findViewById(R.id.rb_xj);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        recycler_view = findViewById(R.id.recycler_view);

        rg_select_sxj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_sj.getId() == i) {
                    // 上级
                    loggersName = rb_sj.getText().toString();
                    url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/getTopUser.action";
                    RecordTier(url);
                    type="1";
                }
                if (rb_xj.getId() == i) {
                    // 下级
                    loggersName = rb_xj.getText().toString();
                    url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/getMyUserInfo.action";
                    RecordTier(url);
                    type="2";
                }
                Toast.makeText(TierActivity.this, "你选择了：" + loggersName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TierActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("层级关系");
    }


    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     * 查看层级接口
     */
    private void RecordTier(String urlpath) {
        try {
            Map<String, String> map = new HashMap<>();
//        map.put("id", id);
            map.put("id", "66");
            Log.i("", "");

            HttpUtils.doPost(urlpath, map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("dfsd", "dsfsd" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result", "result:tierEntity" + result);
                        tierEntity = gson.fromJson(result, TierEntity.class);
                        Log.i("tierEntity", "tierEntity" + tierEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tierEntity.getCode() == 200) {
                                    ToastHelper.show(TierActivity.this, tierEntity.getMsg());
                                    dataAll =tierEntity.getData();
                                    initData();
                                }else {
                                    ToastHelper.show(TierActivity.this, tierEntity.getMsg());
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initData() {
        if(dataAll.size()!=0){
            tierAdapter = new TierAdapter(dataAll,this);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            recycler_view.setAdapter(tierAdapter);
            // 模拟获取数据
//        getData();
            // 设置下拉刷新
            swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
//                    if(dataAll.size()>15){
                        // 刷新数据
//                        dataAll.clear();
//                getData();
//                    if(type.equals("1")){
//                        url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/getTopUser.action";
//                        RecordTier(url);
//                    }else if(type.equals("2")){
//                        url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/getMyUserInfo.action";
//                        RecordTier(url);
//                    }
                        tierAdapter.notifyDataSetChanged();

                        // 延时1s关闭下拉刷新
                        swipe_refresh_layout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing()) {
                                    swipe_refresh_layout.setRefreshing(false);
                                }
                            }
                        }, 1000);
//                    }else{
//                        ToastHelper.show(TierActivity.this,"数据不足");
//                        swipe_refresh_layout.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing()) {
//                                    swipe_refresh_layout.setRefreshing(false);
//                                }
//                            }
//                        }, 1000);
//                    }
                }
            });

            // 设置加载更多监听
            recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
                @Override
                public void onLoadMore() {
                    tierAdapter.setLoadState(tierAdapter.LOADING);
                    if (dataAll.size() < 52) {
                        // 模拟获取网络数据，延时1s
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        /**
                                         * 想要完成刷新加载数据，在这重新调用接口做刷新或者分页
                                         * //调用接口
                                         */
//                                    getData();
                                        //tierAdapter.setLoadState(tierAdapter.LOADING_COMPLETE);

                                        tierAdapter.setLoadState(tierAdapter.LOADING_END);
                                    }
                                });
                            }
                        }, 1000);
                    } else {
                        // 显示加载到底的提示
                        tierAdapter.setLoadState(tierAdapter.LOADING_END);
                    }
                }
            });
        }

    }
}
