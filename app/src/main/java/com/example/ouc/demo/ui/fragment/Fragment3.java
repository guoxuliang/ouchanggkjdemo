package com.example.ouc.demo.ui.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseFragment;
import com.example.ouc.demo.entity.CheckUpdataEntity;
import com.example.ouc.demo.entity.ExitEntiy;
import com.example.ouc.demo.entity.LoginEntity;
import com.example.ouc.demo.entity.MembersEntity;
import com.example.ouc.demo.entity.RecommendedEntity;
import com.example.ouc.demo.entity.RecommendedListEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.LoginActivity;
import com.example.ouc.demo.ui.activity.vip.AdvertisingActivity;
import com.example.ouc.demo.ui.activity.vip.ChangePawdActivity;
import com.example.ouc.demo.ui.activity.vip.IncomeActivity;
import com.example.ouc.demo.ui.activity.vip.MyInformationActivity;
import com.example.ouc.demo.ui.activity.vip.MyOrderActivity;
import com.example.ouc.demo.ui.activity.vip.RealNameActivity;
import com.example.ouc.demo.ui.activity.vip.WithdrawalActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.MD5Util;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.utils.Tools;
import com.example.ouc.demo.view.CommonProgressDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment3 extends BaseFragment implements View.OnClickListener {
    private LinearLayout order;
    private LinearLayout income;
    private LinearLayout realName;
    private LinearLayout withdrawal;
    private LinearLayout advertising;
    private LinearLayout information;
    private LinearLayout changePawd;
    private LinearLayout cheakversion;
    private TextView exitLogin;
    View v;
    private String updateUrl, updateInfo, lastForce;
    private CheckUpdataEntity checkUpdataEntity;
    private MembersEntity membersEntity;
    private int code;
    private CommonProgressDialog pBar;
    private Gson gson = new Gson();
    // 下载存储的文件名
    private static final String DOWNLOAD_NAME = "channelWe";

    ExitEntiy exitEntiy;
    private ArrayList<MembersEntity> data;
    private double waitaccount,commission;
    private TextView yu_e,djz;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment3, null);
        Log.i("==view", "view" + v + "***************" + getActivity().getSupportFragmentManager());
        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initView();
        yu_e = getActivity().findViewById(R.id.yu_e);
        djz = getActivity().findViewById(R.id.djz);
        order = getActivity().findViewById(R.id.order);
        income = getActivity().findViewById(R.id.income);
        realName = getActivity().findViewById(R.id.realName);
        withdrawal = getActivity().findViewById(R.id.withdrawal);
        advertising = getActivity().findViewById(R.id.advertising);
        information = getActivity().findViewById(R.id.information);
        changePawd = getActivity().findViewById(R.id.changePawd);
        cheakversion = getActivity().findViewById(R.id.cheakversion);
        exitLogin = getActivity().findViewById(R.id.exitLogin);
        order.setOnClickListener(this);
        income.setOnClickListener(this);
        realName.setOnClickListener(this);
        withdrawal.setOnClickListener(this);
        advertising.setOnClickListener(this);
        information.setOnClickListener(this);
        cheakversion.setOnClickListener(this);
        changePawd.setOnClickListener(this);
        exitLogin.setOnClickListener(this);
        String id = getStringSharePreferences("id","id");
        getBalance(id);
    }

    private void initView() {

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.order:
                //TODO  我的订单
                intent.setClass(getActivity(), MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.income:
                //TODO  收入记录
                intent.setClass(getActivity(), IncomeActivity.class);
                startActivity(intent);
                break;
            case R.id.realName:
                //TODO  实名信息
                intent.setClass(getActivity(), RealNameActivity.class);
                startActivity(intent);
                break;
            case R.id.withdrawal:
                //TODO  我的提现
                intent.setClass(getActivity(), WithdrawalActivity.class);
                startActivity(intent);
                break;
            case R.id.advertising:
                //TODO  广告记录
                intent.setClass(getActivity(), AdvertisingActivity.class);
                startActivity(intent);
                break;
            case R.id.information:
                //TODO  个人信息
                intent.setClass(getActivity(), MyInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.changePawd:
                //TODO  修改密码
                intent.setClass(getActivity(), ChangePawdActivity.class);
                startActivity(intent);
                break;
            case R.id.cheakversion:
                //TODO  检查更新
//                intent.setClass(getActivity(), ChangePawdActivity.class);
//                startActivity(intent);
                int vision = Tools.getVersion(getActivity());
                getVersionCode(String.valueOf(vision));
                break;
            case R.id.exitLogin:
                //TODO  退出登录
                postExit();
                break;
        }
    }

    private void getVersionCode(String version) {
        /**c
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        String url = Constants.SERVER_BASE_URL+"system/sys/sysController/updateAppEdition.action?localVersion=" + version;
        Log.i("url", "url:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
//                ToastHelper.show(getActivity(), "ERROR:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    checkUpdataEntity = gson.fromJson(result, CheckUpdataEntity.class);
                    code = checkUpdataEntity.getCode();
                    if (code == 200) {
                        lastForce = checkUpdataEntity.getData().getLastForce();
                        updateUrl = checkUpdataEntity.getData().getUpdateUrl().toString().trim();
                        updateInfo = checkUpdataEntity.getData().getUpdateInfo().toString().trim();
                    }

                    Log.i("data", "data:==" + "code" + code + "lastForce" + lastForce + "updateUrl" + updateUrl + "updateInfo" + updateInfo);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        if (code == 200) {
            int oldversion = Integer.parseInt(version) - 1;
            ShowDialog(oldversion, version, updateInfo, updateUrl);
            Log.i("==走了这段代码B", "==走了这段代码");
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
        new android.app.AlertDialog.Builder(getActivity())
                .setTitle("版本更新")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 检查是否获得了权限（Android6.0运行时权限）
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        } else {
                            pBar = new CommonProgressDialog(getActivity());
                            pBar.setCanceledOnTouchOutside(false);
                            pBar.setTitle("正在下载");
                            pBar.setCustomTitle(LayoutInflater.from(
                                    getActivity()).inflate(
                                    R.layout.title_dialog, null));
                            pBar.setMessage("正在下载");
                            pBar.setIndeterminate(true);
                            pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            pBar.setCancelable(true);
                            // downFile(URLData.DOWNLOAD_URL);
                            final DownloadTask downloadTask = new DownloadTask(
                                    getActivity());
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
                        if (lastForce.equals("2")) {
                            dialog.dismiss();
//                            System.exit(0);
                        } else {
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
                            Log.i("==path","path:"+ file.getParentFile().mkdirs());
                        }
                    }

                } else {
                    Toast.makeText(getActivity(), "sd卡未挂载",
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
                AndPermission.with(getActivity())
                        .requestCode(REQUEST_CODE_PERMISSION_SD)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(rationaleListener).send();


                Toast.makeText(context, "您未打开SD卡权限" + result, Toast.LENGTH_LONG).show();
//                update();
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
            AlertDialog.build(getActivity())
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
        Toast.makeText(getActivity(), R.string.message_post_succeed, Toast.LENGTH_SHORT).show();
    }

    @PermissionNo(REQUEST_CODE_PERMISSION_SD)
    private void getMultiNo(List<String> deniedPermissions) {
        Toast.makeText(getActivity(), R.string.message_post_failed, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getActivity(), R.string.message_setting_back, Toast.LENGTH_LONG).show();
                //设置成功，再次请求更新
                int vision = Tools.getVersion(getActivity());
                getVersionCode(String.valueOf(vision));
                break;
            }
        }
    }


    private void update() {
        try{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    boolean installAllowed= getActivity().getPackageManager().canRequestPackageInstalls();
                    if(installAllowed){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
                            File file= new File(
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                    , "app-release.apk");
                            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                            Uri apkUri = FileProvider.getUriForFile(getContext().getApplicationContext(), getContext().getApplicationContext().getPackageName() + ".FileProvider", file);//在AndroidManifest中的android:authorities值
                            Log.i("****apkUri","apkUri"+apkUri);
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            getActivity().startActivity(install);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent = intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), DOWNLOAD_NAME)), "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }else {
                        //无权限 申请权限
//                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_APK_REQUESTCODE);
                    }
                }
        }catch (Exception e) {
            Log.i("==e","==e"+e);
        }

    }
    /**
     * 退出登录
     */
    private void postExit(){
        Map<String,String> map = new HashMap<>();
        String id = getStringSharePreferences("id","id");
        Log.i("id::::::","id::::::"+id);
        map.put("id", id);

        HttpUtils.doPost(Constants.SERVER_BASE_URL+"system/sys/SysMemloginController/logout.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    exitEntiy = gson.fromJson(result, ExitEntiy.class);
                    final String exitCode = String.valueOf(exitEntiy.getCode());
                    if(exitCode.equals("200")){
                        boolean is_login=false;
                        setBooleanSharedPreferences("is_login","is_login",is_login);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            initListData();
                            if(exitCode.equals("200")){
                                ToastHelper.show(getActivity(),""+exitEntiy.getMsg());
                                getActivity().finish();
                                System.exit(0);
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }


    private void getBalance(String id) {
        /**
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
//        String url=Constants.SERVER_BASE_URL + "system/sys/sysController/updateAppEdition.action?" + "localVersion=" + version;
        String url = Constants.SERVER_BASE_URL +"system/sys/SysMemAccountController/getAccount.action?id=" + id;
        Log.i("url", "url:" + url);
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("e", "e:" + e);
//                ToastHelper.show(getActivity(), "ERROR:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    Log.i("result", "resultCode:" + result);
                    membersEntity = gson.fromJson(result, MembersEntity.class);
//                    Type listType2 = new TypeToken<ArrayList<MembersEntity>>() {
//                    }.getType();//TypeToken内的泛型就是Json数据中的类型
//                    data = gson.fromJson(gson.toJson(membersEntity.getData()), listType2);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            code = membersEntity.getCode();
                            if (code == 200) {
                                commission = membersEntity.getData().getCommission();
                                waitaccount = membersEntity.getData().getWaitaccount();
//                                commission = data.getData().getCommission();
                                yu_e.setText("余额（"+waitaccount+")");
                                djz.setText("待进账("+commission+")");
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
