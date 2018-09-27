package com.example.ouc.demo.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.Application.ObjApplication;
import com.example.ouc.demo.R;
import com.example.ouc.demo.ShareTypeActivity;
import com.example.ouc.demo.base.BaseActivity;
import com.example.ouc.demo.uitool.ShareBoard;
import com.example.ouc.demo.uitool.ShareBoardlistener;
import com.example.ouc.demo.uitool.SnsPlatform;
import com.example.ouc.demo.utils.TimeUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.android.utils.Logger;
import cn.jiguang.share.facebook.Facebook;
import cn.jiguang.share.facebook.messenger.FbMessenger;
import cn.jiguang.share.jchatpro.JChatPro;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.twitter.Twitter;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jiguang.share.weibo.SinaWeiboMessage;


public class AdvertisingVideoActivity extends BaseActivity {
    private TextView tv_name, tv_gold, tv_datelong;
    private Button timerText;
    private VideoView videoview;
    private ImageView iv_right;
    private TextView tv_back, tv_content;
    private String name;
    private String gold;
    private String videourl, timelong,content;





    private static final String TAG = "MainActivity";
    private int mAction = Platform.ACTION_SHARE;
    private ProgressDialog progressDialog;
    private ShareBoard mShareBoard;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(AdvertisingVideoActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advvideo);
        Bundle bundle = getIntent().getExtras();   //得到传过来的bundle
        if (bundle != null) {
            name = bundle.getString("name");//读出数据
            gold = bundle.getString("gold");//读出数据
            videourl = bundle.getString("videourl");//读出数据
            timelong = bundle.getString("timelong");//读出数据
            content = bundle.getString("content");
        }
        initTitle();
        initViews();
        timer.start();


    }
    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right=findviewByid(R.id.iv_right);
        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
        iv_right.setVisibility(View.GONE);
//        iv_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 点击分享
//            }
//        });
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisingVideoActivity.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("视频广告");
    }
    private void initViews() {
        tv_name = findviewByid(R.id.tv_name);
        tv_gold = findviewByid(R.id.tv_gold);
        tv_datelong = findviewByid(R.id.tv_datelong);
        timerText=findviewByid(R.id.timerText);
        timerText.setEnabled(false);
        tv_name.setText(name);
        tv_gold.setText("奖励金：￥"+gold);
        tv_datelong.setText("时长："+timelong);
        videoview = findviewByid(R.id.videoview);
        MediaController controller = new MediaController(this);//实例化控制器
        videoview.setVideoURI(Uri.parse(videourl));
        controller.setMediaPlayer(videoview);
        videoview.setMediaController(controller);
        videoview.getDuration();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });

        timerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAction = Platform.ACTION_SHARE;
                showBroadView();
            }
        });
        Log.i("duration", "duration" + videoview.getCurrentPosition());


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候");

        if(Build.VERSION.SDK_INT >= 23){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    private void showBroadView() {
        if (mShareBoard == null) {
            mShareBoard = new ShareBoard(this);
            List<String> platforms = JShareInterface.getPlatformList();
            if (platforms != null) {
                Iterator var2 = platforms.iterator();
                while (var2.hasNext()) {
                    String temp = (String) var2.next();
                    SnsPlatform snsPlatform = createSnsPlatform(temp);
                    mShareBoard.addPlatform(snsPlatform);
                }
            }
            mShareBoard.setShareboardclickCallback(mShareBoardlistener);
        }
        mShareBoard.show();
    }

    private ShareBoardlistener mShareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, String platform) {

            switch (mAction) {
                case Platform.ACTION_SHARE:
                    progressDialog.show();
                    //这里以分享链接为例
                    ShareParams shareParams = new ShareParams();
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setTitle(ShareTypeActivity.share_title);
                    shareParams.setTitle(name);
                    shareParams.setText(content);
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setUrl(ShareTypeActivity.share_url);
                    shareParams.setImagePath(ObjApplication.ImagePath);
                    JShareInterface.share(platform, shareParams, mShareListener);
                    break;
                default:
                    break;
            }
        }
    };

    private PlatActionListener mShareListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享成功";
                handler.sendMessage(message);
            }
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            Logger.e(TAG, "error:" + errorCode + ",msg:" + error);
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享失败:" + error.getMessage() + "***" + errorCode;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onCancel(Platform platform, int action) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享取消";
                handler.sendMessage(message);
            }
        }
    };



    public static SnsPlatform createSnsPlatform(String platformName) {
        String mShowWord = platformName;
        String mIcon = "";
        String mGrayIcon = "";
        String mKeyword = platformName;
        if (platformName.equals(Wechat.Name)) {
            mIcon = "jiguang_socialize_wechat";
            mGrayIcon = "jiguang_socialize_wechat";
            mShowWord = "jiguang_socialize_text_weixin_key";
        } else if (platformName.equals(WechatMoments.Name)) {
            mIcon = "jiguang_socialize_wxcircle";
            mGrayIcon = "jiguang_socialize_wxcircle";
            mShowWord = "jiguang_socialize_text_weixin_circle_key";

        } else if (platformName.equals(WechatFavorite.Name)) {
            mIcon = "jiguang_socialize_wxfavorite";
            mGrayIcon = "jiguang_socialize_wxfavorite";
            mShowWord = "jiguang_socialize_text_weixin_favorite_key";

        } else if (platformName.equals(SinaWeibo.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_key";
        } else if (platformName.equals(SinaWeiboMessage.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_msg_key";
        } else if (platformName.equals(QQ.Name)) {
            mIcon = "jiguang_socialize_qq";
            mGrayIcon = "jiguang_socialize_qq";
            mShowWord = "jiguang_socialize_text_qq_key";

        } else if (platformName.equals(QZone.Name)) {
            mIcon = "jiguang_socialize_qzone";
            mGrayIcon = "jiguang_socialize_qzone";
            mShowWord = "jiguang_socialize_text_qq_zone_key";
        } else if (platformName.equals(Facebook.Name)) {
            mIcon = "jiguang_socialize_facebook";
            mGrayIcon = "jiguang_socialize_facebook";
            mShowWord = "jiguang_socialize_text_facebook_key";
        } else if (platformName.equals(FbMessenger.Name)) {
            mIcon = "jiguang_socialize_messenger";
            mGrayIcon = "jiguang_socialize_messenger";
            mShowWord = "jiguang_socialize_text_messenger_key";
        }else if (Twitter.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_twitter";
            mGrayIcon = "jiguang_socialize_twitter";
            mShowWord = "jiguang_socialize_text_twitter_key";
        } else if (platformName.equals(JChatPro.Name)) {
            mShowWord = "jiguang_socialize_text_jchatpro_key";
        }
        return ShareBoard.createSnsPlatform(mShowWord, mKeyword, mIcon, mGrayIcon, 0);
    }



    private TextView vertifyView;
    private CountDownTimer timer = new CountDownTimer(20000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
                timerText.setText("观看"+millisUntilFinished/1000+"秒视频,领取奖励金");
                if((millisUntilFinished/1000)==1){
                    timerText.setBackgroundColor(Color.parseColor("#ff0000"));
                    timerText.setText("观看0秒视频,领取奖励金");
                    timerText.setEnabled(true);
                }

        }

        @Override
        public void onFinish() {
//            vertifyView.setEnabled(true);
//            vertifyView.setText("获取验证码");
        }
    };


}