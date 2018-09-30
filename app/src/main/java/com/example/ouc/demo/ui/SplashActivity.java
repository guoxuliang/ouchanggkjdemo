package com.example.ouc.demo.ui;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.CheckUpdataEntity;
import com.example.ouc.demo.entity.GetVersionEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.LoginActivity;
import com.example.ouc.demo.ui.fragment.Fragment1;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.utils.Tools;
import com.example.ouc.demo.view.CommonProgressDialog;
import com.google.gson.Gson;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.youth.banner.Banner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends BaseActivity {
    private  GetVersionEntity getVersionEntity;
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
    private CheckUpdataEntity checkUpdataEntity;
    private int code;
    private ImageView iv_bigimg;
    /**
     * 版本更新
     */
    private CommonProgressDialog pBar;
    private Gson gson = new Gson();
    private String  versioncode;

    private String url="";
    // 声明控件对象
    private TextView textView,textView_pass;
    private int count = 5;
    private Animation animation;
    private LinearLayout daojishi;
    private ImageView imageview;
    private Intent intent;
    private boolean islogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         islogin = getBooleanSharePreferences("is_login","is_login");

        imageview= (ImageView) findViewById(R.id.imageview);
        textView = (TextView) findViewById(R.id.textView);
        textView_pass = (TextView) findViewById(R.id.pass);
        daojishi = (LinearLayout) findViewById(R.id.daojishi);
        new FetchDataTask().execute(url);

        handler.sendEmptyMessageDelayed(0, 1000);
        daojishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(islogin==true){
                    intent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }else{
                    intent=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
                handler.removeMessages(0);
            }
        });
//        getVersion();
//        initViews();
    }
    private void initViews() {
        // 获取本版本号，是否更新
        int vision = Tools.getVersion(this);
        getVersionCode(String.valueOf(vision));
        Log.i("code", "code" + code);
    }

    private void getVersionCode(String version) {
        /**
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        String url = Constants.SERVER_BASE_URL+"system/sys/sysController/updateAppEdition.action?serverFlag=1&localVersion=" + version;
        Log.i("url", "url:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
//                ToastHelper.show(SplashActivity.this, "ERROR:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    checkUpdataEntity = gson.fromJson(result, CheckUpdataEntity.class);
                    code = checkUpdataEntity.getCode();
                    Log.i("data", "data:==" + "code" + code + "lastForce" + lastForce + "updateUrl" + updateUrl + "updateInfo" + updateInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO   UI线程中的操作在此处进行，否则报错
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        if (code == 200) {
            lastForce = checkUpdataEntity.getData().getLastForce();
            updateUrl = checkUpdataEntity.getData().getUpdateUrl().toString().trim();
            updateInfo = checkUpdataEntity.getData().getUpdateInfo().toString().trim();
            int oldversion = Integer.parseInt(version) - 1;
            ShowDialog(oldversion, version, updateInfo, updateUrl);
        }
    }


    private void getVersion() {
        /**
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        String url = Constants.SERVER_BASE_URL+"system/sys/sysController/NowAppEdition.action?serverFlag=1";
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
                     getVersionEntity = gson.fromJson(result, GetVersionEntity.class);
                    code = getVersionEntity.getCode();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO   UI线程中的操作在此处进行，否则报错
                            if(getVersionEntity.getCode()==200){
                               versioncode= String.valueOf(getVersionEntity.getData());
                                int vision = Tools.getVersion(SplashActivity.this);
                                String vision_str= String.valueOf(vision);
                                if(vision_str!=versioncode){
                                    getVersionCode(String.valueOf(vision));
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

    /**
     * 升级系统
     *
     * @param content
     * @param url
     */
    private void ShowDialog(int vision, String newversion, String content,
                            final String url) {
        new android.app.AlertDialog.Builder(SplashActivity.this)
                .setTitle("版本更新")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pBar = new CommonProgressDialog(SplashActivity.this);
                        pBar.setCanceledOnTouchOutside(false);
                        pBar.setTitle("正在下载");
                        pBar.setCustomTitle(LayoutInflater.from(
                                SplashActivity.this).inflate(
                                R.layout.title_dialog, null));
                        pBar.setMessage("正在下载");
                        pBar.setIndeterminate(true);
                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pBar.setCancelable(true);
                        // downFile(URLData.DOWNLOAD_URL);
                        final DownloadTask downloadTask = new DownloadTask(
                                SplashActivity.this);
                        downloadTask.execute(url);
                        pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                downloadTask.cancel(true);
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lastForce.equals("2")) {
                            System.exit(0);
                        } else {
                            dialog.dismiss();
                        }

                    }
                })
                .show();
    }

    @PermissionYes(REQUEST_CODE_PERMISSION_SD)
    private void getMultiYes(List<String> grantedPermissions) {
        Toast.makeText(SplashActivity.this, R.string.message_post_succeed, Toast.LENGTH_SHORT).show();
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_SD)
    private void getMultiNo(List<String> deniedPermissions) {
        Toast.makeText(SplashActivity.this, R.string.message_post_failed, Toast.LENGTH_SHORT).show();

        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                    .setTitle(R.string.title_dialog)
                    .setMessage(R.string.message_permission_failed)
                    .setPositiveButton(R.string.btn_dialog_yes_permission)
                    .setNegativeButton(R.string.btn_dialog_no_permission, null)
                    .show();

            // 更多自定dialog，请看上面。
        }
    }


    //----------------------------------SD权限----------------------------------//

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 转给AndPermission分析结果。
         *
         * @param object     要接受结果的Activity、Fragment。
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         */
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(SplashActivity.this, R.string.message_setting_back, Toast.LENGTH_LONG).show();
                //设置成功，再次请求更新
                break;
            }
        }
    }

    //----------------------------------权限回调处理----------------------------------//

//    private void update() {
//        //安装应用
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(new File(Environment
//                        .getExternalStorageDirectory(), DOWNLOAD_NAME)),
//                "application/vnd.android.package-archive");
//        startActivity(intent);
//    }

    private void update() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
//                        File file= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                                , "app-release.apk");
////                        File file=new File(getActivity().getCacheDir(),"app-release.apk");
//                        Log.i("****apkUri","file"+file);
//                        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//                        Uri apkUri = FileProvider.getUriForFile(getContext().getApplicationContext(), getContext().getApplicationContext().getPackageName() + ".FileProvider", file);//在AndroidManifest中的android:authorities值
//                        Log.i("****apkUri","apkUri"+apkUri);
//                        Intent install = new Intent(Intent.ACTION_VIEW);
//                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
//                        getActivity().startActivity(install);
//                    } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent = intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), DOWNLOAD_NAME)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                File   f= new File(Environment.getExternalStorageDirectory(),DOWNLOAD_NAME);
                Log.i("****apkUri2","file"+f);
                startActivity(intent);
            }
//                }else {
//                    //无权限 申请权限
////                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_APK_REQUESTCODE);
//                }
//            }
        }catch (Exception e) {
            Log.i("==e","==e"+e);
        }

    }

    /**
     * 下载应用
     *
     * @author Administrator
     */
    class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File file = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP "
                            + connection.getResponseCode() + " "
                            + connection.getResponseMessage();
                }
                int fileLength = connection.getContentLength();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = new File(Environment.getExternalStorageDirectory(),
                            DOWNLOAD_NAME);

                    if (!file.exists()) {
                        // 判断父文件夹是否存在
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }

                } else {
                    Toast.makeText(SplashActivity.this, "sd卡未挂载",
                            Toast.LENGTH_LONG).show();
                }
                input = connection.getInputStream();
                output = new FileOutputStream(file);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);

                }
            } catch (Exception e) {
                System.out.println(e.toString());
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            pBar.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            pBar.setIndeterminate(false);
            pBar.setMax(100);
            pBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            pBar.dismiss();
            if (result != null) {
                // 申请多个权限。
                AndPermission.with(SplashActivity.this)
                        .requestCode(REQUEST_CODE_PERMISSION_SD)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(rationaleListener).send();


                Toast.makeText(context, "您未打开SD卡权限" + result, Toast.LENGTH_LONG).show();
            } else {
                update();
            }

        }
    }
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();

            // 自定义对话框。
            AlertDialog.build(SplashActivity.this)
                    .setTitle(R.string.title_dialog)
                    .setMessage(R.string.message_permission_rationale)
                    .setPositiveButton(R.string.btn_dialog_yes_permission, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })

                    .setNegativeButton(R.string.btn_dialog_no_permission, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    })
                    .show();
        }
    };


    private int getCount() {
        count--;
        if (count == 0) {
            if(islogin==true){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }
        return count;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                textView.setText(getCount()+"");
                handler.sendEmptyMessageDelayed(0, 1000);
//                animation.reset();
//                textView.startAnimation(animation);
            }


        };

    };



    public class FetchDataTask extends AsyncTask<String,Void,Bitmap> {

        //执行前调用
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //执行后台任务
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;
            try {
                //通过传入的图片地址，获取图片
                HttpURLConnection connection= (HttpURLConnection) (new URL(strings[0])).openConnection();
                InputStream is= null;
                try {
                    is = connection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap= BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        //任务完成时调用
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
////
////            //将获得的数据通过Intent传送给MainActivity
//            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
//            //注意，intent传递图片时，图片对象大小不应该超过40K
//            intent.putExtra("Image",result);
//            startActivity(intent);
//
//            //启动MainActivity后销毁自身
//            finish();
//            imageview.setImageBitmap(result);

        }
    }
}
