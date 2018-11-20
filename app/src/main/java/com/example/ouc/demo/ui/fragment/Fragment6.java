package com.example.ouc.demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.MyOrderAdapter;
import com.example.ouc.demo.adapter.MyOrderAdapter3;
import com.example.ouc.demo.base.BaseFragment;
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

public class Fragment6 extends BaseFragment {
    private View view;
    private ListView orderlist6;
    private TextView nodata;
    private MyOrderEntity myOrderEntity;
    private ArrayList<MyOrderEntity.DataBean> orderDataBeans;
    private Gson gson = new Gson();
    private int code;
    private MyOrderAdapter3 myOrderAdapter3;
    private String userid;
    private String type = "2";//type=0 未完成   type=1 已完成   type=2 已取消
private ProgersssDialog progersssDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment6, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userid = getStringSharePreferences("id", "id");
        progersssDialog = new ProgersssDialog(getActivity());
        initViews();
        getMyOrderList();

    }

    private void initViews() {
        orderlist6 = view.findViewById(R.id.list_order_f6);
        nodata = view.findViewById(R.id.nodata);

    }


    private void getMyOrderList() {
        /**c
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        String url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserTaskController/getTaskStatus.action?userid=" + userid + "&type=" + type+"&start=0"+"&limit=1000000000";
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
            myOrderAdapter3 = new MyOrderAdapter3(getActivity(), orderDataBeans);
            orderlist6.setAdapter(myOrderAdapter3);
            ToastHelper.show(getActivity(),myOrderEntity.getMsg());
        }else {
            nodata.setVisibility(View.VISIBLE);
            ToastHelper.show(getActivity(),"暂无数据");
        }
    }
}