package com.example.ouc.demo.ui.activity.shopping;

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
import com.example.ouc.demo.adapter.ItemAllCommentsListAdapter;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.AllCommentsEntity;
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

public class AllCommentsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_title,tv_right;
    private ImageView iv_left;
    private Gson gson = new Gson();
    private SwipeRefreshLayout allcommentslistrefreshLayout;
    private RecyclerView allcommentslistrecyclerView;
    private int PAGE_COUNT = 10;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem = 0;
    private ItemAllCommentsListAdapter itemAllCommentsListAdapter;
    private AllCommentsEntity allCommentsEntity;
    private ArrayList<AllCommentsEntity.DataBean> allcommentdData;
private String goodsid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcomment);
        initTitle();
        initViews();
        findView();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            goodsid = bundle.getString("productid");
        }

        getAllComments();
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
        tv_title.setText("全部评论");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
    }

    private void initViews() {
    }
    private void findView() {
        allcommentslistrefreshLayout = (SwipeRefreshLayout) findViewById(R.id.allcommentslist_refreshLayout);
        allcommentslistrecyclerView = (RecyclerView) findViewById(R.id.allcommentslist_recyclerview);
    }
    private void initRefreshLayout() {
        allcommentslistrefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        allcommentslistrefreshLayout.setOnRefreshListener(this);
    }



    private void initRecyclerView() {
        if (allcommentdData != null) {
            if (allcommentdData.size() <= 7) {
                PAGE_COUNT = 15;
                itemAllCommentsListAdapter = new ItemAllCommentsListAdapter(getDatas(0, PAGE_COUNT), AllCommentsActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
            } else {
                itemAllCommentsListAdapter = new ItemAllCommentsListAdapter(getDatas(0, PAGE_COUNT), AllCommentsActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
            }
        }


        mLayoutManager = new GridLayoutManager(this, 1);
        allcommentslistrecyclerView.setLayoutManager(mLayoutManager);
        allcommentslistrecyclerView.setAdapter(itemAllCommentsListAdapter);
        if (allcommentdData != null) {
            itemAllCommentsListAdapter.notifyDataSetChanged();
            allcommentslistrecyclerView.setItemAnimator(new DefaultItemAnimator());
            //添加Android自带的分割线
            allcommentslistrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
        allcommentslistrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (itemAllCommentsListAdapter.isFadeTips() == false && lastVisibleItem + 1 == itemAllCommentsListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(itemAllCommentsListAdapter.getRealLastPosition(), itemAllCommentsListAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (itemAllCommentsListAdapter.isFadeTips() == true && lastVisibleItem + 2 == itemAllCommentsListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(itemAllCommentsListAdapter.getRealLastPosition(), itemAllCommentsListAdapter.getRealLastPosition() + PAGE_COUNT);
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

        allcommentslistrecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AllCommentsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  recyclerView的点击事件
//                if (allcommentdData.size() != 0) {
//                    Intent intent = new Intent();
//                    intent.setClass(AllCommentsActivity.this, OrderDetailsActivity.class);
//                    Bundle mBundle = new Bundle();
//                    mBundle.putString("phone", orderList.get(position).getPhone() + "");
//                    mBundle.putString("createdate", orderList.get(position).getCreatedate() + "");
//                    mBundle.putString("orderno", orderList.get(position).getOrderno() + "");
//                    mBundle.putString("shopname", orderList.get(position).getShopname() + "");
//                    mBundle.putString("info", orderList.get(position).getInfo() + "");
//                    mBundle.putString("delivename", orderList.get(position).getDelivename() + "");//收货人名称
//                    mBundle.putString("bannerone", orderList.get(position).getBannerone() + "");
//                    mBundle.putString("updatedate", orderList.get(position).getUpdatedate() + "");
//                    mBundle.putString("address", orderList.get(position).getAddress() + "");
//                    mBundle.putString("money", orderList.get(position).getMoney() + "");
//                    mBundle.putString("id", orderList.get(position).getId() + "");
//                    mBundle.putString("username", orderList.get(position).getUsername() + "");
//                    intent.putExtras(mBundle);
//                    startActivity(intent);
//                }
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

    }
    private void updateRecyclerView(int fromIndex, int toIndex) {
        ArrayList<AllCommentsEntity.DataBean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            itemAllCommentsListAdapter.updateList(newDatas, true);
        } else {
            itemAllCommentsListAdapter.updateList(null, false);
        }
    }

    private ArrayList<AllCommentsEntity.DataBean> getDatas(final int firstIndex, final int lastIndex) {
        ArrayList<AllCommentsEntity.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < allcommentdData.size()) {
                resList.add(allcommentdData.get(i));
            }
        }
        return resList;
    }
    /**
     * getAllComments
     * 获取商品评论列表
     */
    private void getAllComments() {
        Map<String, String> map = new HashMap<>();
        map.put("goodsid",goodsid);
        map.put("pageindex", "0");
        map.put("pagenum", "10000");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/shopCommon/getComment.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    allCommentsEntity = gson.fromJson(result, AllCommentsEntity.class);
                    Type listType2 = new TypeToken<ArrayList<AllCommentsEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    allcommentdData = gson.fromJson(gson.toJson(allCommentsEntity.getData()), listType2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (allCommentsEntity.getCode() == 200) {
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
