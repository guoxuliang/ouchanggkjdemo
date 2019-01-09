package com.example.ouc.demo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.ui.activity.WebViewActivity;
import com.example.ouc.demo.ui.activity.shopping.SearchActivity;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.ImageCycleView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class FragmentShop extends BaseFragment {
    View v;
    private android.support.v7.widget.Toolbar toolbar;
    private com.youth.banner.Banner banner2;
    private ArrayList<String> list_path = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();

    private RecyclerView recyclerView;
    private EditText et_nono_left;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmentshop, null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle();
        initViews();
    }
    private void initTitle() {
    }

    private void initViews() {
        ImageCycleView banner = (ImageCycleView)v.findViewById(R.id.banner);
        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ArrayList<Integer> resArray = new ArrayList<>();
        resArray.add(R.mipmap.ic_one);
        resArray.add(R.mipmap.ic_two);
        resArray.add(R.mipmap.ic_three);
        resArray.add(R.mipmap.ic_four);
        resArray.add(R.mipmap.ic_five);
        banner.setImageResources(resArray);
        banner.startImageCycle();


        banner2 = v.findViewById(R.id.banner2);
        et_nono_left = v.findViewById(R.id.et_nono_left);
        et_nono_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(SearchActivity.class);
            }
        });
        recyclerView = v.findViewById(R.id.recyclerview);
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
        bannerload();



    }

    private void bannerload(){
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
                        Toast.makeText(getActivity(),"点击了"+position+"项",Toast.LENGTH_SHORT).show();
                    }
                }).start();
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
