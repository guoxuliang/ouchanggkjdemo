package com.example.ouc.demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.MyOrderAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.MyOrderEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.WithdrawalRecordActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment4 extends BaseFragment {
    private View view;
    private ListView orderlist4;
    private MyOrderEntity myOrderEntity;
    private ArrayList<MyOrderEntity.DataBean> orderDataBeans;
    private Gson gson = new Gson();
    private int code;
    private MyOrderAdapter myOrderAdapter;
    private String userid;
    private String type="0";//type=0 未完成   type=1 已完成   type=2 已取消
    private TextView nodata;
    private ProgersssDialog progersssDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment4, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userid= getStringSharePreferences("id","id");
        progersssDialog = new ProgersssDialog(getActivity());
        initViews();
        getMyOrderList();

    }

    private void initViews() {
        orderlist4 = view.findViewById(R.id.list_order_f4);
        nodata = view.findViewById(R.id.nodata);
        swipeRefreshLayout = view.findViewById(R.id.sr1);
    }


    private void getMyOrderList() {
        /**c
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        String url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserTaskController/getTaskStatus.action?userid="+userid+"&type="+type+"&start=0"+"&limit=1000000000";
        Log.i("url", "url:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progersssDialog.dismiss();
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    myOrderEntity = gson.fromJson(result, MyOrderEntity.class);
                    Type listType2 = new TypeToken<ArrayList<MyOrderEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    orderDataBeans = gson.fromJson(gson.toJson(myOrderEntity.getData()), listType2);
                    code = myOrderEntity.getCode();
                    Log.i("666", "eeee" + orderDataBeans);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            changeDatas();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("eeee", "eeee" + e);
                }

            }
        });
    }

    private void changeDatas() {
        if (orderDataBeans != null) {
            myOrderAdapter = new MyOrderAdapter(getActivity(), orderDataBeans);
            orderlist4.setAdapter(myOrderAdapter);
            ToastHelper.show(getActivity(),myOrderEntity.getMsg());

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(orderDataBeans.size()>15){
                        // 刷新数据
                        orderDataBeans.clear();
//                getData();
                        myOrderAdapter.notifyDataSetChanged();

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
                        ToastHelper.show(getActivity(),"数据不足");
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





        }else {
                nodata.setVisibility(View.VISIBLE);
                ToastHelper.show(getActivity(),"暂无数据");
            }
        }
}
