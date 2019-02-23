package com.example.ouc.demo.ui.fragment;

import android.content.Context;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.ItemShopAdapter;
import com.example.ouc.demo.adapter.ShoppingCategoryAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.ShoppingCategoryEntity;
import com.example.ouc.demo.entity.ShoppingListEntity;
import com.example.ouc.demo.entity.ShoppingMainBannerTopEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.ShoppingCartActivity;
import com.example.ouc.demo.ui.activity.WebViewActivity;
import com.example.ouc.demo.ui.activity.shopping.LocationCtiyActivity;
import com.example.ouc.demo.ui.activity.shopping.OrderListActivity;
import com.example.ouc.demo.ui.activity.shopping.PurchaseOrderActivity;
import com.example.ouc.demo.ui.activity.shopping.SearchActivity;
import com.example.ouc.demo.ui.activity.shopping.ShoppingGoodsDetailsActivity;
import com.example.ouc.demo.ui.activity.shopping.ShoppingListActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.RequestCodeInfo;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class FragmentShop extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    View v;
    private android.support.v7.widget.Toolbar toolbar;
    private com.youth.banner.Banner banner2;
    private ArrayList<String> list_path = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
    private ArrayList<String> list_path_top = new ArrayList<>();
    private ArrayList<String> list_title_top = new ArrayList<>();
    private EditText et_nono_left;
    private TextView locationctiy;
    private ImageView saocode,myorderlist;
    private Banner banner;
    private ItemShopAdapter shopadapter;
//    private SwipeRefreshLayout srefreshLayout;
    private RecyclerView srecyclerView;
    private int PAGE_COUNT = 10;
    private int REQUEST_CODE_SCAN = 111;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem = 0;
    private ArrayList<ShoppingListEntity.DataBean> dataShopList;
    private ProgersssDialog progersssDialog;
    private ShoppingListEntity shoppingListEntity;
    private Gson gson = new Gson();
    private String url= Constants.SERVER_BASE_URL + "app/goodsInfo/getAllPassGoodsInfo.action?pageindex=0&pagenum=10";
    private String url2= Constants.SERVER_BASE_URL + "system/shop/shopBannerController/HomeBannerList.action?";
    private ShoppingMainBannerTopEntity shoppingMainBannerTopEntity;
    private ArrayList<ShoppingMainBannerTopEntity.DataBean> dataShopBannerTop;
    //===========主界面分类接口==============
    private ShoppingCategoryEntity shoppingCategoryEntity;
    private ArrayList<ShoppingCategoryEntity.DataBean> shoppingCategoryData;
    private String url3=Constants.SERVER_BASE_URL +"system/shop/shopcategory/getDictList.action?start=0&limit=8";
    private ShoppingCategoryAdapter shoppingCategoryAdapter;
    private GridView gv_category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmentshop, null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progersssDialog = new ProgersssDialog(getActivity());
        initTitle();
        initViews();
        findView();
        getShoppingBanner(url2+"start="+"0"+"&limit="+"5");
        getShoppingCategory(url3);
        getShoppingList(url);

    }

    private void categoryData() {
        if (shoppingCategoryData!=null){
            shoppingCategoryAdapter = new ShoppingCategoryAdapter(getActivity(),shoppingCategoryData,gv_category);
            gv_category.setAdapter(shoppingCategoryAdapter);
        }
    }

    private void initTitle() {
    }

    private void initViews() {
        gv_category = v.findViewById(R.id.gv_category);
        locationctiy = v.findViewById(R.id.locationctiy);
        saocode = v.findViewById(R.id.saocode);
        myorderlist = v.findViewById(R.id.myorderlist);
//        locationctiy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(getActivity(), LocationCtiyActivity.class), RequestCodeInfo.GETCITY);
//            }
//        });
         banner = (Banner) v.findViewById(R.id.banner);
        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ArrayList<Integer> resArray = new ArrayList<>();
//        resArray.add(R.mipmap.ic_one);
//        resArray.add(R.mipmap.ic_two);
//        resArray.add(R.mipmap.ic_three);
//        resArray.add(R.mipmap.ic_four);
//        resArray.add(R.mipmap.ic_five);
//        banner.setImageResources(resArray);
//        banner.startImageCycle();
        banner2 = v.findViewById(R.id.banner2);
        et_nono_left = v.findViewById(R.id.et_nono_left);
        et_nono_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(SearchActivity.class);
            }
        });
//        srecyclerView = v.findViewById(R.id.recyclerview);
//        srecyclerView.setNestedScrollingEnabled(false);
        list_path = new ArrayList<>();
//        //放标题的集合
        list_title = new ArrayList<>();
        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/1.jpg");
        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/2.jpg");
        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/3.jpg");
        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/4.jpg");
        list_title.add("掌上营销");
        list_title.add("畅联达广告机");
        list_title.add("广告新模式");
        list_title.add("广告推广");
        bannerload(list_path,list_title);

        gv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String code = String.valueOf(shoppingCategoryData.get(i).getDICT_DETALL_CODE());
                Log.i("code==","code=="+code);
                Intent intent=new Intent(getActivity(),ShoppingListActivity.class);
                Bundle bundlecode=new Bundle();
                bundlecode.putString("code",code);
                intent.putExtras(bundlecode);
                startActivity(intent);
            }
        });
        saocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCart = new Intent(getActivity(), ShoppingCartActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("memberid", getStringSharePreferences("id", "id"));
                intentCart.putExtras(bundle1);
                startActivity(intentCart);
            }
        });

        myorderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 获取订单列表
                 */
                Intent intent=new Intent(getActivity(),OrderListActivity.class);
                startActivity(intent);
            }
        });
    }
    private void bannerloadTop(ArrayList<String> list_path,ArrayList<String> list_title) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new FragmentShop.MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                //必须最后调用的方法，启动轮播图。
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                       String bannerurl = dataShopBannerTop.get(position).getBannerurl();
                        Bundle bundle=new Bundle();
                        if(!bannerurl.equals("")){
                            bundle.putString("bannerurl",bannerurl);
                            openActivity(WebViewActivity.class,bundle);
                        }else {
                            ToastHelper.show(getActivity(),"此内容不存在");
                        }
                    }
                }).start();
    }


    private void bannerload(ArrayList<String> list_path,ArrayList<String> list_title) {
        banner2.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner2.setImageLoader(new FragmentShop.MyLoader());
        //设置图片网址或地址的集合
        banner2.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner2.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner2.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner2.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner2.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner2.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                //必须最后调用的方法，启动轮播图。
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getActivity(), "点击了" + position + "项", Toast.LENGTH_SHORT).show();
//                       String bannerurl = dataShopBannerTop.get(position).getBannerurl();
//                        Bundle bundle=new Bundle();
//                        if(!bannerurl.equals("")){
//                            bundle.putString("bannerurl",bannerurl);
//                            openActivity(WebViewActivity.class,bundle);
//                        }else {
//                            ToastHelper.show(getActivity(),"此内容不存在");
//                        }
                    }
                }).start();
    }


    private void findView() {
//        srefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.shop_refreshLayout);
        srecyclerView = (RecyclerView) v.findViewById(R.id.shop_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        srecyclerView.setLayoutManager(layoutManager);
    }

    private void initRefreshLayout() {
//        srefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
//                android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        srefreshLayout.setOnRefreshListener(this);
    }
    @Override
    public void onRefresh() {
//        srefreshLayout.setRefreshing(true);
        shopadapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                srefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
    private void updateRecyclerView(int fromIndex, int toIndex) {
        ArrayList<ShoppingListEntity.DataBean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            shopadapter.updateList(newDatas, true);
        } else {
            shopadapter.updateList(null, false);
        }
    }
    private ArrayList<ShoppingListEntity.DataBean> getDatas(final int firstIndex, final int lastIndex) {
        ArrayList<ShoppingListEntity.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < dataShopList.size()) {
                resList.add(dataShopList.get(i));
            }
        }
        return resList;
    }


    private void initRecyclerView() {
        if (dataShopList.size() <= 15) {
            PAGE_COUNT = 15;
        shopadapter = new ItemShopAdapter(getDatas(0, PAGE_COUNT), getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        } else {
            shopadapter = new ItemShopAdapter(getDatas(0, PAGE_COUNT), getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        }

        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        srecyclerView.setLayoutManager(mLayoutManager);
        srecyclerView.setAdapter(shopadapter);
        shopadapter.notifyDataSetChanged();
        srecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
        srecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        srecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (shopadapter.isFadeTips() == false && lastVisibleItem + 1 == shopadapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(shopadapter.getRealLastPosition(), shopadapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (shopadapter.isFadeTips() == true && lastVisibleItem + 2 == shopadapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(shopadapter.getRealLastPosition(), shopadapter.getRealLastPosition() + PAGE_COUNT);
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

        srecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  recyclerView的点击事件

                Intent intent = new Intent();
                intent.setClass(getActivity(), ShoppingGoodsDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("bannerone", dataShopList.get(position).getBannerone()+"");
                mBundle.putString("bannertwo", dataShopList.get(position).getBannertwo()+"");
                mBundle.putString("bannerthree", dataShopList.get(position).getBannerthree()+"");
                mBundle.putString("price", dataShopList.get(position).getPrice()+"");
                mBundle.putString("name", dataShopList.get(position).getName()+"");
                mBundle.putString("id", dataShopList.get(position).getId()+"");
                mBundle.putString("number", dataShopList.get(position).getNumber()+"");
                mBundle.putString("goodsinfo", dataShopList.get(position).getGoodsinfo()+"");
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
     * Get请求   推荐商品列表请求接口
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
                    Log.i("resultCodeFragment2", "resultCodeFragment2:" + result);
                    shoppingListEntity = gson.fromJson(result, ShoppingListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<ShoppingListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    dataShopList = gson.fromJson(gson.toJson(shoppingListEntity.getData()), listType2);

                    Log.i("dataBeansList2", "dataBeansList2" + dataShopList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingListEntity.getCode();
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



    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case RequestCodeInfo.GETCITY:
                    String city = data.getExtras().getString("city");
                    if (city != null) {
                        System.out.println("ccccccctttttt" + city);
                        locationctiy.setText(city);
                    }
                    break;
            }
        }
    }




    /**
     * 接口名：getRecommendedList
     * Get请求   推荐商品列表请求接口
     */
    private void getShoppingBanner(String url) {


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
                    Log.i("resultCodeFragment2", "resultCodeFragment2:" + result);
                    shoppingMainBannerTopEntity = gson.fromJson(result, ShoppingMainBannerTopEntity.class);
                    Type listType2 = new TypeToken<ArrayList<ShoppingMainBannerTopEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    dataShopBannerTop = gson.fromJson(gson.toJson(shoppingMainBannerTopEntity.getData()), listType2);

                    Log.i("dataShopBannerTop", "dataShopBannerTop" + dataShopBannerTop);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingMainBannerTopEntity.getCode();
                            if (code==200) {
                                for (int i=0;i<dataShopBannerTop.size();i++){
                                    list_path_top.add(dataShopBannerTop.get(i).getImg());
                                    list_title_top.add(dataShopBannerTop.get(i).getBannername());
                                }
                               if(list_path_top!=null&&list_title_top!=null){
                                   bannerloadTop(list_path_top,list_title_top);
                               }

                            }
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
     * 接口名：getRecommendedList
     * Get请求   主界面商品模块分类接口
     */
    private void getShoppingCategory(String url) {


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
                    Log.i("shoppingCategoryEntity", "shoppingCategoryEntity:" + result);
                    shoppingCategoryEntity = gson.fromJson(result, ShoppingCategoryEntity.class);
                    Type listType2 = new TypeToken<ArrayList<ShoppingCategoryEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    shoppingCategoryData = gson.fromJson(gson.toJson(shoppingCategoryEntity.getData()), listType2);

                    Log.i("shoppingCategoryData", "shoppingCategoryData" + shoppingCategoryData);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingMainBannerTopEntity.getCode();
                            if (code==200) {
                                categoryData();
                            }
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
