package com.example.ouc.demo.ui.activity.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.ItemOrderListAdapter;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.OrderListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.weigets.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_title, tv_right;
    private ImageView iv_left;
    private ItemOrderListAdapter itemOrderListAdapter;
    private Gson gson = new Gson();
    private OrderListEntity orderListEntity;
    private ArrayList<OrderListEntity.DataBean> orderList;
    private SwipeRefreshLayout orderlistrefreshLayout;
    private RecyclerView orderlistrecyclerView;
    private int PAGE_COUNT = 10;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        initTitle();
        findView();
        getOrderList();
    }

    private void initTitle() {
        iv_left = findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("订单管理");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
//        tv_right.setText("");
//        tv_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 调用保存地址
//            }
//        });
    }

    private void findView() {
        orderlistrefreshLayout = (SwipeRefreshLayout) findViewById(R.id.orderlist_refreshLayout);
        orderlistrecyclerView = (RecyclerView) findViewById(R.id.orderlist_recyclerview);
    }

    private void initRefreshLayout() {
        orderlistrefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        orderlistrefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        if (orderList != null) {
            if (orderList.size() <= 7) {
                PAGE_COUNT = 15;
                itemOrderListAdapter = new ItemOrderListAdapter(getDatas(0, PAGE_COUNT), OrderListActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
            } else {
                itemOrderListAdapter = new ItemOrderListAdapter(getDatas(0, PAGE_COUNT), OrderListActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
            }
        }


        mLayoutManager = new GridLayoutManager(this, 1);
        orderlistrecyclerView.setLayoutManager(mLayoutManager);
        orderlistrecyclerView.setAdapter(itemOrderListAdapter);
        if (orderList != null) {
            itemOrderListAdapter.notifyDataSetChanged();
            orderlistrecyclerView.setItemAnimator(new DefaultItemAnimator());
            //添加Android自带的分割线
            orderlistrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
        orderlistrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (itemOrderListAdapter.isFadeTips() == false && lastVisibleItem + 1 == itemOrderListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(itemOrderListAdapter.getRealLastPosition(), itemOrderListAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (itemOrderListAdapter.isFadeTips() == true && lastVisibleItem + 2 == itemOrderListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(itemOrderListAdapter.getRealLastPosition(), itemOrderListAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        orderlistrecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(OrderListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  recyclerView的点击事件
                if (orderList.size() != 0) {
                    Intent intent = new Intent();
                    intent.setClass(OrderListActivity.this, OrderDetailsActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("phone", orderList.get(position).getPhone() + "");
                    mBundle.putString("createdate", orderList.get(position).getCreatedate() + "");
                    mBundle.putString("orderno", orderList.get(position).getOrderno() + "");
                    mBundle.putString("shopname", orderList.get(position).getShopname() + "");
                    mBundle.putString("info", orderList.get(position).getInfo() + "");
                    mBundle.putString("delivename", orderList.get(position).getDelivename() + "");//收货人名称
                    mBundle.putString("bannerone", orderList.get(position).getBannerone() + "");
                    mBundle.putString("updatedate", orderList.get(position).getUpdatedate() + "");
                    mBundle.putString("address", orderList.get(position).getAddress() + "");
                    mBundle.putString("money", orderList.get(position).getMoney() + "");
                    mBundle.putString("shopid", orderList.get(position).getShopid() + "");
                    mBundle.putString("username", orderList.get(position).getUsername() + "");
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO  recyclerView的长按事件
                Log.d("", "onLongClick position : " + posotion);
            }
        }));

    }

    @Override
    public void onRefresh() {
        orderlistrefreshLayout.setRefreshing(true);
        itemOrderListAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                orderlistrefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        ArrayList<OrderListEntity.DataBean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            itemOrderListAdapter.updateList(newDatas, true);
        } else {
            itemOrderListAdapter.updateList(null, false);
        }
    }

    private ArrayList<OrderListEntity.DataBean> getDatas(final int firstIndex, final int lastIndex) {
        ArrayList<OrderListEntity.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < orderList.size()) {
                resList.add(orderList.get(i));
            }
        }
        return resList;
    }

    /**
     * 接口名：getOrderList
     * Post   获取订单列表
     */
    private void getOrderList() {
        Map<String, String> map = new HashMap<>();
        map.put("memberid", getStringSharePreferences("id", "id"));
        map.put("pageindex", "0");
        map.put("pagenum", "10000");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/bas/order/getOrderByMemberId.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    orderListEntity = gson.fromJson(result, OrderListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<OrderListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    orderList = gson.fromJson(gson.toJson(orderListEntity.getData()), listType2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (orderListEntity.getCode() == 200) {
                                initRefreshLayout();
                                initRecyclerView();
                            } else {
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
