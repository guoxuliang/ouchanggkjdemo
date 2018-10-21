package com.example.ouc.demo.ui.activity.vip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.ouc.demo.entity.RealNameEntity;
import com.example.ouc.demo.entity.UserInfoEntity;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.PhoneFormatCheckUtils;
import com.example.ouc.demo.utils.PopWindowUtil;
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
import java.util.ArrayList;

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
    private TextView tv_back,tv_content;
    private Button submit_real;
    private EditText et_name,et_mail,et_sfid;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;

    ArrayList<String> list = new ArrayList<>();
    private String TAG = RealNameActivity.class.getSimpleName();

    private String id,name,email,cardNumber;
    private String file1, file2, file3;
    private RealNameEntity realNameEntity;
    private Gson gson=new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname);
        initTitle();
        init();
    }

    private void initTitle() {
        tv_back=findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealNameActivity.this.finish();
            }
        });
        tv_content=findViewById(R.id.tv_title);
        tv_content.setText("实名信息");
    }

    private void init() {
        submit_real = findViewById(R.id.submit_real);
        et_sfid = findViewById(R.id.et_sfid);
        et_sfid.getText().toString().trim();

        submit_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  调用接口    传入需要的参数值
                boolean sfid= PhoneFormatCheckUtils.isSFid(et_sfid.getText().toString().trim());
                id=getStringSharePreferences("id","id");
                name=et_name.getText().toString().trim();
                email=et_mail.getText().toString().trim();
                cardNumber=et_sfid.getText().toString().trim();
                if(id==null){
                    ToastHelper.show(RealNameActivity.this,"用户id不能为空");
                    return;
                }
                if(name==null){
                    ToastHelper.show(RealNameActivity.this,"请输入姓名");
                    return;
                }
                if(email==null){
                    ToastHelper.show(RealNameActivity.this,"请输入邮箱");
                    return;
                }
                if(PhoneFormatCheckUtils.isEmail(email)!=true){
                    ToastHelper.show(RealNameActivity.this,"邮箱格式不对");
                    return;
                }
                if(PhoneFormatCheckUtils.isEmail(cardNumber)!=true){
                    ToastHelper.show(RealNameActivity.this,"身份证格式不对");
                    return;
                }
                if (imagePaths.size() == 3) {
                    file1 = imagePaths.get(0);
                    file2 = imagePaths.get(1);
                    post_userNameInfo(file1,file2,id,name,email,cardNumber);
                }else {
                    ToastHelper.show(RealNameActivity.this,"请上传身份证");
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
                    intent.setMaxTotal(2); // 最多选择照片数量，默认为3
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    Log.i("imagePaths", "imagePaths" + imagePaths);
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
//                    Toast.makeText(ConvenientlyPictureActivity.this, "1" + position, Toast.LENGTH_SHORT).show();
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(RealNameActivity.this);
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
                    if(imagePaths.size()!=ListExtra.size()){
                        imagePaths.clear();
                        loadAdpater(ListExtra);
                    }
                    break;
            }

        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() >=4) {
            imagePaths.clear();
        }
        imagePaths.remove("paizhao");
        if (paths.contains("paizhao")) {
            paths.remove("paizhao");
        }
        paths.add("paizhao");
        Log.i("paths","paths"+paths);
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
                holder.image.setImageResource(R.mipmap.find_add_img);
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
    protected void onRestart() {
        super.onRestart();
    }


    public void post_userNameInfo(final String imgpath1,final String imgpath2, final String id, final String name,final String email,final String cardNumber) {
        new Thread() {
            @Override
            public void run() {
                MediaType MEDIA_TYPE = MediaType.parse("image/*");
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                File f1 = new File(imgpath1);
                File f2 = new File(imgpath2);
                Log.i("==gxl==f1","==gxl==f1"+f1+"==gxl==f2"+f2);
                builder.addFormDataPart("name", name)
                        .addFormDataPart("id",id+"")
                        .addFormDataPart("email",email)
                        .addFormDataPart("cardNumber",cardNumber)
                        .addFormDataPart("file", f1.getName(), RequestBody.create(MEDIA_TYPE, "file:////"+f1))
                        .addFormDataPart("file1", f2.getName(), RequestBody.create(MEDIA_TYPE, "file:////"+f2));
                Log.i("==gxl==f1","==gxl==f1"+f1+"==gxl==f2"+f2);
                MultipartBody requestBody = builder.build();
                Log.i("requestBody","requestBody"+requestBody);
                //构建请求
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Constants.SERVER_BASE_URL+"system/sys/SysMemUserController/updateRealInfo.action")
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("onResponse", "onFailure: 访问失败!"+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String result = response.body().string();
                            Log.d("result", "result:"+result);
                            realNameEntity=gson.fromJson(result, RealNameEntity.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int code=realNameEntity.getCode();
                                    if(code==200){
                                        ToastHelper.show(RealNameActivity.this,realNameEntity.getMsg());
                                        RealNameActivity.this.finish();
                                    }else{
                                        ToastHelper.show(RealNameActivity.this,realNameEntity.getMsg());
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
