package com.example.ouc.demo.ui.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.ShoppingCollectionEntity;
import com.example.ouc.demo.entity.ShoppingGoodsDetailEntity;
import com.example.ouc.demo.entity.ShoppingqueryListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.ShoppingCartActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ProgersssDialog;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.BottomWebView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShoppingGoodsDetailsActivity extends BaseActivity implements View.OnClickListener {

    private TextView dianpu, shoucang, gouwuche, lijigoumai, goodsprice, tv_goodsname, tv_allpl;
    private ImageView imageview, btn_search, addresslist;
    private Banner banner_x;
    private CheckBox checkbox;
    private BottomWebView shopwebview;
    private boolean isCheack = false;
    private String price, titlename, productid, shopid, name2, price2, goodsinfo2;
    private String contentAll;
    private ArrayList<String> titleimg_path = new ArrayList<>();
    private ArrayList<String> titleimg_path2 = new ArrayList<>();
    private ArrayList<ShoppingGoodsDetailEntity.DataBean> detailShopList;
    private ProgersssDialog progersssDialog;
    private ShoppingGoodsDetailEntity shoppingGoodsDetailEntity;
    private ShoppingCollectionEntity shoppingCollectionEntity;//用户收藏实体
    private Gson gson = new Gson();
    private String url = Constants.SERVER_BASE_URL + "app/goodsInfo/getGoodsInfoById.action?";
    private String url2 = Constants.SERVER_BASE_URL + "app/shopCommon/getClickCollection.action?";
    private String url3 = Constants.SERVER_BASE_URL + "app/shopCommon/delete.action?";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinggoodsdetails);
        initViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleimg_path.add(bundle.getString("bannerone"));
            titleimg_path.add(bundle.getString("bannertwo"));
            titleimg_path.add(bundle.getString("bannerthree"));
            price = bundle.getString("price");
            titlename = bundle.getString("name");
            productid = bundle.getString("id");
            Log.i("productid", "productid" + productid);
            bundle.getString("number");
            contentAll = bundle.getString("goodsinfo");

            goodsprice.setText("￥" + price);
            tv_goodsname.setText(titlename);
            initViewsBanner(titleimg_path);
            dataWebView(contentAll);
        }
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            String userid = getStringSharePreferences("id", "id");
            shopid = bundle.getString("id");
            progersssDialog = new ProgersssDialog(this);
            getShoppingDetail(url + "shopid=" + shopid + "&userid=" + userid);
        }


    }

    private void dataWebView(String weburl) {
        shopwebview = findViewById(R.id.shopwebview);
        shopwebview.getSettings().setDefaultTextEncodingName("UTF-8");
        shopwebview.loadDataWithBaseURL("", weburl, "text/html", "UTF-8", "");
        shopwebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        shopwebview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        shopwebview.requestFocus();
        shopwebview.setVerticalScrollBarEnabled(true);
        shopwebview.setHorizontalScrollBarEnabled(false);
        shopwebview.getSettings().setSupportZoom(true);
        shopwebview.getSettings().setBuiltInZoomControls(true);
        shopwebview.getSettings().setJavaScriptEnabled(true);
        shopwebview.getSettings().setDomStorageEnabled(true);
        shopwebview.getSettings().setUseWideViewPort(true);
        shopwebview.getSettings().setLoadWithOverviewMode(true);
        shopwebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        shopwebview.setWebViewClient(new TestWebViewClient());
        webViewScroolChangeListener();
    }

    private void initViews() {
        tv_goodsname = findViewById(R.id.tv_goodsname);
        goodsprice = findViewById(R.id.goodsprice);
        tv_goodsname = findViewById(R.id.tv_goodsname);
        btn_search = findViewById(R.id.btn_search);
        addresslist = findViewById(R.id.addresslist);
        imageview = findViewById(R.id.btn_back_search);
        dianpu = findViewById(R.id.dianpu);
        shoucang = findViewById(R.id.shoucang);
        gouwuche = findViewById(R.id.gouwuche);
        lijigoumai = findViewById(R.id.lijigoumai);
        checkbox = findviewByid(R.id.checkbox);
        tv_allpl = findviewByid(R.id.tv_allpl);
        tv_allpl.setOnClickListener(this);
        dianpu.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        gouwuche.setOnClickListener(this);
        lijigoumai.setOnClickListener(this);
        imageview.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        addresslist.setOnClickListener(this);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String memberid = getStringSharePreferences("id", "id");
                if (b) {
                    //TODO 选中
                    Toast.makeText(ShoppingGoodsDetailsActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                    if (shopid != null) {
                        shoppingCollection(url2 + "goodsid=" + shopid + "&memberid=" + memberid);
                    } else {
                        shoppingCollection(url2 + "goodsid=" + productid + "&memberid=" + memberid);
                    }


                } else {
                    //TODO 取消

                    shoppingCollection(url3 + "goodsid=" + productid + "&memberid=" + memberid);
                    Toast.makeText(ShoppingGoodsDetailsActivity.this, "取消", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void webViewScroolChangeListener() {
        shopwebview.setOnCustomScroolChangeListener(new BottomWebView.ScrollInterface() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                //WebView的总高度
                float webViewContentHeight = shopwebview.getContentHeight() * shopwebview.getScale();
                //WebView的现高度
                float webViewCurrentHeight = (shopwebview.getHeight() + shopwebview.getScrollY());
                System.out.println("webViewContentHeight=" + webViewContentHeight);
                System.out.println("webViewCurrentHeight=" + webViewCurrentHeight);
                if ((webViewContentHeight - webViewCurrentHeight) == 0) {
                    System.out.println("WebView滑动到了底端");
                    ToastHelper.show(ShoppingGoodsDetailsActivity.this, "WebView滑动到了底端");
                }
            }
        });
    }

    private class TestWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViewsBanner(ArrayList<String> titleimg_path) {
        banner_x = findViewById(R.id.banner_x);
        //设置图片加载器，图片加载器在下方
        banner_x.setImageLoader(new MyLoader2());
        //设置图片网址或地址的集合
        banner_x.setImages(titleimg_path);
        banner_x.start();
    }

    //自定义的图片加载器
    private class MyLoader2 extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_allpl:
                //TODO  查看全部评论
                Intent intentpl = new Intent(ShoppingGoodsDetailsActivity.this, AllCommentsActivity.class);
                Bundle bundlepl = new Bundle();
                bundlepl.putString("productid", productid);
                intentpl.putExtras(bundlepl);
                startActivity(intentpl);

                break;
            case R.id.dianpu:
                //TODO  店铺
                break;
            case R.id.shoucang:
                //TODO  收藏
                break;
            case R.id.gouwuche:
                //TODO  加入购物车
                Intent intent1 = new Intent(ShoppingGoodsDetailsActivity.this, ChooseCommodityDialogActivity.class);
                Bundle bundle = new Bundle();
                if (productid != null) {
                    bundle.putString("productid", productid);
                } else {
                    bundle.putString("productid", shopid);
                }

                bundle.putString("memberid", getStringSharePreferences("id", "id"));
//                startActivityForResult(intent1,10);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.lijigoumai:
                //TODO  立即购买
                Intent intent2 = new Intent(ShoppingGoodsDetailsActivity.this, ChooseCommodityDialogActivity.class);
                Bundle bundle2 = new Bundle();
                if (productid != null) {
                    bundle2.putString("productid", productid);
                } else {
                    bundle2.putString("productid", shopid);
                }
                bundle2.putString("flag", "flag");
                bundle2.putString("memberid", getStringSharePreferences("id", "id"));
//                  startActivityForResult(intent1,10);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.btn_back_search:
                //TODO  返回上一页
                ShoppingGoodsDetailsActivity.this.finish();
                break;
            case R.id.btn_search:
                // TODO 购物车
                Intent intentCart = new Intent(ShoppingGoodsDetailsActivity.this, ShoppingCartActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("memberid", getStringSharePreferences("id", "id"));
                intentCart.putExtras(bundle1);
                startActivity(intentCart);
                break;
            case R.id.addresslist:
                // TODO 地址列表管理
                Intent intentaddresslist = new Intent(ShoppingGoodsDetailsActivity.this, AddressListActivity.class);
                startActivity(intentaddresslist);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 11 && requestCode == 10) {
            Toast.makeText(this, "返回数据", Toast.LENGTH_SHORT).show();
            String number = data.getStringExtra("number");
            String seledtedColor = data.getStringExtra("seledtedColor");
//            tvCommodityChange.setText("已选 X "+number+"     "+seledtedColor);
            Toast.makeText(this, "已选 X " + number + "     " + seledtedColor, Toast.LENGTH_SHORT).show();
        }
        if (resultCode == 14 && requestCode == 13) {
            // ToastUtil.showL(this,"返回地址");
        }
    }

    /**
     * 接口名：getRecommendedList
     * Get请求   获取商品列表请求接口
     */
    private void getShoppingDetail(String url) {


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
                    shoppingGoodsDetailEntity = gson.fromJson(result, ShoppingGoodsDetailEntity.class);
//                    Type listType2 = new TypeToken<ArrayList<ShoppingqueryListEntity.DataBean>>() {
//                    }.getType();//TypeToken内的泛型就是Json数据中的类型
//                    detailShopList = gson.fromJson(gson.toJson(shoppingGoodsDetailEntity.getData()), listType2);

//                    Log.i("queryShopList", "queryShopList" + detailShopList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingGoodsDetailEntity.getCode();
                            if (code == 200) {
                                titleimg_path2.add(shoppingGoodsDetailEntity.getData().getBannerone());
                                titleimg_path2.add(shoppingGoodsDetailEntity.getData().getBannertwo());
                                titleimg_path2.add(shoppingGoodsDetailEntity.getData().getBannerthree());
                                name2 = shoppingGoodsDetailEntity.getData().getName();
                                price2 = shoppingGoodsDetailEntity.getData().getPrice() + "";
                                goodsinfo2 = shoppingGoodsDetailEntity.getData().getGoodsinfo();
                                isCheack = shoppingGoodsDetailEntity.getData().getIsCollection();
                                initViewsBanner(titleimg_path2);
                                goodsprice.setText("￥" + price2);
                                tv_goodsname.setText(name2);
                                checkbox.setChecked(isCheack);
                                dataWebView(goodsinfo2);
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
     * Get请求   用户点击收藏接口
     */
    private void shoppingCollection(String url) {


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
                    shoppingCollectionEntity = gson.fromJson(result, ShoppingCollectionEntity.class);
//                    Type listType2 = new TypeToken<ArrayList<ShoppingqueryListEntity.DataBean>>() {
//                    }.getType();//TypeToken内的泛型就是Json数据中的类型
//                    detailShopList = gson.fromJson(gson.toJson(shoppingGoodsDetailEntity.getData()), listType2);

//                    Log.i("queryShopList", "queryShopList" + detailShopList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = shoppingCollectionEntity.getCode();
                            if (code == 200) {
                                Toast.makeText(ShoppingGoodsDetailsActivity.this, "" + shoppingCollectionEntity.getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ShoppingGoodsDetailsActivity.this, "" + shoppingCollectionEntity.getMsg(), Toast.LENGTH_SHORT).show();
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
