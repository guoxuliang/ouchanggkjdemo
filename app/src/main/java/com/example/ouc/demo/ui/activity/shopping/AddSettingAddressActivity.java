//package com.example.ouc.demo.ui.activity.shopping;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.ouc.demo.R;
//import com.example.ouc.demo.base.BaseActivity;
//import com.example.ouc.demo.entity.AddSettingAddressEntity;
//import com.example.ouc.demo.http.HttpUtils;
//import com.example.ouc.demo.utils.Constants;
//import com.example.ouc.demo.utils.ToastHelper;
//import com.example.ouc.demo.view.DrawableSwitch;
//import com.google.gson.Gson;
//import com.lljjcoder.citypickerview.widget.CityPicker;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//public class AddSettingAddressActivity extends BaseActivity implements View.OnClickListener {
//    private ImageView iv_left;
//    private TextView tv_title,tv_right;
//    private Gson gson = new Gson();
//    private String userid;
//    private EditText shr_name,shr_phone,xi_address;
//    private DrawableSwitch drawableSwitch;
//    private TextView textview_address;
//    private TextView tv_edit_address;
//    private AddSettingAddressEntity addSettingAddressEntity;
//    private String str_isdefault2="1";
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_addsettingaddress);
//        initTitle();
//        userid = getStringSharePreferences("id", "id");
//        initViews();
//       }
//
//    private void initTitle() {
//        iv_left  = findViewById(R.id.iv_left);
//        iv_left.setVisibility(View.VISIBLE);
//        iv_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        tv_title = findViewById(R.id.tv_title);
//        tv_title.setText("添加收货地址");
//        tv_right = findViewById(R.id.tv_right);
//        tv_right.setText("保存");
//        tv_right.setOnClickListener(this);
//
//    }
//
//
//    private void initViews() {
//        shr_name = findviewByid(R.id.shr_name);
//        shr_phone = findviewByid(R.id.shr_phone);
//        textview_address = findviewByid(R.id.textview_address);
//        xi_address = findviewByid(R.id.xi_address);
//        tv_edit_address = findviewByid(R.id.tv_edit_address);
//        tv_edit_address.setVisibility(View.GONE);
//        drawableSwitch = findviewByid(R.id.drawableSwitch);
//        textview_address.setOnClickListener(this);
//        drawableSwitch.setListener(new DrawableSwitch.MySwitchStateChangeListener(){
//            @Override
//            public void mySwitchStateChanged(boolean isSwitchOn){
//                if(isSwitchOn==true){
//                    str_isdefault2 = "0";
//                }else {
//                    str_isdefault2 = "1";
//                }
//
//            }
//        });
//
//    }
//    /**
//     * 选择地址
//     */
//    private void selectAddress() {
//        CityPicker cityPicker = new CityPicker.Builder(AddSettingAddressActivity.this)
//                .textSize(14)
//                .title("地址选择")
//                .titleBackgroundColor("#FFFFFF")
//                .confirTextColor("#696969")
//                .cancelTextColor("#696969")
//                .province("陕西省")
//                .city("西安市")
//                .district("新城区")
//                .textColor(Color.parseColor("#000000"))
//                .provinceCyclic(false)
//                .cityCyclic(false)
//                .districtCyclic(false)
//                .visibleItemsCount(7)
//                .itemPadding(20)
//                .onlyShowProvinceAndCity(false)
//                .build();
//        cityPicker.show();
//        //监听方法，获取选择结果
//        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
//            @Override
//            public void onSelected(String... citySelected) {
//                //省份
//                String province = citySelected[0];
//                //城市
//                String city = citySelected[1];
//                //区县（如果设定了两级联动，那么该项返回空）
//                String district = citySelected[2];
//                //邮编
//                String code = citySelected[3];
//                //为TextView赋值
//                textview_address.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
//            }
//        });
//    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.tv_right:
//                String address_z=textview_address.getText().toString().trim();
//                String xxdizhi = xi_address.getText().toString().trim();
//                String delivename = shr_name.getText().toString().trim();
//                String  phone = shr_phone.getText().toString().trim();
//                addAddress(userid,"陕西省","西安市","新城区",xxdizhi,delivename,phone,str_isdefault2);
//                break;
//            case R.id.textview_address:
//                selectAddress();
//                break;
//        }
//    }
//
//
//    /**
//     * Post请求
//     * 参数一：请求Url
//     * 参数二：请求的键值对
//     * 参数三：添加地址
//     */
//    private void addAddress(String userid,String province,String city,String district,String detailed_address,String delivename,String phone,String str_isdefault2) {
//        Map<String, String> map = new HashMap<>();
//        map.put("userid", userid);
//        map.put("province",province);
//        map.put("city", city);
//        map.put("district", district);
//        map.put("detailed_address",detailed_address);
//        map.put("delivename",delivename);
//        map.put("phone",phone);
//        map.put("isdefault", str_isdefault2);
//        Log.i("str_isdefault2","str_isdefault2"+"="+userid+"="+province+"="+city+"="+district+"="+detailed_address+"="+delivename+"="+phone+"="+str_isdefault2);
//        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/shop/addressController/addAddress.action", map, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("dfsd", "dsfsd" + e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    String result = response.body().string();
//                    Log.i("result", "result:" + result);
//                    addSettingAddressEntity = gson.fromJson(result, AddSettingAddressEntity.class);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (addSettingAddressEntity.getCode() == 200) {
//                                ToastHelper.show(AddSettingAddressActivity.this, addSettingAddressEntity.getMsg());
//                                AddSettingAddressActivity.this.finish();
//
//                            } else {
//                                ToastHelper.show(AddSettingAddressActivity.this, addSettingAddressEntity.getMsg());
//                            }
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//    }
//
//}





package com.example.ouc.demo.ui.activity.shopping;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.entity.AddSettingAddressEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.view.DrawableSwitch;
import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddSettingAddressActivity extends BaseActivity implements View.OnClickListener {

    private DrawableSwitch drawableSwitch;
    private TextView textview_address,tv_title,tv_right,tv_edit_address;
    private ImageView iv_left;
    private String str_isdefault = "";
    private String str_isdefault2="1";
    private EditText shr_name,shr_phone,xi_address;
    private AddSettingAddressEntity addSettingAddressEntity;
    private Gson gson = new Gson();
    private String id,province,city,district,detailed_address,delivename,phone;
    private boolean isdefault=false;
    private String userid;

    private String province2;
    private String city2;
    private String district2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsettingaddress);
        userid = getStringSharePreferences("id","id");
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            id = bundle.getString("id");
            province =   bundle.getString("province");
            city =   bundle.getString("city");
            district =   bundle.getString("district");
            detailed_address =   bundle.getString("detailed_address");
            delivename =   bundle.getString("delivename");
            phone =   bundle.getString("phone");
            str_isdefault =   bundle.getString("isdefault");
        }
        initTitle();
        initViews();
        if(bundle!=null){
            tv_right.setVisibility(View.GONE);
            tv_edit_address.setVisibility(View.VISIBLE);

            if(str_isdefault.equals("1")){
                drawableSwitch.setSwitchOn(false);
            }else if(str_isdefault.equals("0")){
                drawableSwitch.setSwitchOn(true);
            }
        }


    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void initTitle() {
        iv_left  = findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("添加收货地址");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("保存");
    }

    private void initViews() {
        shr_name = findviewByid(R.id.shr_name);
        shr_phone = findviewByid(R.id.shr_phone);
        tv_edit_address = findviewByid(R.id.tv_edit_address);
        tv_edit_address.setVisibility(View.GONE);
        xi_address = findviewByid(R.id.xi_address);
        tv_edit_address.setOnClickListener(this);
        textview_address = findViewById(R.id.textview_address);

        textview_address.setText(province+city+district);
        xi_address.setText(detailed_address);
        shr_name.setText(delivename);
        shr_phone.setText(phone);

        drawableSwitch = (DrawableSwitch) findViewById(R.id.drawableSwitch);


        textview_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAddress();
            }
        });
        selectZt();

        tv_right.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //TODO 调用保存地址
                String address_z=textview_address.getText().toString().trim();
//                String province=null,city=null,county=null,town=null,village=null;
//                String address_z= "陕西省西安市未央区";
////                String regex="(?<province>[^省]+省|.+自治区)(?<city>[^自治州]+自治州|[^市]+市|[^盟]+盟|[^地区]+地区|.+区划)(?<county>[^市]+市|[^县]+县|[^旗]+旗|.+区)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
//                String regex="(?<province>[^省]+省|.+自治区)(?<city>[^自治州]+自治州|[^市]+市|[^盟]+盟|[^地区]+地区|.+区划)(?<county>[^市]+市|[^县]+县|[^旗]+旗|.+区)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
//                String regex2="((?<province>[^省]+省|.+自治区)|上海|北京|天津|重庆)(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区|.+镇|.+局)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
//                String regex3="([\\u4e00-\\u9fa5\\s]+)省([\\u4e00-\\u9fa5\\s]+)市([\\u4e00-\\u9fa5\\s]+)(区|$) ";
//                Pattern mm = Pattern.compile(regex3);
//                Log.i("mm","mm"+province);
//                Matcher bb=mm.matcher(address_z);
//                Log.i("bb","bb"+province);
//                while (bb.find()){
//                     province = bb.group();
//                     Log.i("province","province"+province);
//                }
//                Matcher m= Pattern.compile(regex).matcher(address_z);
//
//                Log.i("address_z","address_z"+address_z);
//                province = m.group("province");
//                Log.i("address_z","address_z"+m.group());
                String xxdizhi = xi_address.getText().toString().trim();
                String delivename = shr_name.getText().toString().trim();
                String  phone = shr_phone.getText().toString().trim();
                if(userid==null){
                    return;
                }
                if(delivename==null){
                    return;
                }
                if(phone==null){
                    return;
                }
                if(!province2.equals("")&&!city2.equals("")&&!district2.equals("")){
                    addAddress(userid,province2,city2,district2,xxdizhi,delivename,phone);
//                addAddress(userid,"陕西省","西安市","新城区","五路口民安大厦A座100层",delivename,phone);
                }else {
                   ToastHelper.show(AddSettingAddressActivity.this,"地址不能为空");
                }

            }
        });
    }

    private void selectZt() {
        drawableSwitch.setListener(new DrawableSwitch.MySwitchStateChangeListener(){
            @Override
            public void mySwitchStateChanged(boolean isSwitchOn){
                if(isSwitchOn==true){
                    str_isdefault2 = "0";
                }else {
                    str_isdefault2 = "1";
                }

            }
        });
    }
    /**
     * 选择地址
     */
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(AddSettingAddressActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("陕西省")
                .city("西安市")
                .district("新城区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(20)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                textview_address.setText(province.trim() + city.trim() + district.trim());
                province2=province.trim();
                city2=city.trim();
                district2=district.trim();
            }
        });
    }

    /**
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：添加地址
     */
    private void addAddress(String userid,String province,String city,String district,String detailed_address,String delivename,String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("province",province);
        map.put("city", city);
        map.put("district", district);
        map.put("detailed_address",detailed_address);
        map.put("delivename",delivename);
        map.put("phone",phone);
        map.put("isdefault", str_isdefault2);
        Log.i("str_isdefault2","str_isdefault2"+"="+userid+"="+province+"="+city+"="+district+"="+detailed_address+"="+delivename+"="+phone+"="+str_isdefault2);
        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/shop/addressController/addAddress.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    addSettingAddressEntity = gson.fromJson(result, AddSettingAddressEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (addSettingAddressEntity.getCode() == 200) {
                                ToastHelper.show(AddSettingAddressActivity.this, addSettingAddressEntity.getMsg());
                                AddSettingAddressActivity.this.finish();

                            } else {
                                ToastHelper.show(AddSettingAddressActivity.this, addSettingAddressEntity.getMsg());
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
     * Post请求
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：确认修改地址
     */
    private void editAddress(String userid,String province,String city,String district,String detailed_address,String delivename,String phone,String str_isdefault,String id) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("province",province);
        map.put("city", city);
        map.put("district", district);
        map.put("detailed_address",detailed_address);
        map.put("delivename",delivename);
        map.put("phone",phone);
        map.put("isdefault", str_isdefault);
        map.put("id", id);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "app/bas/user/editUserAddress.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dfsd", "dsfsd" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    addSettingAddressEntity = gson.fromJson(result, AddSettingAddressEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (addSettingAddressEntity.getCode() == 200) {
                                ToastHelper.show(AddSettingAddressActivity.this, addSettingAddressEntity.getMsg());
                                AddSettingAddressActivity.this.finish();

                            } else {
                                ToastHelper.show(AddSettingAddressActivity.this, addSettingAddressEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_edit_address:
                if (!str_isdefault2.equals("")){
                    String address_z=textview_address.getText().toString().trim();
                    String xxdizhi = xi_address.getText().toString().trim();
                    String delivename = shr_name.getText().toString().trim();
                    String  phone = shr_phone.getText().toString().trim();
                    editAddress(userid,province2,city2,district2,xxdizhi,delivename,phone,str_isdefault2,id);
                    Log.i("editAddress","editAddress"+"userid"+userid+"province"+province2+"city"+city2+"district"+district2+"detailed_address"+detailed_address+"delivename"+delivename+"phone"+phone+"str_isdefault2"+str_isdefault2+"id"+id);
                }

                break;
        }
    }
}