package com.example.ouc.demo.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CustomerServiceActivity extends BaseActivity{
    private ImageView iv_ewCode;
    private String shopinfo;
    private TextView tv_back, tv_content,tv_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerservice);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            shopinfo = bundle.getString("shopinfo");
        }
        initTitle();
        initViews();
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
//        tv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerServiceActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("联系客服");
    }

    private void initViews() {
        iv_ewCode = findviewByid(R.id.iv_ewCode);
        Glide.with(CustomerServiceActivity.this).load(shopinfo).into(iv_ewCode);
        iv_ewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isWeixinAvilible(CustomerServiceActivity.this);//打开微信
            }
        });
        iv_ewCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                new AlertDialog.Builder(CustomerServiceActivity.this)
//                        .setPositiveButton(R.string.recognize_qr_code, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                returnBitMap(shopinfo);//识别二维码
//                            }
//                        }).show();

                return true;
            }
        });
    }
    class QrCodeAsyncTask extends AsyncTask<BinaryBitmap, Void, Result> {

        @Override
        protected Result doInBackground(BinaryBitmap... params) {
            QRCodeReader reader = new QRCodeReader();
            Result result = null;
            try {
                result = reader.decode(params[0]);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (ChecksumException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
//            progressBar.setVisibility(View.GONE);

            String text = result.getText();
            Log.i("QR CODE", "QR CODE: " + text);
            Toast.makeText(CustomerServiceActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 图片URL转bitmap
     */
    Bitmap obmp;
    public Bitmap returnBitMap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    obmp = BitmapFactory.decodeStream(is);
                    is.close();


//                    Bitmap obmp = ((BitmapDrawable) (iv_ewCode.getDrawable())).getBitmap();
                    int width = obmp.getWidth();
                    int height = obmp.getHeight();
                    int[] data = new int[width * height];
                    obmp.getPixels(data, 0, width, 0, 0, width, height);
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    new QrCodeAsyncTask().execute(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return obmp;
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

}
