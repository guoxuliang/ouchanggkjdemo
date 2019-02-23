package com.example.ouc.demo.ui.activity.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.ItemShopListAdapter;
import com.example.ouc.demo.entity.ModuleShoppingListEntity;
import com.example.ouc.demo.entity.ShoppingqueryListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.weigets.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShoppingListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private TextView texttest;
    private ImageView btn_back_search;
    private String search_content, code;
    private ArrayList<ShoppingqueryListEntity.DataBean> queryShopList;
    private ProgersssDialog progersssDialog;
    private ShoppingqueryListEntity shoppingqueryListEntity;
    private Gson gson = new Gson();
    private ItemShopListAdapter shopListAdapter;
    private SwipeRefreshLayout listrefreshLayout;
    private RecyclerView listrecyclerView;
    private int PAGE_COUNT = 10;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem = 0;
    private EditText et_input;
    private ImageView btn_search;

    //用户点击分类获取到的商品列表
    private ModuleShoppingListEntity moduleShoppingListEntity;
    private ArrayList<ModuleShoppingListEntity.DataBean> queryModuleShopList;

    private String url= Constants.SERVER_BASE_URL + "app/goodsInfo/getAllProductByName.action?";
    private String url2= Constants.SERVER_BASE_URL + "system/shop/goods/getGoodsListByDictId.action?";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        initViews();
        findView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            search_content = bundle.getString("search_content");
            progersssDialog = new ProgersssDialog(this);
            getShoppingList(url+"name="+search_content+"&start="+0+"&limit="+10);
        }
        Bundle bundle2 = getIntent().getExtras();
        if(bundle2!=null){
             code = bundle.getString("code");
            Log.i("code==","code=="+code);
            getModuleShoppingList(url2+"code="+code+"&start="+0+"&limit="+100);
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if(search_content!=null){
//            getShoppingList(url+"name="+search_content+"&start="+0+"&limit="+10);
//        }else {
//            getModuleShoppingList(url2+"code="+code+"&start="+0+"&limit="+100);
//        }

    }

    private void findView() {
        listrefreshLayout = (SwipeRefreshLayout)findViewById(R.id.shoplist_refreshLayout);
        listrecyclerView = (RecyclerView)findViewById(R.id.shoplist_recyclerview);
    }

    private void initViews() {
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String  searchQuery = et_input.getText().toString().trim();
               if(searchQuery!=null){
                   getShoppingList(url+"name="+searchQuery+"&start="+0+"&limit="+10);
               }

            }
        });
        et_input = findViewById(R.id.et_input);
        et_input.setText(search_content);
        btn_back_search = findViewById(R.id.btn_back_search);
        btn_back_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingListActivity.this.finish();
//                if(queryShopList!=null){
//                    if(queryShopList.size()>0){
//                        queryShopList.clear();
//                    }
//                }
            }
        });
    }
    private void initRefreshLayout() {
        listrefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        listrefreshLayout.setOnRefreshListener(this);
    }
    private void initRecyclerView() {
        if(queryShopList!=null){
            if (queryShopList.size() <= 7) {
                PAGE_COUNT = 15;
                shopListAdapter = new ItemShopListAdapter(getDatas(0, PAGE_COUNT), ShoppingListActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
            } else {
                shopListAdapter = new ItemShopListAdapter(getDatas(0, PAGE_COUNT), ShoppingListActivity.this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
            }
        }


        mLayoutManager = new GridLayoutManager(this, 1);
        listrecyclerView.setLayoutManager(mLayoutManager);
        listrecyclerView.setAdapter(shopListAdapter);
        if(queryShopList!=null){
            shopListAdapter.notifyDataSetChanged();
            listrecyclerView.setItemAnimator(new DefaultItemAnimator());
            //添加Android自带的分割线
            listrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
        listrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (shopListAdapter.isFadeTips() == false && lastVisibleItem + 1 == shopListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(shopListAdapter.getRealLastPosition(), shopListAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (shopListAdapter.isFadeTips() == true && lastVisibleItem + 2 == shopListAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(shopListAdapter.getRealLastPosition(), shopListAdapter.getRealLastPosition() + PAGE_COUNT);
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

        listrecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ShoppingListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  recyclerView的点击事件

                Intent intent = new Intent();
                intent.setClass(ShoppingListActivity.this, ShoppingGoodsDetailsActivity.class);
                Bundle mBundle = new Bundle();
//                mBundle.putString("bannerone", queryShopList.get(position).getBannerone()+"");
//                mBundle.putString("bannertwo", queryShopList.get(position).getBannertwo()+"");
//                mBundle.putString("bannerthree", queryShopList.get(position).getBannerthree()+"");
//                mBundle.putString("price", queryShopList.get(position).getPrice()+"");
//                mBundle.putString("name", queryShopList.get(position).getName()+"");
                mBundle.putString("id", queryShopList.get(position).getId()+"");
//                mBundle.putString("number", queryShopList.get(position).getNumber()+"");
//                mBundle.putString("goodsinfo", queryShopList.get(position).getGoodsinfo()+"");
                intent.putExtras(mBundle);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO  recyclerView的长按事件
                Log.d("", "onLongClick position : " + posotion);
            }
        }));

    }

    /**
     * 接口名：getRecommendedList
     * Get请求   获取商品列表请求接口
     */
    private void getShoppingList(String url) {


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
                    Log.i("shoppingqueryListEntity", "shoppingqueryListEntitygetShoppingList:" + result);
                    shoppingqueryListEntity = gson.fromJson(result, ShoppingqueryListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<ShoppingqueryListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    queryShopList = gson.fromJson(gson.toJson(shoppingqueryListEntity.getData()), listType2);

                    Log.i("queryShopList", "queryShopList" + queryShopList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingqueryListEntity.getCode();
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

    @Override
    public void onRefresh() {
        listrefreshLayout.setRefreshing(true);
        shopListAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listrefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
    private void updateRecyclerView(int fromIndex, int toIndex) {
        ArrayList<ShoppingqueryListEntity.DataBean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            shopListAdapter.updateList(newDatas, true);
        } else {
            shopListAdapter.updateList(null, false);
        }
    }
    private ArrayList<ShoppingqueryListEntity.DataBean> getDatas(final int firstIndex, final int lastIndex) {
        ArrayList<ShoppingqueryListEntity.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < queryShopList.size()) {
                resList.add(queryShopList.get(i));
            }
        }
        return resList;
    }

    /**
     * 接口名：getRecommendedList
     * Get请求   点击模块获取商品列表请求接口
     */
    private void getModuleShoppingList(String url) {


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
                    Log.i("shoppingqueryListEntity", "shoppingqueryListEntity:" + result);
                    shoppingqueryListEntity = gson.fromJson(result, ShoppingqueryListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<ShoppingqueryListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    queryShopList = gson.fromJson(gson.toJson(shoppingqueryListEntity.getData()), listType2);

                    Log.i("queryShopList", "queryShopList" + queryShopList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingqueryListEntity.getCode();
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
}
