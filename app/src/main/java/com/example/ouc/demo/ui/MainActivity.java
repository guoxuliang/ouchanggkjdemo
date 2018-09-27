package com.example.ouc.demo.ui;

import android.Manifest;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.Permission.PermissionHelper;
import com.example.ouc.demo.Permission.PermissionInterface;
import com.example.ouc.demo.R;
import com.example.ouc.demo.adapter.myFragmentPagerAdapter;
import com.example.ouc.demo.entity.CheckUpdataEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.fragment.Fragment1;
import com.example.ouc.demo.ui.fragment.Fragment2;
import com.example.ouc.demo.ui.fragment.Fragment3;
import com.example.ouc.demo.utils.Tools;
import com.example.ouc.demo.view.CommonProgressDialog;
import com.google.gson.Gson;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

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

public class MainActivity extends FragmentActivity implements PermissionInterface {
    private ImageView imageview_back, imageView;
    private TextView textView2;
    private TextView tv_back, tv_content;
    SharedPreferences userSettings;

    private String updateUrl,updateInfo,lastForce,msg;
    private   CheckUpdataEntity checkUpdataEntity;
    private int code;
    /**
     * 版本更新
     */
    private CommonProgressDialog pBar;
    private Gson gson=new Gson();

    private ViewPager mPager;
    private RadioGroup mGroup;
    private RadioButton rbChat,rbContacts,rbDiscovery,rbMe;
    private ArrayList<Fragment> fragmentList;

    private boolean is_login=false;
    private PermissionHelper mPermissionHelper;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private FragmentManager mFm;
    private Fragment mCurrentFragmen = null;  // 记录当前显示的Fragment
    private String[] mFragmentTagList = {"Fragment1", "Fragment2", "Fragment3"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSettings= getSharedPreferences("is_login", 0);
        Log.i("userSettings","userSettings"+userSettings);
        setContentView(R.layout.activity_main);
        initTitle();
        initViews();
        //初始化界面组件
        initView();
        //初始化ViewPager
        initViewPager();
        mPermissionHelper = new PermissionHelper(MainActivity.this, this);
        mPermissionHelper.requestPermissions();
    }

    private void initViewPager(){
        fragment1=new Fragment1();
        fragment2=new Fragment2();
        fragment3=new Fragment3();
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(0,fragment1);
        fragmentList.add(1,fragment2);
        fragmentList.add(2,fragment3);
        //ViewPager设置适配器
        mPager.setAdapter(new myFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        //ViewPager显示第一个Fragment
        mPager.setCurrentItem(0);
        //ViewPager页面切换监听
        mPager.setOnPageChangeListener(new myOnPageChangeListener());
    }
    private void initView(){
        mPager=(ViewPager)findViewById(R.id.viewPager);
        mGroup=(RadioGroup)findViewById(R.id.radiogroup);
        rbChat=(RadioButton)findViewById(R.id.rb_chat);
        rbContacts=(RadioButton)findViewById(R.id.rb_contacts);
        rbDiscovery=(RadioButton)findViewById(R.id.rb_discovery);
        //RadioGroup选中状态改变监听
        mGroup.setOnCheckedChangeListener(new myCheckChangeListener());
        mPager .setOffscreenPageLimit(2);
    }



    /**
     *RadioButton切换Fragment
     */
    private class myCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_chat:
                    //ViewPager显示第一个Fragment且关闭页面切换动画效果
                    mPager.setCurrentItem(0);
                    rbChat.setChecked(true);
                    rbContacts.setChecked(false);
                    rbDiscovery.setChecked(false);

                    break;
                case R.id.rb_contacts:
                    mPager.setCurrentItem(1);
                    rbChat.setChecked(false);
                    rbContacts.setChecked(true);
                    rbDiscovery.setChecked(false);

                    break;
                case R.id.rb_discovery:
                    mPager.setCurrentItem(2);
                    rbChat.setChecked(false);
                    rbContacts.setChecked(false);
                    rbDiscovery.setChecked(true);
                    break;

            }
        }
    }








    /**
     *ViewPager切换Fragment,RadioGroup做相应变化
     */
    private class myOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    mGroup.check(R.id.rb_chat);
                    break;
                case 1:
                    mGroup.check(R.id.rb_contacts);
                    break;
                case 2:
                    mGroup.check(R.id.rb_discovery);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }








//***********************************************************************************************************************************************************************************************************









    private void initViews() {
        // 获取本版本号，是否更新
        int vision = Tools.getVersion(this);
        getVersionCode(String.valueOf(vision));
        Log.i("code","code"+code);
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_back.setVisibility(View.GONE);
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("广告增值平台");
    }

    @Override
    public int getPermissionsRequestCode() {
        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_SMS,
                Manifest.permission.BODY_SENSORS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        //权限请求用户已经全部允许
    }

    @Override
    public void requestPermissionsFail() {
        //权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。
//        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * //****************************************************************************************************************************
     * 版本更新
     */

    // 下载存储的文件名
    private static final String DOWNLOAD_NAME = "channelWe";

    // 获取更新版本号
    // 获取更新版本号
    private void getVersion(final int vision) {
        String data = "";

        String newversion = "2";//更新新的版本号
        String content = "侧边栏、弹框优化 —— 这个你自己去探索吧，总得留点悬念嘛-。";//更新内容
        String url = "http://openbox.mobilem.360.cn/index/d/sid/3429345";//安装包下载地址

        double newversioncode = Double
                .parseDouble(newversion);
        int cc = (int) (newversioncode);

        if (cc != vision) {
//            if (vision < cc) {
            // 版本号不同
            ShowDialog(vision, newversion, content, url);
//            }
        }
    }
    /**
     * 升级系统
     *
     * @param content
     * @param url
     */
    private void ShowDialog(int vision, String newversion, String content,
                            final String url) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 检查是否获得了权限（Android6.0运行时权限）
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        } else {
                            pBar = new CommonProgressDialog(MainActivity.this);
                            pBar.setCanceledOnTouchOutside(false);
                            pBar.setTitle("正在下载");
                            pBar.setCustomTitle(LayoutInflater.from(
                                    MainActivity.this).inflate(
                                    R.layout.title_dialog, null));
                            pBar.setMessage("正在下载");
                            pBar.setIndeterminate(true);
                            pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            pBar.setCancelable(true);
                            // downFile(URLData.DOWNLOAD_URL);
                            final DownloadTask downloadTask = new DownloadTask(
                                    MainActivity.this);
                            downloadTask.execute(url);
                            pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    downloadTask.cancel(true);
                                }
                            });
                        }


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(lastForce.equals("2")){
                            System.exit(0);
                        }else {
                            dialog.dismiss();
                        }

                    }
                })
                .show();
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
                    Toast.makeText(MainActivity.this, "sd卡未挂载",
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
                AndPermission.with(MainActivity.this)
                        .requestCode(REQUEST_CODE_PERMISSION_SD)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(rationaleListener).send();


                Toast.makeText(context, "您未打开SD卡权限" + result, Toast.LENGTH_LONG).show();
                update();
            } else {
                update();
            }

        }
    }

    private static final int REQUEST_CODE_PERMISSION_SD = 101;

    private static final int REQUEST_CODE_SETTING = 300;
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 自定义对话框。
            AlertDialog.build(MainActivity.this)
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


    //----------------------------------SD权限----------------------------------//


    @PermissionYes(REQUEST_CODE_PERMISSION_SD)
    private void getMultiYes(List<String> grantedPermissions) {
        Toast.makeText(this, R.string.message_post_succeed, Toast.LENGTH_SHORT).show();
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_SD)
    private void getMultiNo(List<String> deniedPermissions) {
        Toast.makeText(this, R.string.message_post_failed, Toast.LENGTH_SHORT).show();

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

    //----------------------------------权限回调处理----------------------------------//

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(this, R.string.message_setting_back, Toast.LENGTH_LONG).show();
                //设置成功，再次请求更新
                int vision = Tools.getVersion(this);
                getVersionCode(String.valueOf(vision));
                break;
            }
        }
    }


    private void update() {
        //安装应用
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), DOWNLOAD_NAME)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }



    /**
     * 检查更新
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */

    /**
     * 获取验证码
     */

    private void getVersionCode(String version) {
        /**
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
//        String url=Constants.SERVER_BASE_URL + "system/sys/sysController/updateAppEdition.action?" + "localVersion=" + version;
        String url =  "http://kgj.ockeji.com/system/sys/sysController/updateAppEdition.action?localVersion="+version;
        Log.i("url", "url:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
//                ToastHelper.show(MainActivity.this, "ERROR:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    checkUpdataEntity = gson.fromJson(result, CheckUpdataEntity.class);
                    code = checkUpdataEntity.getCode();
                    if(code==200) {
                        lastForce = checkUpdataEntity.getData().getLastForce();
                        updateUrl = checkUpdataEntity.getData().getUpdateUrl().toString().trim();
                        updateInfo = checkUpdataEntity.getData().getUpdateInfo().toString().trim();
                    }

                    Log.i("data","data:=="+"code"+code+"lastForce"+lastForce+"updateUrl"+updateUrl+"updateInfo"+updateInfo);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        if(code==200){
            int oldversion = Integer.parseInt(version)-1;
            ShowDialog(oldversion, version, updateInfo, updateUrl);
        }
    }

}

