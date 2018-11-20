package com.example.ouc.demo.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.ShareTypeActivity;
import com.example.ouc.demo.adapter.FragmentAdapter;
import com.example.ouc.demo.entity.ShareSuccessfulNoticeEntity;
import com.example.ouc.demo.entity.TaskOverEntity;
import com.example.ouc.demo.http.HttpUtils;
import com.example.ouc.demo.ui.fragment.AdvertFragment1;
import com.example.ouc.demo.ui.fragment.AdvertFragment2;
import com.example.ouc.demo.ui.fragment.AdvertFragment3;
import com.example.ouc.demo.uitool.ShareBoard;
import com.example.ouc.demo.uitool.ShareBoardlistener;
import com.example.ouc.demo.uitool.SnsPlatform;
import com.example.ouc.demo.utils.Constants;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.weigets.GuideView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//     <com.xiao.nicevideoplayer.NiceVideoPlayer
//             android:id="@+id/videoview"
//             android:layout_width="match_parent"
//             android:layout_height="wrap_content"
//             android:layout_marginBottom="8dp"
//             android:layout_marginTop="16dp"/>
public class AdvertisingVideoActivity2 extends FragmentActivity {
    private TextView tv_name, tv_gold, tv_datelong;
    private Button timerText;
    //    private CustomVideoView videoview;
//private VideoView videoview;
    private NiceVideoPlayer mNiceVideoPlayer;
    private ImageView iv_right;
    private TextView tv_back, tv_content;
    private String name;
    private String gold;
    private String videourl, timelong, content, shareUrl;
    private Gson gson = new Gson();
    private String id, taskid, cover;
    private ArrayList<ShareSuccessfulNoticeEntity.DataBean> sharedataBeansList2;
    private ShareSuccessfulNoticeEntity shareSuccessfulNoticeEntity;
    private String code;
    private TaskOverEntity taskOverEntity;
    private static final String TAG = "MainActivity";
    private int mAction = Platform.ACTION_SHARE;
    private ProgressDialog progressDialog;
    private ShareBoard mShareBoard;
    private ArrayList<Fragment> mFragmentList;
    private ViewPager mPageVp;
    private AdvertFragment1 advertFragment1;
    private AdvertFragment2 advertFragment2;
    private AdvertFragment3 advertFragment3;
    private RadioGroup mGroup_page;
    private RadioButton rbChat_page, rbContacts_page, rbDiscovery_page;
    //    private ImageView imageiew;
    private GuideView guideView;
    private GuideView guideView3;
    private GuideView guideView2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(AdvertisingVideoActivity2.this, toastMsg, Toast.LENGTH_SHORT).show();
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
            id = bundle.getString("id");//读出用户id
            name = bundle.getString("name");//读出数据
            gold = bundle.getString("gold");//读出数据
            videourl = bundle.getString("videourl");//读出数据
            shareUrl = bundle.getString("shareUrl");
            taskid = bundle.getString("taskid");
        }

        initViews();
        initView();
        initViewPager();
        initTitle();
        initVideo();
    }

    private void initVideo() {
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.videoview);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
//        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
//        videoUrl = Environment.getExternalStorageDirectory().getPath().concat("/办公室小野.mp4");
        mNiceVideoPlayer.setUp(videourl, null);
        Log.i("====videourl","====videourl"+videourl);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setLenght(98000);
        Glide.with(this)
                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
        mNiceVideoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
            }
        });
    }


    private void initViewPager() {
        advertFragment1 = new AdvertFragment1();
        advertFragment2 = new AdvertFragment2();
        advertFragment3 = new AdvertFragment3();
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(0, advertFragment1);
        mFragmentList.add(1, advertFragment2);
        mFragmentList.add(2, advertFragment3);
        //ViewPager设置适配器
        mPageVp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mFragmentList));
        //ViewPager显示第一个Fragment
        mPageVp.setCurrentItem(0);
        //ViewPager页面切换监听
        mPageVp.setOnPageChangeListener(new myOrderOnPageChangeListener());
    }

    private void initView() {
        mPageVp = (ViewPager) findViewById(R.id.id_page_vp);
        mGroup_page = (RadioGroup) findViewById(R.id.radiogroup_page);
        rbChat_page = (RadioButton) findViewById(R.id.rb_chat_page);
        rbChat_page.setText("详情");
        rbContacts_page = (RadioButton) findViewById(R.id.rb_contacts_page);
        rbContacts_page.setText("公众号");
        rbDiscovery_page = (RadioButton) findViewById(R.id.rb_discovery_page);
        rbDiscovery_page.setText("评论");
        //RadioGroup选中状态改变监听
        mGroup_page.setOnCheckedChangeListener(new myAdvCheckChangeListener());
        mPageVp.setOffscreenPageLimit(2);
    }


    /**
     * RadioButton切换Fragment
     */
    private class myAdvCheckChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_chat_page:
                    //ViewPager显示第一个Fragment且关闭页面切换动画效果
                    mPageVp.setCurrentItem(0);
                    rbChat_page.setChecked(true);
                    rbContacts_page.setChecked(false);
                    rbDiscovery_page.setChecked(false);

                    break;
                case R.id.rb_contacts_page:
                    mPageVp.setCurrentItem(1);
                    rbChat_page.setChecked(false);
                    rbContacts_page.setChecked(true);
                    rbDiscovery_page.setChecked(false);

                    break;
                case R.id.rb_discovery_page:
                    mPageVp.setCurrentItem(2);
                    rbChat_page.setChecked(false);
                    rbContacts_page.setChecked(false);
                    rbDiscovery_page.setChecked(true);
                    break;

            }
        }
    }

    /**
     * ViewPager切换Fragment,RadioGroup做相应变化
     */
    private class myOrderOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mGroup_page.check(R.id.rb_chat_page);
                    break;
                case 1:
                    mGroup_page.check(R.id.rb_contacts_page);
                    break;
                case 2:
                    mGroup_page.check(R.id.rb_discovery_page);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initMengBan() {
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.img_new_task_guide);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);
        /**
         * 下边为显示引导蒙版，为了防止和购物popwindow冲突，暂时先注销
         */
//        guideView = GuideView.Builder
//                .newInstance(this)
//                .setTargetView(iv_right)//设置目标
//                .setCustomGuideView(iv)
//                .setDirction(GuideView.Direction.LEFT_BOTTOM)
//                .setShape(GuideView.MyShape.CIRCULAR)   // 设置圆形显示区域，
//                .setBgColor(getResources().getColor(R.color.shadow))
//                .setOnclickListener(new GuideView.OnClickCallback() {
//                    @Override
//                    public void onClickedGuideView() {
//                        guideView.hide();
//                    }
//                })
//                .build();
//        guideView.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (id == null) {
            return;
        }
        if (taskid == null) {
            return;
        }
        TaskOver(id, taskid);
    }

    private void initTitle() {
        tv_back = findViewById(R.id.tv_left);
        iv_right = findViewById(R.id.iv_right);
        Glide.with(this).load(R.drawable.icon_fx).into(iv_right);
        iv_right.setVisibility(View.VISIBLE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvertisingVideoActivity2.this.finish();
            }
        });
        tv_content = findViewById(R.id.tv_title);
        tv_content.setText("视频广告");
    }

    @SuppressLint("NewApi")
    private void initViews() {
        tv_name = findViewById(R.id.tv_name);
        tv_gold = findViewById(R.id.tv_gold);
        tv_datelong = findViewById(R.id.tv_datelong);
        timerText = findViewById(R.id.timerText);
        timerText.setEnabled(false);
        tv_name.setText(name);
        tv_gold.setText("奖励金：￥" + gold);
//        videoview = findViewById(R.id.videoview);
//
//
//        videoview.setZOrderMediaOverlay(true);
//        MediaController controller = new MediaController(this);//实例化控制器
////        videoview.setVideoURI(Uri.parse(videourl));
//        videoview.setVideoPath(videourl);
//        controller.setMediaPlayer(videoview);
//        videoview.setMediaController(controller);
//        videoview.setZOrderOnTop(true);
//        videoview.getDuration();
//        videoview.requestFocus();
//
//        /**
//         * 开始播放
//         */
//        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.start();
//                mediaPlayer.setLooping(false);
////                ToastHelper.show(AdvertisingVideoActivity.this,"开始播放...");
//                timer.start();
////                imageiew.setVisibility(View.GONE);
//            }
//        });
//        /**
//         * 缓冲播放
//         */
//        videoview.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//            @Override
//            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
//                return true;
//            }
//        });
//        /**
//         * 结束播放
//         */
//        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                // 播放结束后的动作
//                if(id==null){
//                    return;
//                }
//                if(taskid==null){
//                    return;
//                }
//                TaskOver(id,taskid);
//
//            }
//        });

        timerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mAction = Platform.ACTION_SHARE;
//                showBroadView();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候");

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (videoview.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        videoview.releaseAllVideos();
//                        // 播放结束后的动作
//                if(id==null){
//                    return;
//                }
//                if(taskid==null){
//                    return;
//                }
//                TaskOver(id,taskid);
//    }

    // 获取视频缩略图
    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        Toast.makeText(AdvertisingVideoActivity2.this, "视频播放结束", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
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
                    shareParams.setText(name);
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setUrl(shareUrl);
//                    shareParams.setUrl("http://news.cctv.com/2018/10/15/ARTI2lXQgl2499SVUnlLL0p7181015.shtml");
//                    shareParams.setImagePath(ObjApplication.ImagePath);
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
                getShareSuccessfulNotice();
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
        } else if (Twitter.Name.equals(platformName)) {
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
        public void onTick(final long millisUntilFinished) {
            timerText.setText("倒计时:" + millisUntilFinished / 1000);
            final String time = String.valueOf(millisUntilFinished / 1000);
            if ((millisUntilFinished / 1000) == 1) {
                timerText.setBackgroundColor(Color.parseColor("#ff0000"));
                timerText.setText("倒计时:0秒");
                timerText.setEnabled(true);
                iv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 点击分享
                        mAction = Platform.ACTION_SHARE;
                        showBroadView();
//                                ToastHelper.show(AdvertisingVideoActivity.this,time+"秒后可分享");

                    }
                });
            }

        }

        @Override
        public void onFinish() {
//            vertifyView.setEnabled(true);
//            vertifyView.setText("获取验证码");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        initMengBan();
    }

    private void getShareSuccessfulNotice() {
        /**c
         * Get请求
         * 参数一：请求Ur
         * 参数二：请求回调
         */
        String url = Constants.SERVER_BASE_URL + "system/sys/SysMemUserTaskController/updateShare.action?userid=" + id + "&taskid=" + taskid;
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
//                    progersssDialog.dismiss();
                    Log.i("result", "resultCode:" + result);
                    shareSuccessfulNoticeEntity = gson.fromJson(result, ShareSuccessfulNoticeEntity.class);
                    Type listType2 = new TypeToken<ArrayList<ShareSuccessfulNoticeEntity.DataBean>>() {
                    }.getType();//TypeToken内的泛型就是Json数据中的类型
                    sharedataBeansList2 = gson.fromJson(gson.toJson(shareSuccessfulNoticeEntity.getData()), listType2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            code = String.valueOf(shareSuccessfulNoticeEntity.getCode());
                            Log.i("dataBeansList2", "dataBeansList2" + sharedataBeansList2);
                            if (code.equals("200")) {
                                ToastHelper.show(AdvertisingVideoActivity2.this, "" + shareSuccessfulNoticeEntity.getMsg() + "");
                            }
//                            initListData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("e", "e" + e);
                }

            }
        });
    }


    /**
     * Post请求  完成任务接口
     * 参数一：请求Url
     * 参数二：请求的键值对
     * 参数三：请求回调
     */
    private void TaskOver(String id, String taskid) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("taskid", String.valueOf(taskid));

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "system/sys/SysMemIntegralController/saveIntegral.action", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result", "result:" + result);
                    taskOverEntity = gson.fromJson(result, TaskOverEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (taskOverEntity.getCode() == 200) {
                                ToastHelper.show(AdvertisingVideoActivity2.this, taskOverEntity.getMsg());
                            } else {
                                ToastHelper.show(AdvertisingVideoActivity2.this, taskOverEntity.getMsg());
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