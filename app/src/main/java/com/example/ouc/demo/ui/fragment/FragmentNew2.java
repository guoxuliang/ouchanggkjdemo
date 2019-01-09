package com.example.ouc.demo.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.ItemAdapter;
import com.example.ouc.demo.adapter.ItemAdapterF2;
import com.example.ouc.demo.adapter.LoadMoreAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.GetTaskEntity;
import com.example.ouc.demo.entity.RecommendedListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.listener.EndlessRecyclerOnScrollListener;
import com.example.ouc.demo.ui.activity.AdvertisingVideoActivity;
import com.example.ouc.demo.ui.activity.LoginActivity;
import com.example.ouc.demo.ui.activity.WithdrawalRecordActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentNew2 extends BaseFragment{
    private TextView tv_back, tv_content;
    private View view;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ItemAdapterF2 adapter;
    private String is_login;
    private ProgersssDialog progersssDialog;
    private String id;
    int start = 0;
    int limit = 1000000;
    String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/tasklist.action?";
    private Gson gson = new Gson();
    private ArrayList<RecommendedListEntity.DataBean> dataBeansList2;
    private RecommendedListEntity recommendedListEntity;
    private GetTaskEntity getTaskEntity;
    private String contents;//获取内容
    private int taskid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragmentnew2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        is_login = getStringSharePreferences("is_login", "is_login");
        progersssDialog = new ProgersssDialog(getActivity());
        id = getStringSharePreferences("id", "id");
        initTitle();
        initViews();
        if (isConnNet(getActivity()) == true) {
            String dhs = "start=" + start + "&" + "limit=" + limit + "&" + "userid=" + id;
            getRecommendedList(url + dhs);
        } else {
            ToastHelper.show(getActivity(), "请检查网络");
        }
    }

    private void initViews() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layoutnew2);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_viewnew2);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // 使用Toolbar替换ActionBar
//        setSupportActionBar(toolbar);
        // 设置刷新控件颜色
//        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));


//        // 模拟获取数据
//        adapter = new ItemAdapterF2(dataBeansList2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);
//
//        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
//                dataBeansList2.clear();
                if(dataBeansList2!=null){
                adapter.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    ToastHelper.show(getActivity(),"暂无数据");
                }
            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(adapter.LOADING);
                if (dataBeansList2.size() < 52) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setLoadState(adapter.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    adapter.setLoadState(adapter.LOADING_END);
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  recyclerView的点击事件
//                Toast.makeText(getActivity(),"您点击了第"+position+"项",Toast.LENGTH_LONG).show();
                if(id!=null){
                    try {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("id", id);//用户id
                        mBundle.putString("name", dataBeansList2.get(position).getTitle());//名称
                        mBundle.putString("gold", dataBeansList2.get(position).getGold() + "");//奖励金
                        mBundle.putString("videourl", dataBeansList2.get(position).getVideo());//视频地址
//                mBundle.putString("timelong", dataBeansList2.get(position).getTimelong());//视频地址
//                mBundle.putString("content", dataBeansList2.get(position).getContent());//视频信息
                        mBundle.putString("shareUrl", dataBeansList2.get(position).getShareUrl());//要分享的web页面地址
                        mBundle.putString("taskid", dataBeansList2.get(position).getId()+"");//获取任务ID
                        contents =  dataBeansList2.get(position).getContent();//获取内容
                        Log.i("contents","contents"+contents);
                        setStringSharedPreferences("content","content",contents);
                        taskid = dataBeansList2.get(position).getId();

                        intent.putExtras(mBundle);
                        startActivity(intent);
                        Gettask(id,taskid);//调用领取任务接口
                        Log.i("result:","result:调用次数");
                    }catch (Exception e){
                        ToastHelper.show(getActivity(),"error:"+e);
                    }
                }else {
                    showCustomizeDialog();
                }
            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO  recyclerView的长按事件
                int posotion2=posotion;
                Toast.makeText(getActivity(),"您长按了第"+posotion2+"项",Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void initTitle() {
        tv_back = view.findViewById(R.id.tv_left);
        tv_back.setVisibility(View.GONE);
        tv_content = view.findViewById(R.id.tv_title);
        tv_content.setText("广告增值平台");
    }

    @Override
    public void onResume() {
        super.onResume();
        is_login = getStringSharePreferences("is_login", "is_login");
        if (isConnNet(getActivity()) == true) {
            String dhs = "start=" + start + "&" + "limit=" + limit + "&" + "userid=" + id;
            getRecommendedList(url + dhs);
        } else {
            ToastHelper.show(getActivity(), "请检查网络");
        }
    }

    /**
     * 接口名：getRecommendedList
     * Get请求   任务列表请求接口
     */
    private void getRecommendedList(String url) {
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
                    Log.i("resultCodeFragment2", "resultCodeFragment2:ttt" + result);
                    recommendedListEntity = gson.fromJson(result, RecommendedListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<RecommendedListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    dataBeansList2 = gson.fromJson(gson.toJson(recommendedListEntity.getData()), listType2);

                    Log.i("dataBeansList2", "dataBeansList2" + dataBeansList2);
                    Log.i("dataBeansList2", "dataBeansList2.size()::::::::::"+dataBeansList2.size());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String code = String.valueOf(recommendedListEntity.getCode());
//                            ToastHelper.show(getActivity(),recommendedListEntity.getMsg());
                            if (code.equals("200")) {
//                                initRefreshLayout();
//                                initRecyclerView();
                                initData();
                            }
//                            initListData();
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
     * Post请求  领取任务接口
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void Gettask(String id, int taskid) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("taskid", String.valueOf(taskid));

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemUserTaskController/receivetask.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    getTaskEntity = gson.fromJson(result, GetTaskEntity.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (getTaskEntity.getCode() == 200) {
                                ToastHelper.show(getActivity(), getTaskEntity.getMsg());
                            } else {
                                ToastHelper.show(getActivity(), getTaskEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void showCustomizeDialog() {
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_customize, null);
        customizeDialog.setTitle("提示");
        customizeDialog.setView(dialogView);
        TextView edit_text = (TextView) dialogView.findViewById(R.id.edit_text);
        edit_text.setText("请先登录您的账号");
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取EditView中的输入内容
                        openActivity(LoginActivity.class);

                    }
                });
        customizeDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        customizeDialog.setCancelable(false);
        customizeDialog.show();
    }













    private void initData() {
        if(dataBeansList2.size()!=0){
            adapter = new ItemAdapterF2(dataBeansList2);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            // 模拟获取数据
//        getData();
            // 设置下拉刷新
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(dataBeansList2.size()>10){
                        // 刷新数据
//                        dataBeansList2.clear();
                        adapter.notifyDataSetChanged();

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
//                        ToastHelper.show(getActivity(),"数据不足");
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
                    adapter.setLoadState(adapter.LOADING);
                    if (dataBeansList2.size() < 52) {
                        // 模拟获取网络数据，延时1s
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                    getData();
                                        adapter.setLoadState(adapter.LOADING_COMPLETE);
                                    }
                                });
                            }
                        }, 1000);
                    } else {
                        // 显示加载到底的提示
                        adapter.setLoadState(adapter.LOADING_END);
                    }
                }
            });
        }

    }
}
