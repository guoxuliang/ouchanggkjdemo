package com.example.ouc.demo.wxapi;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import com.example.ouc.demo.utils.Constants;
//import com.example.ouc.demo.utils.ToastHelper;
//import com.tencent.mm.sdk.constants.ConstantsAPI;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
///**
// * 微信支付回调 * * @author StriveStay * @date 2017/3/3
// */
//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//    }
//
//    @Override
//    public void onResp(BaseResp resp) { /* 0 支付成功 -1 发生错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。 -2 用户取消 发生场景：用户不支付了，点击取消，返回APP。 */
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            // 根据返回码
//            int code = resp.errCode;
//            switch (code) {
//                case 0:
//                    // 去本地确认支付结果
////                      EventBus.getDefault().post(new WxPayEvent(true));
//                    finish();
//                    break;
//                case -2:
//                    ToastHelper.show(this, "支付已取消");
//                    finish();
//                    break;
//                default:
//                    ToastHelper.show(this, "支付失败");
//                    finish();
//                    break;
//            }
//        }
//    }
//}

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.ui.activity.vip.UpgradeMembersActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private final String TAG = "WXPayEntryActivity";
    private TextView tv_back, tv_content;
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, null);
        api.handleIntent(getIntent(), this);
        initTitle();
    }
    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WXPayEntryActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("支付反馈");
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG,"errCode = " + resp.errCode);
        //最好依赖于商户后台的查询结果
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //如果返回-1，很大可能是因为应用签名的问题。用官方的工具生成
            //签名工具下载：https://open.weixin.qq.com/zh_CN/htmledition/res/dev/download/sdk/Gen_Signature_Android.apk
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
            builder.show();
        }
    }
}
