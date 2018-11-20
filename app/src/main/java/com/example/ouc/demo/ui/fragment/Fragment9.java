package com.example.ouc.demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.MyOrderAdapter3;
import com.example.ouc.demo.adapter.MyOrderAdapter4;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.ShareSumEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment9 extends BaseFragment {
    private String id;
    private View view;
    private ListView orderlist9;
    private ShareSumEntity shareSumEntity;
    private List<ShareSumEntity.DataBean> sharesumBeans;
    private Gson gson = new Gson();
    private int code;
    private MyOrderAdapter4 myOrderAdapter;
    private String userid;
    private String type="2";//type=0 未完成   type=1 已完成   type=2 已取消
    private TextView nodata;
    private ProgersssDialog progersssDialog;
    private SwipeRefreshLayout sr1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment9, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        id= getStringSharePreferences("id","id");
        progersssDialog = new ProgersssDialog(getActivity());
        initViews();
        getShareSum();

    }

    private void initViews() {
        orderlist9 = view.findViewById(R.id.list_order_f9);
        nodata = view.findViewById(R.id.nodata);
        sr1 = view.findViewById(R.id.sr1);

    }


    private void getShareSum() {
        Map<String, String> map = new HashMap<>();
        map.put("userid", id);
//        map.put("userid", "5");
        map.put("start", "0");
        map.put("limit", "1000000");
        Log.i("", "");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemUserTaskController/getShareRecoreList.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:getShareSum" + result);
                    shareSumEntity = gson.fromJson(result, ShareSumEntity.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (shareSumEntity.getCode() == 200) {
                                ToastHelper.show(getActivity(), shareSumEntity.getMsg());
                                sharesumBeans=shareSumEntity.getData();
                                changeDatas();
                            }else {
                                ToastHelper.show(getActivity(), shareSumEntity.getMsg());
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void changeDatas() {
        if (sharesumBeans != null) {
            myOrderAdapter = new MyOrderAdapter4(getActivity(), sharesumBeans);
            orderlist9.setAdapter(myOrderAdapter);
            ToastHelper.show(getActivity(),shareSumEntity.getMsg());

            sr1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(sharesumBeans.size()==0){
                        getShareSum();
                    }
                    if(sharesumBeans.size()>15){
                        // 刷新数据
                        sharesumBeans.clear();
//                getData();
                        myOrderAdapter.notifyDataSetChanged();

                        // 延时1s关闭下拉刷新
                        sr1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (sr1 != null && sr1.isRefreshing()) {
                                    sr1.setRefreshing(false);
                                }
                            }
                        }, 1000);
                    }else{
                        ToastHelper.show(getActivity(),"数据不足");
                        sr1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (sr1 != null && sr1.isRefreshing()) {
                                    sr1.setRefreshing(false);
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
