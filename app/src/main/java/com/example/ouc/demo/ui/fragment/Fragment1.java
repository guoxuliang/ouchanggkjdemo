package com.example.ouc.demo.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.MyAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.BannerEntity;
import com.example.ouc.demo.entity.DeviceEntity;
import com.example.ouc.demo.entity.GetTaskEntity;
import com.example.ouc.demo.entity.RecommendedEntity;
import com.example.ouc.demo.entity.RecommendedListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.receiver.MyReceiver;
import com.example.ouc.demo.ui.MainActivity;
import com.example.ouc.demo.ui.activity.AdvertisingVideoActivity;
import com.example.ouc.demo.ui.activity.DeliveryActivity;
import com.example.ouc.demo.ui.activity.LoginActivity;
import com.example.ouc.demo.ui.activity.WebViewActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.BounceScrollView;
import com.example.ouc.demo.weigets.MyOnScrollListener;
import com.example.ouc.demo.weigets.RefreshListView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment1 extends BaseFragment implements MyOnScrollListener.OnloadDataListener {
    MainActivity activity = (MainActivity) getActivity();
    /**
     * 获取验证码
     */
    // 下载存储的文件名
    private static final String DOWNLOAD_NAME = "channelWe";
    private static final int REQUEST_CODE_PERMISSION_SD = 101;
    private static final int REQUEST_CODE_SETTING = 300;
    View v;
    private Banner banner;
    private ArrayList<String> list_path = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
    private String updateUrl, updateInfo, lastForce, msg;
    private RecommendedEntity recommendedEntity;
    private RecommendedListEntity recommendedListEntity;
    private DeviceEntity deviceEntity;
    private GetTaskEntity getTaskEntity;
    private String code;
    private ImageView iv_bigimg;
    private ProgersssDialog progersssDialog;
    private boolean change = false;
    private TextView title_rwtj, title_error;
    private int taskid;

    /**
     * 版本更新
     */
    private Gson gson = new Gson();
    private Gson gson2 = new Gson();
    private ImageView vi_tj, vi_tj2;
    private TextView rw_name, rw_name2, yd_count, yd_count2, jlj, jlj2, syrw, syrw2;

    private ArrayList<RecommendedEntity.DataBean> dataBeans2;
    private ArrayList<RecommendedListEntity.DataBean> dataBeansList2;
    private LinearLayout adv1, adv2;
    //ListView展示的数据项
    private List<RecommendedListEntity.DataBean> data;
    //ListView控件
    private RefreshListView mList;
    //自定义适配器
    MyAdapter adapter;
    //底部加载更多布局
    View footer;
    private Button btn;
    private TextView tv_back, tv_content, tv_advertising;
    private BounceScrollView scrollview;
    private String id;
    private String contents;
    int start = 0;
    int limit = 1000000;
    String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/getIsCommTasklist.action?";
    private String is_login;

    /**
     * banner轮播图
     * @param context
     */
    private BannerEntity bannerEntity;
    private  List<BannerEntity.DataBean> bannerData;
    private String  bannerurl;

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
        if (v == null) {
            v = inflater.inflate(R.layout.fragment1, container, false);
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        is_login = getStringSharePreferences("is_login", "is_login");
        progersssDialog = new ProgersssDialog(getActivity());
        id = getStringSharePreferences("id", "id");
        Log.i("id111", "id111" + id);
        postbanner();//获取banner
        initView();

    }

    @Override
    public void onStart() {
        super.onStart();
        String equipmentID2=Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        if(!id.equals("")){
            postequipmentID(equipmentID2);
        }else {
            showCustomizeDialog();
        }
        initRefreshData();
    }

    @Override
    public void onStop() {
        super.onStop();
        String equipmentID2=Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        if(!id.equals("")){
            postequipmentID(equipmentID2);
        }else {
            showCustomizeDialog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        is_login = getStringSharePreferences("is_login", "is_login");
        initRefreshData();

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
//                title_rwtj.setVisibility(View.VISIBLE);
            }
        } else {
            ToastHelper.show(getActivity(), "暂无数据");
            title_rwtj.setVisibility(View.GONE);
            mList.setVisibility(View.GONE);
//            title_error.setVisibility(View.VISIBLE);
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
            adapter = new MyAdapter(data, getActivity());
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
//        title_error = v.findViewById(R.id.title_error);
        title_rwtj = v.findViewById(R.id.title_rwtj);
        scrollview = v.findViewById(R.id.scrollview);
//        title_error.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////TODO  跳转到fragment
//            }
//        });
        scrollview.setOnReboundEndListener(new BounceScrollView.OnReboundEndListener() {
            @Override
            public void onReboundTopComplete() {
//                Toast.makeText(getActivity(), "正在刷新数据", Toast.LENGTH_SHORT).show();
                initRefreshData();
            }

            @Override
            public void onReboundBottomComplete() {
//                Toast.makeText(getActivity(), "正在加载数据", Toast.LENGTH_SHORT).show();
                initRefreshData();
            }
        });
        tv_advertising = v.findViewById(R.id.tv_advertising);
        tv_advertising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转到广告投放
                if(!id.equals("")){
//                    Intent intent = new Intent(getActivity(), DeliveryActivity.class);
//                    startActivity(intent);
                    openActivity(DeliveryActivity.class);
                }else {
                    showCustomizeDialog();
                }

            }
        });
        Timer timer = new Timer();
        timer.schedule(task, 1, 500);  //参数分别是delay（多长时间后执行），duration（执行间隔）
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
        mList = (RefreshListView) v.findViewById(R.id.mList);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ToastHelper.show(getActivity(),"点击了"+i+"项");
                //TODO  点击列表跳转到视频播放页面
                 if(!id.equals("")){


                    try {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("id", id);//用户id
                        mBundle.putString("name", dataBeansList2.get(i).getTitle());//名称
                        mBundle.putString("gold", dataBeansList2.get(i).getGold() + "");//奖励金
                        mBundle.putString("videourl", dataBeansList2.get(i).getVideo());//视频地址
//                mBundle.putString("content", dataBeansList2.get(i).getContent());//视频信息
                        mBundle.putString("shareUrl", dataBeansList2.get(i).getShareUrl());//要分享的web页面地址
                        mBundle.putString("taskid", dataBeansList2.get(i).getId() + "");//获取任务ID
//                    mBundle.putString("cover", dataBeans2.get(i).getCover());//获取封面
                        setStringSharedPreferences("content", "content", "");
                        contents = dataBeansList2.get(i).getContent();//获取内容
                        if (contents != null) {
                            setStringSharedPreferences("content", "content", contents);
                        } else {
                            ToastHelper.show(getActivity(), "内容为空");
                        }

                        taskid = dataBeansList2.get(i).getId();
                        Gettask(id, taskid);//调用领取任务接口
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    } catch (Exception e) {
                        ToastHelper.show(getActivity(), "error:" + e);
                    }
                } else {
                    showCustomizeDialog();
                }
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
//        //放图片地址的集合
//        list_path = new ArrayList<>();
//        //放标题的集合
//        list_title = new ArrayList<>();
//        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/1.jpg");
//        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/2.jpg");
//        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/3.jpg");
//        list_path.add("http://kgj.ockeji.com/upload/kgj/banner/4.jpg");
//        list_title.add("掌上营销");
//        list_title.add("畅联达广告机");
//        list_title.add("广告新模式");
//        list_title.add("广告推广");

        //设置内置样式，共有六种可以点入方法内逐一体验使用。

        adv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//TODO  点击广告跳转到视频详情页面
                if(!id.equals("")){
                try {
                    if (dataBeans2 != null) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("id", id);//用户id
                        mBundle.putString("name", dataBeans2.get(0).getTitle());//名称
                        mBundle.putString("gold", dataBeans2.get(0).getGold() + "");//奖励金
                        mBundle.putString("videourl", dataBeans2.get(0).getVideo());//视频地址
//                mBundle.putString("content", dataBeans2.get(0).getContent());//视频信息
                        mBundle.putString("shareUrl", dataBeans2.get(0).getShareUrl());//要分享的web页面地址
                        mBundle.putString("taskid", dataBeans2.get(0).getId() + "");//获取任务ID
//                    mBundle.putString("cover", dataBeans2.get(0).getCover());//获取封面

                        taskid = dataBeans2.get(0).getId();
                        setStringSharedPreferences("content", "content", "");
                        contents = dataBeans2.get(0).getContent();//获取内容

                        if (contents != null) {
                            setStringSharedPreferences("content", "content", contents);
                        } else {
                            ToastHelper.show(getActivity(), "内容为空");
                        }
                        Gettask(id, taskid);//调用领取任务接口
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    } else {
                        ToastHelper.show(getActivity(), "数据为空");
                    }
                } catch (Exception e) {
                    ToastHelper.show(getActivity(), "error:" + e);
                }
                }else {
                    showCustomizeDialog();
                }
            }
        });
        adv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//TODO  点击广告跳转到视频详情页面
                if(!id.equals("")){
                try {
                    if (dataBeans2 != null) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                        Bundle mBundle = new Bundle();
//                    mBundle.putString("id", id);//用户id
                        mBundle.putString("name", dataBeans2.get(1).getTitle());//名称
                        mBundle.putString("gold", dataBeans2.get(1).getGold() + "");//奖励金
                        mBundle.putString("videourl", dataBeans2.get(1).getVideo());//视频地址
//                    mBundle.putString("content", dataBeans2.get(1).getContent());//视频信息
                        mBundle.putString("shareUrl", dataBeans2.get(1).getShareUrl());//要分享的web页面地址
                        mBundle.putString("taskid", dataBeans2.get(1).getId() + "");//获取任务ID
//                    mBundle.putString("cover", dataBeans2.get(1).getCover());//获取封面
                        taskid = dataBeans2.get(1).getId();
                        setStringSharedPreferences("content", "content", "");
                        contents = dataBeans2.get(1).getContent();//获取内容
                        if (contents != null) {
                            setStringSharedPreferences("content", "content", contents);
                        } else {
                            ToastHelper.show(getActivity(), "内容为空");
                        }
                        Gettask(id, taskid);//调用领取任务接口
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    } else {
                        ToastHelper.show(getActivity(), "数据为空");
                    }
                } catch (Exception e) {
                    ToastHelper.show(getActivity(), "error:" + e);
                }
            }else {
                showCustomizeDialog();
            }
            }
        });
        mList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

    }

    private void initRefreshData() {
        if (isConnNet(getActivity()) == true) {
            getRecommended();
            String dhs = "start=" + start + "&limit=" + limit + "&userid=" + id;
            getRecommendedList(url + dhs);
        } else {
            ToastHelper.show(getActivity(), "请检查网络");
        }
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

    /**
     * 接口名：getRecommended
     * Get请求  推荐任务列表请求接口
     */
    private void getRecommended() {
        String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/getTopTask.action?userid=" + id;
        Log.i("url", "url9999999999999:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:getRecommended：：：" + result);
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

    /**
     * 接口名：getRecommendedList
     * Get请求   任务列表请求接口
     */
    private void getRecommendedList(String url) {


        Log.i("url", "url00000000000000000000000000000000:" + url);
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
                    Log.i("result", "resultCode222222222:" + result);
                    recommendedListEntity = gson.fromJson(result, RecommendedListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<RecommendedListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    dataBeansList2 = gson.fromJson(gson.toJson(recommendedListEntity.getData()), listType2);
                    code = String.valueOf(recommendedListEntity.getCode());
                    Log.i("===dataBeansList2", "dataBeansList2" + dataBeansList2+"====dataBeansList2.size()"+dataBeansList2.size());
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

    /**
     * Post请求  领取任务接口
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void Gettask(String id, int taskid) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("taskid", String.valueOf(taskid));

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemUserTaskController/receivetask.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    getTaskEntity = gson.fromJson(result, GetTaskEntity.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (getTaskEntity.getCode() == 200) {
//                                ToastHelper.show(getActivity(), getTaskEntity.getMsg());
                            } else {
//                                ToastHelper.show(getActivity(), getTaskEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("onDestroyView", "-->onDestroyView");
        super.onDestroyView();
        if (null != v) {
            ((ViewGroup) v.getParent()).removeView(v);
        }
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (getActivity()==null){
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (change) {
                        change = false;
                        tv_advertising.setTextColor(Color.WHITE);
                    } else {
                        change = true;
                        tv_advertising.setTextColor(Color.BLUE);
                    }
                    System.gc();
                }
            });

        }
    };




    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void postbanner() {
        Map<String, String> map = new HashMap<>();
        map.put("start", "0");
        map.put("limit", "5");

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/bannerController/HomeBannerList.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    bannerEntity = gson2.fromJson(result, BannerEntity.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bannerEntity.getCode() == 200) {
//                                ToastHelper.show(getActivity(), bannerEntity.getMsg());
                                bannerData = bannerEntity.getData();
                                Log.i("bannerData=====","bannerData====="+bannerData.size()+"======="+bannerData);
                                for (int i=0;i<bannerData.size();i++){
                                    list_path.add(bannerData.get(i).getHeadimg());
                                    list_title.add(bannerData.get(i).getBannername());
                                }
                                Log.i("list_path","list_path"+list_path);
                                Log.i("list_title","list_title"+list_title);
                                if(list_path!=null&&list_title!=null){
                                    bannerload();
                                }
                                Log.i("bannerData","bannerData"+bannerData);
                            }else{
//                                ToastHelper.show(getActivity(), bannerEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }




    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void postequipmentID(final String equipmentID) {
        Map<String, String> map = new HashMap<>();
        map.put("equipmentID", equipmentID);
        map.put("id", id);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/getEquiID.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    deviceEntity = gson2.fromJson(result, DeviceEntity.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (deviceEntity.getCode() == 200) {
//                                ToastHelper.show(getActivity(), deviceEntity.getMsg());
                                String device = deviceEntity.getData().getEquipmentID();
                                String  is_login = deviceEntity.getData().getIs_login();

//                                if(!equipmentID.equals("device")){
//                                    gbReceiver();
//                                }

                            }else{
//                                ToastHelper.show(getActivity(), deviceEntity.getMsg());
                                boolean execute = deviceEntity.isExecute();
                                if(execute==false){
                                    gbReceiver();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }







   private void bannerload(){
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
                       bannerurl = bannerData.get(position).getUrl();
                       if(!id.equals("")){
                           Bundle bundle2=new Bundle();
                           if(!bannerurl.equals("")){
                               bundle2.putString("bannerurl",bannerurl);
                               openActivity(WebViewActivity.class,bundle2);
                           }else {
                               ToastHelper.show(getActivity(),"此内容不存在");
                           }

                       }else {
                           showCustomizeDialog();
                       }

                   }
               }).start();
    }


    private void gbReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(getActivity())) {
                Intent intent = new Intent(new Intent(getActivity(), MyReceiver.class));
                intent.setAction("com.feiben.rememberpasswordtest.FORCE_OFFLINE");
                getActivity().sendBroadcast(intent);
            } else {
                //若没有权限，提示获取.
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(getActivity(), "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(new Intent(getActivity(), MyReceiver.class));
            intent.setAction("com.feiben.rememberpasswordtest.FORCE_OFFLINE");
            getActivity().sendBroadcast(intent);
        }
    }

    private void showCustomizeDialog(){
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_customize,null);
        customizeDialog.setTitle("温馨提示");
        customizeDialog.setView(dialogView);
        TextView edit_text =(TextView)dialogView.findViewById(R.id.edit_text);
        edit_text.setText("请先登录您的账号");
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //获取EditView中的输入内容
                        openActivity(LoginActivity.class);

                    }
                });
        customizeDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        customizeDialog.setCancelable(false);
        customizeDialog.show();
    }
}
