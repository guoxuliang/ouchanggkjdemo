package com.example.ouc.demo.ui.activity.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.example.ouc.demo.adapter.ItemAddressListAdapter;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.AddSettingAddressEntity;
import com.example.ouc.demo.entity.AddressListEntity;
import com.example.ouc.demo.entity.DeleteAddressEntity;
import com.example.ouc.demo.http.HttpUtils;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddressListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ProgersssDialog progersssDialog;
    private SwipeRefreshLayout listrefreshLayout;
    private RecyclerView listrecyclerView;
    private ItemAddressListAdapter addressListAdapter;
    private int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem = 0;
    private TextView tv_right,tv_left;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TextView tv_title;
    private ImageView iv_left;
    private String url = Constants.SERVER_BASE_URL +"app/bas/user/getUserAddress.action?";
    private Gson gson = new Gson();
    private AddressListEntity addressListEntity;
    private ArrayList<AddressListEntity.DataBean> queryAddresList;
    private DeleteAddressEntity deleteAddressEntity;
    private String userid="";
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);

        initTitle();
        initViews();
        findView();
        progersssDialog = new ProgersssDialog(this);
         userid=getStringSharePreferences("id","id");
        getUserAddress(url+"userid="+userid);
    }


    private void initTitle() {
        iv_left  = findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("收货地址管理");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("添加新地址");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 调用保存地址
            }
        });
    }

    private void initViews() {
        tv_right = findViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("添加新地址");
        tv_left =  findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressListActivity.this.finish();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(AddressListActivity.this,AddSettingAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        listrefreshLayout = (SwipeRefreshLayout)findViewById(R.id.shoplist_refreshLayout);
        listrecyclerView = (RecyclerView)findViewById(R.id.shoplist_recyclerview);
    }

    private void initRefreshLayout() {
        listrefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        listrefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        if (queryAddresList.size() <= 7) {
            PAGE_COUNT = 15;
            addressListAdapter = new ItemAddressListAdapter(getDatas(0, PAGE_COUNT), AddressListActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        } else {
            addressListAdapter = new ItemAddressListAdapter(getDatas(0, PAGE_COUNT), AddressListActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        }

        mLayoutManager = new GridLayoutManager(this, 1);
        listrecyclerView.setLayoutManager(mLayoutManager);
        listrecyclerView.setAdapter(addressListAdapter);
        addressListAdapter.notifyDataSetChanged();
        listrecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
        listrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        listrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (addressListAdapter.isFadeTips() == false && lastVisibleItem + 1 == addressListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(addressListAdapter.getRealLastPosition(), addressListAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (addressListAdapter.isFadeTips() == true && lastVisibleItem + 2 == addressListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(addressListAdapter.getRealLastPosition(), addressListAdapter.getRealLastPosition() + PAGE_COUNT);
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

        listrecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AddressListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  recyclerView的点击事件

                Intent intent = new Intent();
                intent.setClass(AddressListActivity.this, AddSettingAddressActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("id", queryAddresList.get(position).getId()+"");
                mBundle.putString("province", queryAddresList.get(position).getProvince()+"");
                mBundle.putString("city", queryAddresList.get(position).getCity()+"");
                mBundle.putString("district", queryAddresList.get(position).getDistrict()+"");
                mBundle.putString("detailed_address", queryAddresList.get(position).getDetailed_address()+"");
                mBundle.putString("delivename", queryAddresList.get(position).getDelivename()+"");
                mBundle.putString("phone", queryAddresList.get(position).getPhone()+"");
                mBundle.putString("isdefault", queryAddresList.get(position).getIsdefault()+"");
                intent.putExtras(mBundle);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO  recyclerView的长按事件
                Log.d("", "onLongClick position : " + posotion);
                 id = String.valueOf(queryAddresList.get(posotion).getId());
                showNormalDialog(id);
            }
        }));

    }
    private void updateRecyclerView(int fromIndex, int toIndex) {
        ArrayList<AddressListEntity.DataBean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            addressListAdapter.updateList(newDatas, true);
        } else {
            addressListAdapter.updateList(null, false);
        }
    }

    private ArrayList<AddressListEntity.DataBean> getDatas(final int firstIndex, final int lastIndex) {
        ArrayList<AddressListEntity.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < queryAddresList.size()) {
                resList.add(queryAddresList.get(i));
            }
        }
        return resList;
    }
    @Override
    public void onRefresh() {
        listrefreshLayout.setRefreshing(true);
        addressListAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listrefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    /**
     * 接口名：getUserAddress
     * Get请求   获取收获地址列表接口
     */
    private void getUserAddress(String url) {


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
                    Log.i("result--getUserAddress", "result--getUserAddress" + result);
                    progersssDialog.dismiss();
                    Log.i("addressListEntity", "addressListEntity:" + result);
                    addressListEntity = gson.fromJson(result, AddressListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<AddressListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    queryAddresList = gson.fromJson(gson.toJson(addressListEntity.getData()), listType2);

                    Log.i("queryShopList", "queryShopList" + queryAddresList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = addressListEntity.getCode();
                            if (code==200) {
                                initRefreshLayout();
                                initRecyclerView();
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


    private void showNormalDialog(final String id){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(AddressListActivity.this);
        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("");
        normalDialog.setMessage("确认删除此地址");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        delAddress(id);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }


    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：删除地址
     */
    private void delAddress(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/bas/user/delAddress.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    deleteAddressEntity = gson.fromJson(result, DeleteAddressEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (deleteAddressEntity.getCode() == 200) {
                                ToastHelper.show(AddressListActivity.this, deleteAddressEntity.getMsg());
                                AddressListActivity.this.finish();
                            } else {
                                ToastHelper.show(AddressListActivity.this, deleteAddressEntity.getMsg());
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
