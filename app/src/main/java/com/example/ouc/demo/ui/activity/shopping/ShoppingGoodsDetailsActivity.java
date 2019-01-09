package com.example.ouc.demo.ui.activity.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.ui.activity.ShoppingCartActivity;
import com.example.ouc.demo.ui.fragment.FragmentShop;
import com.example.ouc.demo.weigets.ImageCycleView;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class ShoppingGoodsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView dianpu,shoucang,gouwuche,lijigoumai;
    private ImageView imageview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinggoodsdetails);
        initViewsBanner();
        initViews();
    }

    private void initViews() {
        TextView textView=findViewById(R.id.test);
        imageview =findViewById(R.id.btn_back_search);
        dianpu = findViewById(R.id.dianpu);
        shoucang = findViewById(R.id.shoucang);
        gouwuche = findViewById(R.id.gouwuche);
        lijigoumai = findViewById(R.id.lijigoumai);
        dianpu.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        gouwuche.setOnClickListener(this);
        lijigoumai.setOnClickListener(this);
        imageview.setOnClickListener(this);
    }

    private void initViewsBanner() {
        ImageCycleView banner_x = (ImageCycleView)findViewById(R.id.banner_x);
        ArrayList<Integer> resArray = new ArrayList<>();
        resArray.add(R.mipmap.ic_one);
        resArray.add(R.mipmap.ic_two);
        resArray.add(R.mipmap.ic_three);
        resArray.add(R.mipmap.ic_four);
        resArray.add(R.mipmap.ic_five);
        banner_x.setImageResources(resArray);
        banner_x.startImageCycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dianpu:
                break;
            case R.id.shoucang:
                break;
            case R.id.gouwuche:
                Intent intentCart=new Intent(ShoppingGoodsDetailsActivity.this,ShoppingCartActivity.class);
                startActivity(intentCart);
                break;
            case R.id.lijigoumai:
                break;
            case R.id.btn_back_search:
                ShoppingGoodsDetailsActivity.this.finish();
                break;
        }
    }
}
