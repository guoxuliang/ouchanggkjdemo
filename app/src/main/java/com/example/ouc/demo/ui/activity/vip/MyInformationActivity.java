package com.example.ouc.demo.ui.activity.vip;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.UserInfoEntity;
import com.example.ouc.demo.utils.BitmapFileSetting;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.PhotoUtils;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 个人信息
 */
public class MyInformationActivity extends BaseActivity {
    private EditText nackName;
    private CircleImageView iv_photo;
    private Button submit_info;

    private String username;//	true	String	用户名
    private String headImg;//	true	String	会员头像

    private Gson gson = new Gson();
    private File file;

    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;
    // 声明平移动画
    private TranslateAnimation animation;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private UserInfoEntity userInfoEntity;


    private ImageView iv_right;
    private TextView tv_back, tv_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initViews();
        initTitle();
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right = findViewById(R.id.iv_right);
        iv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyInformationActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("个人信息");
    }

    private void initViews() {
        nackName = findViewById(R.id.nickName);
        iv_photo = findViewById(R.id.iv_photo);
        submit_info = findViewById(R.id.submit_info);
        submit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = nackName.getText().toString().trim();
                String ids = getStringSharePreferences("id", "id");
                Log.i("ids", "ids" + ids+"cropImageUri;;;;;;;;;;;;;;;;"+String.valueOf(cropImageUri));
                post_UpLoadIMG(String.valueOf(cropImageUri), username, ids);
//                }
            }
        });
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIcon(view);
                lightoff();
            }
        });
    }

    /**
     * 弹出popupWindow更改头像
     *
     * @param view
     */
    private void changeIcon(View view) {
        if (popupWindow == null) {
            popupView = View.inflate(this, R.layout.window_popup, null);
            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    lighton();
                }
            });
            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);
            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);

            popupView.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开系统拍照程
                    ToastHelper.show(MyInformationActivity.this, "点击拍照");
//                    takePhoto(v);
                    autoObtainCameraPermission();
                }
            });
            popupView.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    // 打开系统图库选择图片
                    ToastHelper.show(MyInformationActivity.this, "点击相册");
//                    getPhoto(v);
                    autoObtainStoragePermission();
                }
            });
        }

        // 在点击之后设置popupwindow的销毁
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            lighton();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(MyInformationActivity.this.findViewById(R.id.lll), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
    }


    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastHelper.show(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(getApplicationContext(), getApplication().getPackageName() + ".FileProvider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastHelper.show(this, "设备没有SD卡！");
            }
        }
    }

    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(getApplicationContext(), getApplication().getPackageName() + ".FileProvider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastHelper.show(this, "设备没有SD卡！");
                    }
                } else {

                    ToastHelper.show(this, "请允许打开相机！！");
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastHelper.show(this, "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Log.i("====cropImageUri","cropImageUri"+cropImageUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Log.i("====cropImageUri","cropImageUri"+cropImageUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(getApplicationContext(), getApplication().getPackageName() + ".FileProvider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastHelper.show(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        BitmapFileSetting bitmapFileSetting = new BitmapFileSetting();
                        File f = bitmapFileSetting.saveBitmapFile(bitmap, Environment.getExternalStorageDirectory().getPath());
                        Log.i("==filepath", "==filepath:" + f);
                        showImages(bitmap);
                    }
                    break;
                default:
            }
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    /**
     * 展示图片
     *
     * @param bitmap
     */
    private void showImages(Bitmap bitmap) {
        iv_photo.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public void post_UpLoadIMG(final String imgpath1, final String username, final String ids) {
        new Thread() {
            @Override
            public void run() {
                MediaType MEDIA_TYPE = MediaType.parse("image/*");
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                Log.i("==gxl==fileCropUri", "" + imgpath1 + username);
                File f1 = new File(imgpath1);
                Log.i("==gxl==f1", "==gxl==f1" + f1);
                int id = Integer.parseInt(ids);
                builder.addFormDataPart("username", username)
                        .addFormDataPart("id", ids + "")
                        .addFormDataPart("file", f1.getName(), RequestBody.create(MEDIA_TYPE, fileCropUri));
                MultipartBody requestBody = builder.build();
                Log.i("requestBody", "requestBody" + requestBody);
                //构建请求
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/updateUserInfo.action")
                        .post(requestBody)
                        .build();
//                        Call call = client.newCall(request);
//                        call.enqueue(new Callback() {
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("onResponse", "onFailure: 访问失败!" + e);
//                        ToastHelper.show(MyInformationActivity.this,"访问失败!"+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            Log.d("onResponse", "onResponse: 访问成功!");
                            String json = response.body().string();
                            Log.d("onResponse", "onResponse: json:" + json);
                            userInfoEntity = gson.fromJson(json, UserInfoEntity.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int code = userInfoEntity.getCode();
                                    if (code == 200) {
                                        if(String.valueOf(cropImageUri)!=null){
                                            String headphoto=String.valueOf(cropImageUri);
                                            Log.i("headphoto","headphoto"+headphoto);
                                            setStringSharedPreferences("headphoto","headphoto",headphoto);
                                        }
                                        ToastHelper.show(MyInformationActivity.this, "" + userInfoEntity.getMsg());
                                        MyInformationActivity.this.finish();
                                    } else {
                                        ToastHelper.show(MyInformationActivity.this, "" + userInfoEntity.getMsg());
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("gxl======", "gxl" + e);
                        }
                    }
                });
            }
        }.start();
    }

}
