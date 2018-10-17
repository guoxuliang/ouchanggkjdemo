package com.example.ouc.demo.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
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
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.AdvertApplyForEntity;
import com.example.ouc.demo.utils.BitmapFileSetting;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.PhotoUtils;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeliveryActivity extends BaseActivity {
    private ImageView iv_right;
    private TextView tv_back, tv_content;
    private RadioGroup rg_select;
    private RadioButton rb_tw, rb_shp;
    private Spinner spacer_select;
    private CircleImageView clicksc;
    private EditText FrName, linkmanName, linkmanPhone;
    private Button btn_sendsubmit;
    private String type="1";
    private String content = "类型一";
    private String loggersName;
    private String userid;
    private String legal_person,contacts,phone;
    private String file;
    private Gson gson=new Gson();
    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;
    // 声明平移动画
    private TranslateAnimation animation;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private Uri imageUri;
    private Uri cropImageUri;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo_yyzz.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo_yyzz.jpg");
    private AdvertApplyForEntity advertApplyForEntity;
    private CheckBox box;
    private TextView tv_tiaokuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        userid=getStringSharePreferences("id","id");
        initTitle();
        initView();
    }

    private void initView() {
        FrName =(EditText)findViewById(R.id.FrName);
        linkmanName =(EditText)findViewById(R.id.linkmanName);
        linkmanPhone =(EditText) findViewById(R.id.linkmanPhone);
        linkmanPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        btn_sendsubmit =(Button)findViewById(R.id.btn_sendsubmit);
        btn_sendsubmit.setEnabled(false);
        box = (CheckBox) findviewByid(R.id.box);
        tv_tiaokuan=findviewByid(R.id.tv_tiaokuan);
        tv_tiaokuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(StatementActivity.class);
            }
        });
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                   ToastHelper.show(DeliveryActivity.this,"选中");
                    btn_sendsubmit.setBackgroundColor(Color.parseColor("#ff0000"));
                    btn_sendsubmit.setEnabled(true);
                    tv_tiaokuan.setTextColor(R.color.hong);
                }else{
                    ToastHelper.show(DeliveryActivity.this,"未选中");
                }
            }
        });
        btn_sendsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 调用广告投放申请接口
                legal_person = FrName.getText().toString().trim();
                contacts = linkmanName.getText().toString().trim();
                phone = linkmanPhone.getText().toString().trim();
                if (type==null) {
                    ToastHelper.show(DeliveryActivity.this,"type不能为空");
                    return;
                }
                if(content==null){
                    ToastHelper.show(DeliveryActivity.this,"content不能为空");
                    return;
                }
                if(legal_person.equals("")){
                    ToastHelper.show(DeliveryActivity.this,"法人不能为空");
                    return;
                }
                if(contacts.equals("")){
                    ToastHelper.show(DeliveryActivity.this,"联系人不能为空");
                    return;
                }
                if(phone.equals("")){
                    ToastHelper.show(DeliveryActivity.this,"联系电话不能为空");
                    return;
                }
                file = String.valueOf(cropImageUri);
                Log.i("file","file====:"+file);
                if (file==null&file.equals("")){
                    ToastHelper.show(DeliveryActivity.this,"请上传营业执照");
                    return;
                }
                submitApplyFor(file,content,legal_person,contacts,phone,type,userid);
            }
        });
        clicksc = findViewById(R.id.clicksc);
        rg_select = (RadioGroup) findViewById(R.id.rg_select);
        rb_tw = (RadioButton) findViewById(R.id.rb_tw);
        rb_shp = (RadioButton) findViewById(R.id.rb_shp);
        spacer_select = findviewByid(R.id.spacer_select);
        //数据源
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("类型一");
        spinners.add("类型二");
        spinners.add("类型三");


        //设置ArrayAdapter内置的item样式-这里是单行显示样式
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners);
        //这里设置的是Spinner的样式 ， 输入 simple_之后会提示有4人，如果专属spinner的话应该是俩种，在特殊情况可自己定义样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //设置Adapter了
        spacer_select.setAdapter(adapter);
        //监听Spinner的操作
        spacer_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //选取时候的操作
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mTv.setText(adapter.getItem(position));//获取选中的item
                content = adapter.getItem(position);

            }

            //没被选取时的操作
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                mTv.setText("No anything");
            }
        });


        rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_tw.getId() == i) {
                    // 广告类型  1图文广告 2 视频广告
                    loggersName = rb_tw.getText().toString();
                    type = "1";
                }
                if (rb_shp.getId() == i) {
                    // 广告类型  1图文广告 2 视频广告
                    loggersName = rb_shp.getText().toString();
                    type = "2";
                }
                Toast.makeText(DeliveryActivity.this, "你选择了：" + loggersName, Toast.LENGTH_SHORT).show();
            }
        });
        clicksc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIcon(view);
                lightoff();
            }
        });
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right = findviewByid(R.id.iv_right);
        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
        iv_right.setVisibility(View.GONE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("广告投放");
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
                    ToastHelper.show(DeliveryActivity.this, "点击拍照");
//                    takePhoto(v);
                    autoObtainCameraPermission();
                }
            });
            popupView.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    // 打开系统图库选择图片
                    ToastHelper.show(DeliveryActivity.this, "点击相册");
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
        popupWindow.showAtLocation(DeliveryActivity.this.findViewById(R.id.lll2), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
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
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
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
        clicksc.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }





    /**
     * 上传图片
     * @return 新图片的路径
     * @throws IOException
     * @throws JSONException
     */





    public void submitApplyFor(final String file,final String content,final String legal_person,final String contacts,final String phone,final String type,final String userid) {
        new Thread() {
            @Override
            public void run() {
                MediaType MEDIA_TYPE = MediaType.parse("image/*");
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                Log.i("==gxl==fileCropUri",""+file);
                File f1 = new File(file);
                Log.i("==gxl==f1","==gxl==f1"+f1);
                builder.addFormDataPart("legal_person", legal_person)//法人姓名
                        .addFormDataPart("content",content)//广告内容
                        .addFormDataPart("contacts",contacts)//联系人
                        .addFormDataPart("phone",phone)//联系电话
                        .addFormDataPart("type",type)//广告类型
                        .addFormDataPart("userid",userid)//广告类型
                        .addFormDataPart("file", f1.getName(), RequestBody.create(MEDIA_TYPE, fileCropUri));
                MultipartBody requestBody = builder.build();
                Log.i("requestBody","requestBody"+requestBody);
                //构建请求
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Constants.SERVER_BASE_URL+"system/sys/sysMemAdvertiserController/insertAdvertiser.action")
                        .post(requestBody)
                        .build();
//                        Call call = client.newCall(request);
//                        call.enqueue(new Callback() {
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("onResponse", "onFailure: 访问失败!"+e);
//                        ToastHelper.show(MyInformationActivity.this,"访问失败!"+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            Log.d("onResponse", "onResponse: 访问成功!");
                            String json = response.body().string();
                            Log.d("onResponse", "onResponse: json:" + json);
                            advertApplyForEntity = gson.fromJson(json, AdvertApplyForEntity.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int  code=advertApplyForEntity.getCode();
                                    if(code==200){
                                        ToastHelper.show(DeliveryActivity.this,""+advertApplyForEntity.getMsg());
                                        DeliveryActivity.this.finish();
                                    }else {
                                        ToastHelper.show(DeliveryActivity.this,""+advertApplyForEntity.getMsg());
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
