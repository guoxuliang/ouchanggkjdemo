package com.example.ouc.demo.ui.activity.vip;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.IsSmInfoEntity;
import com.example.ouc.demo.entity.RealNameEntity;
import com.example.ouc.demo.entity.RecordEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.activity.WithdrawalRecordActivity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.PhoneFormatCheckUtils;
import com.example.ouc.demo.utils.ToastHelper;
import com.google.gson.Gson;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RealNameActivity extends BaseActivity {
    private GridView gridView;
    private GridAdapter gridAdapter;
    private TextView tv_back, tv_content;
    private Button submit_real;
    private EditText et_name, et_mail, et_sfid,sb_sbm;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;

    ArrayList<String> list = new ArrayList<>();
    private String TAG = RealNameActivity.class.getSimpleName();

    private String id, name, email, cardNumber;
    private String file1, file2, file3;
    private RealNameEntity realNameEntity;
    private Gson gson = new Gson();

    private IsSmInfoEntity isSmInfoEntity;
    private List<IsSmInfoEntity.DataBean> dataList = new ArrayList<>();
    private String isreal;
    private String deviceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname);
        initTitle();
        init();
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealNameActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("实名信息");
    }

    private void init() {
        submit_real = findViewById(R.id.submit_real);
        et_sfid = findViewById(R.id.et_sfid);
        sb_sbm = findViewById(R.id.sb_sbm);
        sb_sbm.setFocusable(false);
        sb_sbm.setFocusableInTouchMode(false);
        getIMEI(this);
        et_sfid.getText().toString().trim();

        submit_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  调用接口    传入需要的参数值
                id = getStringSharePreferences("id", "id");
                name = et_name.getText().toString().trim();
                email = et_mail.getText().toString().trim();
                cardNumber = et_sfid.getText().toString().trim();
                if (id == null) {
                    ToastHelper.show(RealNameActivity.this, "用户id不能为空");
                    return;
                }
                if (name == null) {
                    ToastHelper.show(RealNameActivity.this, "请输入姓名");
                    return;
                }
                if (PhoneFormatCheckUtils.isLegalName(name) != true) {
                    ToastHelper.show(RealNameActivity.this, "请输入中文姓名");
                    return;
                }
                if (email == null) {
                    ToastHelper.show(RealNameActivity.this, "请输入邮箱");
                    return;
                }
                if (PhoneFormatCheckUtils.isEmail(email) != true) {
                    ToastHelper.show(RealNameActivity.this, "邮箱格式不对");
                    return;
                }
                if (PhoneFormatCheckUtils.isLegalId(cardNumber) != true) {
                    ToastHelper.show(RealNameActivity.this, "身份证格式不对");
                    return;
                }
                if (imagePaths.size() == 3) {
                    file1 = imagePaths.get(0);
                    file2 = imagePaths.get(1);
                    file3 = imagePaths.get(2);
                    post_userNameInfo(file1, file2, file3, id, name, email, cardNumber,deviceId);
                } else {
                    ToastHelper.show(RealNameActivity.this, "请上传相应的证件");
                }

            }
        });
        gridView = findViewById(R.id.gridView);
        et_name = findViewById(R.id.et_name);
        et_mail = findViewById(R.id.et_mail);
        et_sfid = findViewById(R.id.et_sfid);
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String imgs = (String) parent.getItemAtPosition(position);
                if ("paizhao".equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(RealNameActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(3); // 最多选择照片数量，默认为3
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    Log.i("imagePaths", "imagePaths" + imagePaths);
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
//                    Toast.makeText(ConvenientlyPictureActivity.this, "1" + position, Toast.LENGTH_SHORT).show();
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(RealNameActivity.this);
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("paizhao");
        gridAdapter = new GridAdapter(imagePaths);
        Log.i("imagePathspz", "imagePathspz" + imagePaths);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * 相机相册回来的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "数量：" + list.size());
                    Log.d(TAG, "list" + list);
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra" + ListExtra);
                    if (imagePaths.size() != ListExtra.size()) {
                        imagePaths.clear();
                        loadAdpater(ListExtra);
                    }
                    break;
            }

        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() >= 4) {
            imagePaths.clear();
        }
        imagePaths.remove("paizhao");
        if (paths.contains("paizhao")) {
            paths.remove("paizhao");
        }
        paths.add("paizhao");
        Log.i("paths", "paths" + paths);
        imagePaths.addAll(paths);
//        if (imagePaths.size()==4){
//            imagePaths.remove("paizhao");
//        }
        Log.i("imagePathsq", "imagePathsq" + imagePaths);
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        try {
            JSONArray obj = new JSONArray(imagePaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;

        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            Log.i("listUrls", "listUrls" + listUrls);
            if (listUrls.size() == 4) {
                listUrls.remove(listUrls.size() - 1);
            }
            inflater = LayoutInflater.from(RealNameActivity.this);
        }

        public int getCount() {
            return listUrls.size();
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String path = listUrls.get(position);
            if (path.equals("paizhao")) {
                if (listUrls.size() == 1) {
                    holder.image.setImageResource(R.drawable.bg_sfzzm);
                } else if (listUrls.size() == 2) {
                    holder.image.setImageResource(R.drawable.bg_sfzfm);
                } else {
                    holder.image.setImageResource(R.drawable.bg_sbxx);
                }

            } else {
                Glide.with(RealNameActivity.this)
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        id = getStringSharePreferences("id", "id");
//        post_isSM();
        isreal = getStringSharePreferences("isreal", "isreal");
        if (isreal.equals("1")) {
            submit_real.setBackgroundColor(R.color.gray);
            submit_real.setEnabled(false);
            submit_real.setText("您已实名通过,不可重复提交");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    public void post_userNameInfo(final String imgpath1, final String imgpath2, final String imgpath3, final String id, final String name, final String email, final String cardNumber,final String deviceId) {
        new Thread() {
            @Override
            public void run() {
                MediaType MEDIA_TYPE = MediaType.parse("image/*");
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                File f1 = new File(imgpath1);
                File f2 = new File(imgpath2);
                File f3 = new File(imgpath3);
                builder.addFormDataPart("name", name)
                        .addFormDataPart("id", id + "")
                        .addFormDataPart("email", email)
                        .addFormDataPart("cardNumber", cardNumber)
                        .addFormDataPart("deviceId", deviceId)
                        .addFormDataPart("file", f1.getName(), RequestBody.create(MEDIA_TYPE, f1))
                        .addFormDataPart("file1", f2.getName(), RequestBody.create(MEDIA_TYPE, f2))
                        .addFormDataPart("file2", f3.getName(), RequestBody.create(MEDIA_TYPE, f3));
                Log.i("lujing", "lujing" + "file://" + f3);
                Log.i("==gxl==f1", ",==gxl==f1" + f1 + ",==gxl==f2" + f2 + ",name:" + name + ",id:" + id + ",email:" + email + ",cardNumber:" + cardNumber);
                MultipartBody requestBody = builder.build();
                Log.i("requestBody", "requestBody" + requestBody);
                //构建请求
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Constants.SERVER_BASE_URL + "system/sys/SysMemUserController/updateRealInfo.action")
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
                            realNameEntity = gson.fromJson(result, RealNameEntity.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int code = realNameEntity.getCode();
                                    if (code == 200) {
                                        ToastHelper.show(RealNameActivity.this, realNameEntity.getMsg());
                                        RealNameActivity.this.finish();
                                    } else {
                                        ToastHelper.show(RealNameActivity.this, realNameEntity.getMsg());
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

    /**
     * 获取设备的相关信息
     * @param context
     */
    public void getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, Manifest.permission.REQUEST_PHONE_STATE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 0);

        } else {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            StringBuilder sb = new StringBuilder();
            sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());//获取设备IMEI信息
//            sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
//            sb.append("\nLine1Number = " + tm.getLine1Number());
//            sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
//            sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
//            sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
//            sb.append("\nNetworkType = " + tm.getNetworkType());
//            sb.append("\nPhoneType = " + tm.getPhoneType());
//            sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
//            sb.append("\nSimOperator = " + tm.getSimOperator());
//            sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
//            sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
//            sb.append("\nSimState = " + tm.getSimState());
//            sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
//            sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
            sb.toString();
            Log.i("sb.toString()","sb.toString()======"+sb.toString());
             deviceId= tm.getSubscriberId();
            sb_sbm.setText(tm.getDeviceId());

        }

    }
}