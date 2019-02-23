package com.example.ouc.demo.ui.activity.shopping;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.ConfirmGoodsEntity;
import com.example.ouc.demo.entity.EvaluationEntity;
import com.example.ouc.demo.entity.OrderListEntity;
import com.example.ouc.demo.entity.RealNameEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.vip.RealNameActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private ImageView iv_left;
    private ImageView bannerone;
    private TextView shrname,shrphone,shraddress,shopname,shopguige,shopjiage,orderjiage,fkjine,orderNo,createTime,fkTime;
    private TextView tv_lxmj,tv_qrsh;//联系卖家和确认收货。
    private EditText et_getpl;
    private Button btn_tjpl;
    private String orderno,shopid,username;
    private ConfirmGoodsEntity confirmGoodsEntity;
    private EvaluationEntity evaluationEntity;
    private Gson gson = new Gson();
    float rating;
    private RatingBar mRatingBar;
    String plContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        initTitle();
        initViews();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            shrphone.setText(bundle.getString("phone"));
            createTime.setText(bundle.getString("createdate"));
            username = bundle.getString("username");
             orderno = bundle.getString("orderno");
            orderNo.setText(bundle.getString("orderno"));
            shopname.setText(bundle.getString("shopname"));
            shopguige.setText(bundle.getString("info"));
            shrname.setText(bundle.getString("delivename"));
            String bannerurl = bundle.getString("bannerone");
            Glide.with(this).load(bannerurl).into(bannerone);
            fkTime.setText(bundle.getString("updatedate"));
            shraddress.setText(bundle.getString("address"));
            shopjiage.setText(bundle.getString("money"));
            orderjiage.setText(bundle.getString("money"));
            fkjine.setText(bundle.getString("money"));
            shopid = bundle.getString("shopid");//商品ID
        }
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
        tv_title.setText("订单详情");
    }

    private void initViews() {
        shrname =(TextView) findviewByid(R.id.shrname);
        shrphone = (TextView)findviewByid(R.id.shrphone);
        shraddress =(TextView) findviewByid(R.id.shraddress);
        shopguige = (TextView)findviewByid(R.id.shopguige);
        shopname = (TextView)findviewByid(R.id.shopname);
        shopjiage =(TextView) findviewByid(R.id.shopjiage);
        orderjiage =(TextView) findviewByid(R.id.orderjiage);
        fkjine = (TextView)findviewByid(R.id.fkjine);
        orderNo = (TextView)findviewByid(R.id.orderNo);
        createTime = (TextView)findviewByid(R.id.createTime);
        fkTime = (TextView)findviewByid(R.id.fkTime);
        bannerone =(ImageView) findviewByid(R.id.bannerone);
        tv_lxmj = (TextView)findviewByid(R.id.tv_lxmj);
        tv_qrsh = (TextView)findviewByid(R.id.tv_qrsh);
        et_getpl = (EditText)findviewByid(R.id.et_getpl);
        btn_tjpl = (Button)findviewByid(R.id.btn_tjpl);
        tv_lxmj.setOnClickListener(this);
        tv_qrsh.setOnClickListener(this);
        btn_tjpl.setOnClickListener(this);


         mRatingBar = (RatingBar) findViewById(R.id.ratingbar);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(OrderDetailsActivity.this, "评分星级=" + rating, Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_lxmj:
                //TODO 联系卖家
                ToastHelper.show(this,"敬请期待");
                break;
            case R.id.tv_qrsh:
                //TODO 确认收货
showOrderDialog(orderno);
                break;
            case R.id.btn_tjpl:
                //TODO 提交评论
                 plContent = et_getpl.getText().toString().trim();//获取评论内容
                 rating= mRatingBar.getRating();//获取星级

                if(!plContent.equals("")){
                    AddEvaluation();
                }else {
                    ToastHelper.show(this,"评论不能为空");
                }

                break;
        }
    }


    private void showOrderDialog(final String orderno){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(OrderDetailsActivity.this);
        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("");
        normalDialog.setMessage("请确认是否收到宝贝？");
        normalDialog.setPositiveButton("收到",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        ConfirmGoods(orderno);
                    }
                });
        normalDialog.setNegativeButton("没有",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    /**
     * 接口名：ConfirmGoods
     * Get请求   确认收货
     */
    private void ConfirmGoods(String orderno) {
        Map<String, String> map = new HashMap<>();
        map.put("orderno",orderno);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/bas/order/confirmDelivery.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    confirmGoodsEntity = gson.fromJson(result, ConfirmGoodsEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (confirmGoodsEntity.getCode() == 200) {
                                ToastHelper.show(OrderDetailsActivity.this,confirmGoodsEntity.getMsg());
                            }else{
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
     * 接口名：ConfirmGoods
     * Get请求   增加一条评论
     */
//    private void AddEvaluation() {
//        Map<String, String> map = new HashMap<>();
//        map.put("goodsid",id);//商品id
//        map.put("orderno",orderno);
//        map.put("userid",getStringSharePreferences("id","id"));
//        map.put("username",username);
//        map.put("point", String.valueOf(rating));
//        map.put("commentinfo",plContent);
//
//        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/shopCommon/addOrderComment.action", map, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("dfsd", "dsfsd" + e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    String result = response.body().string();
//                    Log.i("result", "result:" + result);
//                    evaluationEntity = gson.fromJson(result, EvaluationEntity.class);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (evaluationEntity.getCode() == 200) {
//                                ToastHelper.show(OrderDetailsActivity.this,evaluationEntity.getMsg());
//                                finish();
//                            }else{
//                            }
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

        private void AddEvaluation() {
        new Thread() {
            @Override
            public void run() {
                MediaType MEDIA_TYPE = MediaType.parse("image/*");
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("goodsid", shopid)
                        .addFormDataPart("orderno", orderno)
                        .addFormDataPart("userid", getStringSharePreferences("id","id"))
                        .addFormDataPart("username", username)
                        .addFormDataPart("point", String.valueOf(rating))
                        .addFormDataPart("commentinfo",plContent )
                        .addFormDataPart("imgoneFile", "")
                        .addFormDataPart("imgtwoFile", "")
                        .addFormDataPart("imgthreeFile", "");
                MultipartBody requestBody = builder.build();
                Log.i("requestBody", "requestBody" + requestBody);
                //构建请求
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Constants.SERVER_BASE_URL + "app/shopCommon/addOrderComment.action")
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("onResponse", "onFailure: 访问失败!" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String result = response.body().string();
                            Log.d("result", "result:" + result);
                            evaluationEntity = gson.fromJson(result, EvaluationEntity.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int code = evaluationEntity.getCode();
                                    if (code == 200) {
                                        ToastHelper.show(OrderDetailsActivity.this, evaluationEntity.getMsg());
                                        OrderDetailsActivity.this.finish();
                                    } else {
                                        ToastHelper.show(OrderDetailsActivity.this, evaluationEntity.getMsg());
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }

}
