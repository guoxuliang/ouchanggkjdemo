package com.example.ouc.demo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.ItemAdapter;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.GetTaskEntity;
import com.example.ouc.demo.entity.RecommendedListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.AdvertisingVideoActivity;
import com.example.ouc.demo.ui.activity.LoginActivity;
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
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment2 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    View view;
    private android.widget.TextView tv_back, tv_content;
    private int taskid;
    private String contents;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private GetTaskEntity getTaskEntity;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 4;
    private GridLayoutManager mLayoutManager;
    private ItemAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private String is_login;
    private Gson gson = new Gson();
    private ArrayList<RecommendedListEntity.DataBean> dataBeansList2;
    private RecommendedListEntity recommendedListEntity;
    private ProgersssDialog progersssDialog;
    String id;
    int start=0;
    int limit=1000000;
//    String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/getIsCommTasklist.action?";
    String url = Constants.SERVER_BASE_URL + "system/sys/SysMemTaskController/tasklist.action?";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // TODO Auto-generated method stub
         view=inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        is_login = getStringSharePreferences("is_login", "is_login");
        progersssDialog = new ProgersssDialog(getActivity());
        id=getStringSharePreferences("id","id");
        findView();
        initTitle();
        if(isConnNet(getActivity())==true){
            String dhs="start="+start+"&"+"limit="+limit+"&"+"userid="+id;
            getRecommendedList(url+dhs);
        }else {
            ToastHelper.show(getActivity(),"请检查网络");
        }
    }

        private void initTitle() {
        tv_back = view.findViewById(R.id.tv_left);
        tv_back.setVisibility(View.GONE);
        tv_content = view.findViewById(R.id.tv_title);
        tv_content.setText("广告增值平台");
    }

    private void findView() {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new ItemAdapter(getDatas(0, PAGE_COUNT), getActivity(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  recyclerView的点击事件
//                Log.d("", "onItemClick : postion " + position);
//                Toast.makeText(getActivity(),"position:"+position,Toast.LENGTH_LONG).show();
                if(is_login.equals("1")){
                try {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AdvertisingVideoActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("id", id);//用户id
                mBundle.putString("name", dataBeansList2.get(position).getTitle());//名称
                mBundle.putString("gold", dataBeansList2.get(position).getGold() + "");//奖励金
                mBundle.putString("videourl", dataBeansList2.get(position).getVideo());//视频地址
//                mBundle.putString("timelong", dataBeansList2.get(position).getTimelong());//视频地址
//                mBundle.putString("content", dataBeansList2.get(position).getContent());//视频信息
                mBundle.putString("shareUrl", dataBeansList2.get(position).getShareUrl());//要分享的web页面地址
                mBundle.putString("taskid", dataBeansList2.get(position).getId()+"");//获取任务ID
                contents =  dataBeansList2.get(position).getContent();//获取内容
                Log.i("contents","contents"+contents);
                setStringSharedPreferences("content","content",contents);
                taskid = dataBeansList2.get(position).getId();
                Gettask(id,taskid);//调用领取任务接口
                intent.putExtras(mBundle);
                startActivity(intent);
                }catch (Exception e){
                    ToastHelper.show(getActivity(),"error:"+e);
                }
                }else {
                    openActivity(LoginActivity.class);
                }
            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO  recyclerView的长按事件
                Log.d("", "onLongClick position : " + posotion);
                Toast.makeText(getActivity(),"onLongClick position : " + posotion,Toast.LENGTH_LONG).show();
            }
        }));

    }

    private ArrayList<RecommendedListEntity.DataBean> getDatas(final int firstIndex, final int lastIndex) {
        ArrayList<RecommendedListEntity.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < dataBeansList2.size()) {
                resList.add(dataBeansList2.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        ArrayList<RecommendedListEntity.DataBean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        is_login = getStringSharePreferences("is_login", "is_login");
    }

    /**
     * 接口名：getRecommendedList
     * Get请求   任务列表请求接口
     */
    private void getRecommendedList(String url) {


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
                    recommendedListEntity = gson.fromJson(result, RecommendedListEntity.class);
                    Type listType2 = new TypeToken<ArrayList<RecommendedListEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    dataBeansList2 = gson.fromJson(gson.toJson(recommendedListEntity.getData()), listType2);

                    Log.i("dataBeansList2", "dataBeansList2" + dataBeansList2);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          String  code = String.valueOf(recommendedListEntity.getCode());
                            ToastHelper.show(getActivity(),recommendedListEntity.getMsg());
                          if(code.equals("200")){
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

    /**
     * Post请求  领取任务接口
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void Gettask(String id,int taskid){
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("taskid", String.valueOf(taskid));

        HttpUtils.doPost(Constants.SERVER_BASE_URL+"system/sys/SysMemUserTaskController/receivetask.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result=response.body().string();
                    Log.i("result", "result:" + result);
                    getTaskEntity=gson.fromJson(result,GetTaskEntity.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (getTaskEntity.getCode()==200){
                                ToastHelper.show(getActivity(),getTaskEntity.getMsg());
                            }else {
                                ToastHelper.show(getActivity(),getTaskEntity.getMsg());
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
