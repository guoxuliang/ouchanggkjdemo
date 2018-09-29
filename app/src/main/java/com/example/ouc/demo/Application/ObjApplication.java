package com.example.ouc.demo.Application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;


import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;
import cn.jiguang.share.android.utils.Logger;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class ObjApplication extends Application {
    public static String ImagePath;
    public static String VideoPath;
    private static final String TAG = "JIGUANG-Example";
    @Override
    public void onCreate() {
        Logger.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "577d9af4b0", false);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);

        JShareInterface.setDebugMode(true);
        PlatformConfig platformConfig = new PlatformConfig()
                .setWechat("wxa8d1d04e3aba2b5e", "2d529fae1a0e960fa28039f39ea1d22d");
        /**
         * since 1.5.0，1.5.0版本后增加API，支持在代码中设置第三方appKey等信息，当PlatformConfig为null时，或者使用JShareInterface.init(Context)时需要配置assets目录下的JGShareSDK.xml
         **/
        //*
        JShareInterface.init(this, platformConfig);
        /*/
        JShareInterface.init(this);
       /**/

        new Thread(){
            @Override
            public void run() {
                File imageFile =  copyResurces( "jiguang_test_img.png", "test_img.png", 0);
                File videoFile = copyResurces( "jiguang_test.mp4", "jiguang_test.mp4", 0);
                if(imageFile != null){
                    ImagePath = imageFile.getAbsolutePath();
                }

                if(videoFile != null){
                    VideoPath = videoFile.getAbsolutePath();
                }

                super.run();
            }
        }.start();

    }
    private File copyResurces(String src, String dest, int flag){
        File filesDir = null;
        try {
            if(flag == 0) {//copy to sdcard
                filesDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/jiguang/" + dest);
                File parentDir = filesDir.getParentFile();
                if(!parentDir.exists()){
                    parentDir.mkdirs();
                }
            }else{//copy to data
                filesDir = new File(getFilesDir(), dest);
            }
            if(!filesDir.exists()) {
                filesDir.createNewFile();
                InputStream open = getAssets().open(src);
                FileOutputStream fileOutputStream = new FileOutputStream(filesDir);
                byte[] buffer = new byte[4 * 1024];
                int len = 0;
                while ((len = open.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }
                open.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(flag == 0){
                filesDir = copyResurces( src,  dest, 1);
            }
        }
        return filesDir;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public List<String> getPermissionList(Activity activity){
        List<String> permission = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.READ_PHONE_STATE);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_WIFI_STATE);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_NETWORK_STATE);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.INTERNET);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.CHANGE_WIFI_STATE);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.REQUEST_INSTALL_PACKAGES)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.REQUEST_INSTALL_PACKAGES);
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        return permission;
    }


}