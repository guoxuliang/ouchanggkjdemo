package com.example.ouc.demo.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.LoadMoreAdapter;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.RecordEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.listener.EndlessRecyclerOnScrollListener;
import com.example.ouc.demo.ui.activity.vip.WithdrawalActivity;
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

public class WithdrawalRecordActivity extends BaseActivity{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreAdapter loadMoreAdapter;
    private List<RecordEntity.DataBean> dataList = new ArrayList<>();
    private RecordEntity recordEntity;
    private Gson gson = new Gson();
    private TextView tv_back,tv_content;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        id = getStringSharePreferences("id", "id");
        initTitle();
        initView();
        /**
         * 提现记录接口
         */
        Record();
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("提现记录");
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithdrawalRecordActivity.this.finish();
            }
        });
    }

    private void initData() {
        if(dataList.size()!=0){
            loadMoreAdapter = new LoadMoreAdapter(dataList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(loadMoreAdapter);
            // 模拟获取数据
//        getData();
            // 设置下拉刷新
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(dataList.size()>15){
                    // 刷新数据
                    dataList.clear();
//                getData();
                    loadMoreAdapter.notifyDataSetChanged();

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
                        ToastHelper.show(WithdrawalRecordActivity.this,"数据不足");
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
                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING);
                    if (dataList.size() < 52) {
                        // 模拟获取网络数据，延时1s
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                    getData();
                                        loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_COMPLETE);
                                    }
                                });
                            }
                        }, 1000);
                    } else {
                        // 显示加载到底的提示
                        loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_END);
                    }
                }
            });
        }

    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // 使用Toolbar替换ActionBar  设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
    }

//    private void getData() {
//        char letter = 'A';
//        for (int i = 0; i < 20; i++) {
//            dataList.add(String.valueOf(letter));
//            letter++;
//        }
//    }

    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     * 提现记录接口
     */
    private void Record() {
        Map<String, String> map = new HashMap<>();
        map.put("userid", id);
//        map.put("userid", "5");
        Log.i("", "");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemPresentRecordController/getPreRocrdList.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:Record" + result);
                    recordEntity = gson.fromJson(result, RecordEntity.class);
                    Log.i("recordEntity", "recordEntity:Record" + recordEntity);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (recordEntity.getCode() == 200) {
                                ToastHelper.show(WithdrawalRecordActivity.this, recordEntity.getMsg());
                                recordEntity.getData();
                                dataList=recordEntity.getData();
                                initData();
                            }else {
                                ToastHelper.show(WithdrawalRecordActivity.this, recordEntity.getMsg());
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
