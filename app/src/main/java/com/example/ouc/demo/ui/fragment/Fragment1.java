package com.example.ouc.demo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.MyAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.RecommendedEntity;
import com.example.ouc.demo.entity.RecommendedListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.MainActivity;
import com.example.ouc.demo.ui.activity.AdvertisingVideoActivity;
import com.example.ouc.demo.ui.activity.DeliveryActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.MyOnScrollListener;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment1 extends BaseFragment implements MyOnScrollListener.OnloadDataListener {
    /**
     * 获取验证码
     */
    // 下载存储的文件名
    private static final String DOWNLOAD_NAME = "channelWe";
    private static final int REQUEST_CODE_PERMISSION_SD = 101;
    private static final int REQUEST_CODE_SETTING = 300;
    View v;
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private String updateUrl, updateInfo, lastForce, msg;
    private RecommendedEntity recommendedEntity;
    private RecommendedListEntity recommendedListEntity;
    private String code;
    private ImageView iv_bigimg;
    private ProgersssDialog progersssDialog;
    private boolean change = false;
    /**
     * 版本更新
     */
    private Gson gson = new Gson();
    private ImageView vi_tj, vi_tj2;
    private TextView rw_name, rw_name2, yd_count, yd_count2, jlj, jlj2, syrw, syrw2;

    private ArrayList<RecommendedEntity.DataBean> dataBeans2;
    private ArrayList<RecommendedListEntity.DataBean> dataBeansList2;
    private LinearLayout adv1, adv2;
    //ListView展示的数据项
    private List<RecommendedListEntity.DataBean> data;
    //ListView控件
    private ListView mList;
    //自定义适配器
    MyAdapter adapter;
    //底部加载更多布局
    View footer;
    private Button btn;
    private TextView tv_back, tv_content,tv_advertising;

    int start=0;
    int limit=1000000;
    String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/getIsCommTasklist.action?";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if(v==null){
            v = inflater.inflate(R.layout.fragment1, container, false);
        }

        return v;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progersssDialog = new ProgersssDialog(getActivity());
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isNetworkAvailable(getActivity())==true){
            getRecommended();
            String dhs="start="+start+"&"+"limit="+limit;
            getRecommendedList(url+dhs);
        }else {
            ToastHelper.show(getActivity(),"请检查网络");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initListData() {

        //首先加载默认数据，这里设置为10条
        getData();
        //显示到ListView上
        showListView(data);
        //自定义的滚动监听事件
        MyOnScrollListener onScrollListener = new MyOnScrollListener(footer);
        //设置接口回调
        onScrollListener.setOnLoadDataListener(this);
        //设置ListView的滚动监听事件
        mList.setOnScrollListener(onScrollListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * 初始化ListView数据，默认设置为10条
     */
    private void getData() {
        data = new ArrayList<>();
        if (dataBeansList2 != null) {
            for (int i = 0; i < dataBeansList2.size(); i++) {
                dataBeansList2.get(i);
                data.add(dataBeansList2.get(i));
            }
        } else {
            ToastHelper.show(getActivity(), "暂无数据");
        }
    }

    /**
     * 将数据加载到ListView上
     *
     * @param data
     */
    private void showListView(List<RecommendedListEntity.DataBean> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件

            //将底部加载一个加载更多的布局
            footer = LayoutInflater.from(getActivity()).inflate(R.layout.foot_boot, null);
            //初始状态为隐藏
            footer.setVisibility(View.GONE);
            //加入到ListView的底部
            mList.addFooterView(footer);
            //创建adpter数据
            adapter = new MyAdapter(data,getActivity());
            //设置adapter
            mList.setAdapter(adapter);
        } else {
            //不为空，则刷新数据
            this.data.addAll(data);
            //提醒ListView重新更新数据
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadData(List<RecommendedListEntity.DataBean> data) {
        //加载数据完成后，展示数据到ListView
        showListView(data);
    }

    private void initView() {

        tv_advertising=v.findViewById(R.id.tv_advertising);
        tv_advertising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), DeliveryActivity.class);
                startActivity(intent);
            }
        });
        Timer timer = new Timer();
        timer.schedule(task,1,500);  //参数分别是delay（多长时间后执行），duration（执行间隔）
        adv1 = v.findViewById(R.id.adv1);
        adv2 = v.findViewById(R.id.adv2);
        vi_tj = v.findViewById(R.id.vi_tj);
        vi_tj2 = v.findViewById(R.id.vi_tj2);
        rw_name = v.findViewById(R.id.rw_name);
        rw_name2 = v.findViewById(R.id.rw_name2);
        yd_count = v.findViewById(R.id.yd_count);
        yd_count2 = v.findViewById(R.id.yd_count2);
        jlj = v.findViewById(R.id.jlj);
        jlj2 = v.findViewById(R.id.jlj2);
        syrw = v.findViewById(R.id.syrw);
        syrw2 = v.findViewById(R.id.syrw2);
        mList = (ListView) v.findViewById(R.id.mList);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastHelper.show(getActivity(),"点击了"+i+"项");
                //TODO  点击列表跳转到视频播放页面
                Intent intent = new Intent();
                intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("name", dataBeansList2.get(i).getTitle());//名称
                mBundle.putString("gold", dataBeansList2.get(i).getGold() + "");//奖励金
                mBundle.putString("videourl", dataBeansList2.get(i).getVideo());//视频地址
                mBundle.putString("timelong", dataBeansList2.get(i).getTimelong());//视频地址
                mBundle.putString("content", dataBeansList2.get(i).getContent());//视频信息
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });


        iv_bigimg = v.findViewById(R.id.iv_bigimg);
        iv_bigimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show(getActivity(), "jdslkfjsd");
            }
        });
        banner = v.findViewById(R.id.banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("轮播图1");
        list_title.add("轮播图2");
        list_title.add("轮播图3");
        list_title.add("轮播图4");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
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
                        Log.i("tag", "你点了第" + position + "张轮播图");
                        Toast.makeText(getActivity(), "你点了第" + position + "张轮播图", Toast.LENGTH_LONG).show();
                    }
                }).start();
        adv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//TODO  点击广告跳转到视频详情页面
                Intent intent = new Intent();
                intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("name", dataBeans2.get(0).getTitle());//名称
                mBundle.putString("gold", dataBeans2.get(0).getGold() + "");//奖励金
                mBundle.putString("videourl", dataBeans2.get(0).getVideo());//视频地址
                mBundle.putString("timelong", dataBeans2.get(0).getTimelong());//视频地址
                mBundle.putString("content", dataBeans2.get(0).getContent());//视频信息
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        adv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//TODO  点击广告跳转到视频详情页面
                Intent intent = new Intent();
                intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("name", dataBeans2.get(1).getTitle());//名称
                mBundle.putString("gold", dataBeans2.get(1).getGold() + "");//奖励金
                mBundle.putString("videourl", dataBeans2.get(1).getVideo());//视频地址
                mBundle.putString("timelong", dataBeans2.get(1).getTimelong());//视频地址
                mBundle.putString("content", dataBeans2.get(1).getContent());//视频信息
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

    }

    private void initDatas() {
        if (dataBeans2 != null) {
            Glide.with(getActivity()).load(dataBeans2.get(0).getCover()).into(vi_tj);
            Glide.with(getActivity()).load(dataBeans2.get(1).getCover()).into(vi_tj2);
            rw_name.setText(dataBeans2.get(0).getTitle());
            rw_name2.setText(dataBeans2.get(1).getTitle());
            yd_count.setText("已浏览:" + dataBeans2.get(0).getBrowsevolume());
            yd_count2.setText("已浏览:" + dataBeans2.get(1).getBrowsevolume());
            jlj.setText("奖励金:￥" + dataBeans2.get(0).getGold() + "元");
            jlj2.setText("奖励金:￥" + dataBeans2.get(1).getGold() + "元");
            syrw.setText("剩余任务:" + dataBeans2.get(0).getQuantity() + "");
            syrw2.setText("剩余任务:" + dataBeans2.get(1).getQuantity() + "");
        }
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    private void getRecommended() {
        /**c
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
                String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/getIsCommTasklist.action?start=1&limit=2";
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
                            Log.i("result", "resultCode:" + result);
                            recommendedEntity = gson.fromJson(result, RecommendedEntity.class);
                            Type listType2 = new TypeToken<ArrayList<RecommendedEntity.DataBean>>() {
                            }.getType();//TypeToken内的泛型就是Json数据中的类型
                            dataBeans2 = gson.fromJson(gson.toJson(recommendedEntity.getData()), listType2);
                            code = String.valueOf(recommendedEntity.getCode());
                            Log.i("666", "eeee" + dataBeans2);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initDatas();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("eeee", "eeee" + e);
                        }

                    }
                });

    }







    private void getRecommendedList(String url) {
        /**c
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */

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
                            Log.i("result", "resultCode:" + result);
                            recommendedListEntity = gson.fromJson(result, RecommendedListEntity.class);
                            Type listType2 = new TypeToken<ArrayList<RecommendedListEntity.DataBean>>() {
                            }.getType();//TypeToken内的泛型就是Json数据中的类型
                            dataBeansList2 = gson.fromJson(gson.toJson(recommendedListEntity.getData()), listType2);
                            code = String.valueOf(recommendedListEntity.getCode());
                            Log.i("dataBeansList2", "dataBeansList2" + dataBeansList2);
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     initListData();
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("onDestroyView" , "-->onDestroyView");
         super .onDestroyView();
         if (null != v) {
                     ((ViewGroup) v.getParent()).removeView(v);
                 }
    }



    TimerTask task=new TimerTask() {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(change){
                        change=false;
                        tv_advertising.setTextColor(Color.WHITE);
                    }else {
                        change = true;
                        tv_advertising.setTextColor(Color.BLUE);
                    }
                }
            });
        }
    };



    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
