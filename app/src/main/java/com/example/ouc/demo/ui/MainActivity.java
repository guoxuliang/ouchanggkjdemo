package com.example.ouc.demo.ui;

import android.Manifest;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.ouc.demo.entity.GetVersionEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.jpush.ExampleUtil;
import com.example.ouc.demo.jpush.LocalBroadcastManager;
import com.example.ouc.demo.ui.fragment.Fragment1;
import com.example.ouc.demo.ui.fragment.Fragment3;
import com.example.ouc.demo.ui.fragment.FragmentNew2;
import com.example.ouc.demo.ui.fragment.FragmentShop;
import com.example.ouc.demo.ui.fragment.FragmentTutorial;
import com.example.ouc.demo.utils.Constants;
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

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements PermissionInterface {
    private  GetVersionEntity getVersionEntity;
    private ImageView imageview_back, imageView;
    private TextView textView2;
//    private TextView tv_back, tv_content;
    SharedPreferences userSettings;
    private static final String DOWNLOAD_NAME = "channelWe";
    private static final int REQUEST_CODE_PERMISSION_SD = 101;
    private static final int REQUEST_CODE_SETTING = 300;
    private String updateUrl,updateInfo,lastForce,msg;
    private   CheckUpdataEntity checkUpdataEntity;
    private int code;
    private long exitTime = 0;
    /**
     * 版本更新
     */
    private CommonProgressDialog pBar;
    private Gson gson=new Gson();
    private ViewPager mPager;
    private RadioGroup mGroup;
    private RadioButton rbChat,rbContacts,rbDiscovery,rbMe,rb_shop,rb_tutorial;
    private ArrayList<Fragment> fragmentList;
    private boolean is_login=false;
    private PermissionHelper mPermissionHelper;
    public Fragment1 fragment1;
    public FragmentNew2 fragment2;
    public Fragment3 fragment3;
    public FragmentShop fragmentShop;
    public FragmentTutorial fragmentTutorial;
    private FragmentManager mFm;
    private Fragment mCurrentFragmen = null;  // 记录当前显示的Fragment
    private String[] mFragmentTagList = {"Fragment1","FragmentShop","FragmentNew2","FragmentTutorial","Fragment3"};
    private String  versioncode;
    public static boolean isForeground = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userSettings= getSharedPreferences("is_login", 0);
        Log.i("userSettings","userSettings"+userSettings);
        setContentView(R.layout.activity_main);
        //initTitle();
        initViews();
        //初始化界面组件
        initView();
        //初始化ViewPager
        initViewPager();
        mPermissionHelper = new PermissionHelper(MainActivity.this, this);
        mPermissionHelper.requestPermissions();
        jpush();
        init();
    }
    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init(){
        JPushInterface.init(getApplicationContext());
    }

    private void  jpush() {
        JPushInterface.init(getApplicationContext());
        JPushInterface.getRegistrationID(getApplicationContext());
        JPushInterface.resumePush(getApplicationContext());
//        JPushInterface.stopPush(getApplicationContext());
        registerMessageReceiver(); // used for receive msg
    }
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "me.weyye.todaynews.jpush.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
    }
    private void initViewPager(){
        fragment1=new Fragment1();
        fragmentShop=new FragmentShop();
        fragment2=new FragmentNew2();
        fragmentTutorial=new FragmentTutorial();
        fragment3=new Fragment3();
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(0,fragment1);
        fragmentList.add(1,fragmentShop);
        fragmentList.add(2,fragment2);
        fragmentList.add(3,fragmentTutorial);
        fragmentList.add(4,fragment3);
        //ViewPager设置适配器
        //ViewPager显示第一个Fragment
        mPager.setCurrentItem(0);
        //ViewPager页面切换监听
        mPager.setOnPageChangeListener(new myOnPageChangeListener());
        mPager.setAdapter(new myFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
    }
    private void initView(){
        mPager=(ViewPager)findViewById(R.id.viewPager);
        mGroup=(RadioGroup)findViewById(R.id.radiogroup);
        rbChat=(RadioButton)findViewById(R.id.rb_chat);
        rb_shop=(RadioButton)findViewById(R.id.rb_shop);
        rb_tutorial=(RadioButton)findViewById(R.id.rb_tutorial);
        rbContacts=(RadioButton)findViewById(R.id.rb_contacts);
        rbDiscovery=(RadioButton)findViewById(R.id.rb_discovery);
        //RadioGroup选中状态改变监听
        mGroup.setOnCheckedChangeListener(new myCheckChangeListener());
        mPager .setOffscreenPageLimit(4);
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
                    rb_shop.setChecked(false);
                    rb_tutorial.setChecked(false);
                    break;
                case R.id.rb_shop:
                    mPager.setCurrentItem(1);
                    rbChat.setChecked(false);
                    rbContacts.setChecked(false);
                    rbDiscovery.setChecked(false);
                    rb_shop.setChecked(true);
                    rb_tutorial.setChecked(false);
                    break;
                case R.id.rb_contacts:
                    mPager.setCurrentItem(2);
                    rbChat.setChecked(false);
                    rbContacts.setChecked(true);
                    rbDiscovery.setChecked(false);
                    rb_shop.setChecked(false);
                    rb_tutorial.setChecked(false);
                    break;
                case R.id.rb_tutorial:
                    mPager.setCurrentItem(3);
                    rbChat.setChecked(false);
                    rbContacts.setChecked(false);
                    rbDiscovery.setChecked(false);
                    rb_shop.setChecked(false);
                    rb_tutorial.setChecked(true);
                    break;
                case R.id.rb_discovery:
                    mPager.setCurrentItem(4);
                    rbChat.setChecked(false);
                    rbContacts.setChecked(false);
                    rbDiscovery.setChecked(true);
                    rb_shop.setChecked(false);
                    rb_tutorial.setChecked(false);
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
                    mGroup.check(R.id.rb_shop);
                    break;
                case 2:
                    mGroup.check(R.id.rb_contacts);
                    break;
                case 3:
                    mGroup.check(R.id.rb_tutorial);
                    break;
                case 4:
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
        getVersions();//调用更新接口
    }

    private void initTitle() {
//        tv_back = findViewById(R.id.tv_left);
//        tv_back.setVisibility(View.GONE);
//        tv_content = findViewById(R.id.tv_title);
//        tv_content.setText("广告增值平台");
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
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    /**
     * 下载应用
     *
     * @author Administrator
     */

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);   //this
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }

        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(this, R.string.message_setting_back, Toast.LENGTH_LONG).show();
                //设置成功，再次请求更新
                int vision = Tools.getVersion(this);
                Log.i("vision:::::","vision:::::"+vision);
                getVersionCode(String.valueOf(vision));
                break;
            }
        }
    }

    private void getVersionCode(final String version) {
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
                            lastForce = checkUpdataEntity.getData().getLastForce();
                            updateUrl = checkUpdataEntity.getData().getUpdateUrl().toString().trim();
                            updateInfo = checkUpdataEntity.getData().getUpdateInfo().toString().trim();
                            int oldversion = Integer.parseInt(version) - 1;
                            String newversion=version+1;
                            ShowDialog(Integer.parseInt(version), newversion, updateInfo, updateUrl);
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("版本更新");
        builder.setMessage(content);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lastForce.equals("2")) {
                            System.exit(0);
                        } else {
                            dialog.dismiss();
                        }

                    }
                });
        builder.setCancelable(false);
        builder.show();
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
            } else {
                update();
            }

        }
    }
    private void update() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {      //判读版本是否在7.0以上
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent = intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), DOWNLOAD_NAME)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                File   f= new File(Environment.getExternalStorageDirectory(),DOWNLOAD_NAME);
                Log.i("****apkUri2","file"+f);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent = intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), DOWNLOAD_NAME)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                File   f= new File(Environment.getExternalStorageDirectory(),DOWNLOAD_NAME);
                Log.i("****apkUri2","file"+f);
                startActivity(intent);
            }
        }catch (Exception e) {
            Log.i("==e","==e"+e);
        }

    }
    @PermissionYes(REQUEST_CODE_PERMISSION_SD)
    private void getMultiYes(List<String> grantedPermissions) {
        Toast.makeText(MainActivity.this, R.string.message_post_succeed, Toast.LENGTH_SHORT).show();
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_SD)
    private void getMultiNo(List<String> deniedPermissions) {
        Toast.makeText(MainActivity.this, R.string.message_post_failed, Toast.LENGTH_SHORT).show();

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
         * @param object  要接受结果的Activity、Fragment。
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         */
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }







    private void getVersions() {
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
                                int vision = Tools.getVersion(MainActivity.this);
                                String vision_str= String.valueOf(vision);
                                int versioncodes= Integer.parseInt(versioncode);
                                if(vision_str!=versioncode){
                                    if(vision<versioncodes){
                                        getVersionCode(String.valueOf(vision));
                                    }

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
}

