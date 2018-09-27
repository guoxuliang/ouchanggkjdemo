package com.example.ouc.demo.ui.activity.vip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.FragmentAdapter;
import com.example.ouc.demo.ui.activity.AdvertisingVideoActivity;
import com.example.ouc.demo.ui.fragment.Fragment4;
import com.example.ouc.demo.ui.fragment.Fragment5;
import com.example.ouc.demo.ui.fragment.Fragment6;
import com.example.ouc.demo.utils.ProgersssDialog;

import java.util.ArrayList;
public class MyOrderActivity extends FragmentActivity {
    private ImageView iv_right;
    private TextView tv_back, tv_content;
    private ArrayList<Fragment> mFragmentList;
    private ViewPager mPageVp;
    private Fragment4 fragment4;
    private Fragment5 fragment5;
    private Fragment6 fragment6;
    private RadioGroup mGroup_page;
    private RadioButton rbChat_page,rbContacts_page,rbDiscovery_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        initView();
        initViewPager();
        initTitle();

    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right= findViewById(R.id.iv_right);
        iv_right.setVisibility(View.GONE);
//        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
//        iv_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 点击分享
//            }
//        });
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrderActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("我的订单");
    }
    private void initViewPager(){
        fragment4=new Fragment4();
        fragment5=new Fragment5();
        fragment6=new Fragment6();
        mFragmentList=new ArrayList<Fragment>();
        mFragmentList.add(0,fragment4);
        mFragmentList.add(1,fragment5);
        mFragmentList.add(2,fragment6);
        //ViewPager设置适配器
        mPageVp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mFragmentList));
        //ViewPager显示第一个Fragment
        mPageVp.setCurrentItem(0);
        //ViewPager页面切换监听
        mPageVp.setOnPageChangeListener(new myOrderOnPageChangeListener());
    }
    private void initView(){
        mPageVp=(ViewPager)findViewById(R.id.id_page_vp);
        mGroup_page=(RadioGroup)findViewById(R.id.radiogroup_page);
        rbChat_page=(RadioButton)findViewById(R.id.rb_chat_page);
        rbChat_page.setText("未完成");
        rbContacts_page=(RadioButton)findViewById(R.id.rb_contacts_page);
        rbContacts_page.setText("已完成");
        rbDiscovery_page=(RadioButton)findViewById(R.id.rb_discovery_page);
        rbDiscovery_page.setText("已取消");
        //RadioGroup选中状态改变监听
        mGroup_page.setOnCheckedChangeListener(new myOrderCheckChangeListener());
        mPageVp .setOffscreenPageLimit(2);
    }



    /**
     *RadioButton切换Fragment
     */
    private class myOrderCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_chat_page:
                    //ViewPager显示第一个Fragment且关闭页面切换动画效果
                    mPageVp.setCurrentItem(0);
                    rbChat_page.setChecked(true);
                    rbContacts_page.setChecked(false);
                    rbDiscovery_page.setChecked(false);

                    break;
                case R.id.rb_contacts_page:
                    mPageVp.setCurrentItem(1);
                    rbChat_page.setChecked(false);
                    rbContacts_page.setChecked(true);
                    rbDiscovery_page.setChecked(false);

                    break;
                case R.id.rb_discovery_page:
                    mPageVp.setCurrentItem(2);
                    rbChat_page.setChecked(false);
                    rbContacts_page.setChecked(false);
                    rbDiscovery_page.setChecked(true);
                    break;

            }
        }
    }

    /**
     *ViewPager切换Fragment,RadioGroup做相应变化
     */
    private class myOrderOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    mGroup_page.check(R.id.rb_chat_page);
                    break;
                case 1:
                    mGroup_page.check(R.id.rb_contacts_page);
                    break;
                case 2:
                    mGroup_page.check(R.id.rb_discovery_page);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}


