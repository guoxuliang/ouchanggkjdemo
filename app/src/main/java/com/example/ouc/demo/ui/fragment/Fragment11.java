package com.example.ouc.demo.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.AdvRecordAdapter;
import com.example.ouc.demo.adapter.AdvRecordAdapter2;
import com.example.ouc.demo.adapter.MyOrderAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.AdvRecordEntity;
import com.example.ouc.demo.entity.AdvRecordEntity2;
import com.example.ouc.demo.entity.MyOrderEntity;
import com.example.ouc.demo.http.HttpUtils;
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

public class Fragment11 extends BaseFragment {
    private View view;
    private ListView orderlist11;
    private AdvRecordEntity2 advRecordEntity;
    private ArrayList<AdvRecordEntity2.DataBean> advRecordDataBeans;
    private Gson gson = new Gson();
    private int code;
    private AdvRecordAdapter2 advRecordAdapter;
    private String userid;
    private String type="2";//   type=1 分享   type=2 观看
private TextView nodata;
    private ProgersssDialog progersssDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment11, container, false);
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
        orderlist11 = view.findViewById(R.id.list_order_f11);
        nodata = view.findViewById(R.id.nodata);

    }


    private void getMyOrderList() {
        /**c
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        String url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserTaskController/getWatchList.action?userid="+userid+"&type="+type+"&start=0"+"&limit=1000000000";
        Log.i("url", "url:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    progersssDialog.dismiss();
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    advRecordEntity = gson.fromJson(result, AdvRecordEntity2.class);
                    Type listType2 = new TypeToken<ArrayList<AdvRecordEntity2.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    advRecordDataBeans = gson.fromJson(gson.toJson(advRecordEntity.getData()), listType2);
                    code = advRecordEntity.getCode();
                    Log.i("666", "eeee" + advRecordDataBeans);
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
        if (advRecordDataBeans != null) {
            advRecordAdapter = new AdvRecordAdapter2(getActivity(), advRecordDataBeans);
            orderlist11.setAdapter(advRecordAdapter);
            ToastHelper.show(getActivity(),advRecordEntity.getMsg());
        }else {
            nodata.setVisibility(View.VISIBLE);
            ToastHelper.show(getActivity(),"暂无数据");
        }
    }
}
