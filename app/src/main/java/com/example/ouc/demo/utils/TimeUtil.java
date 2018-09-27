package com.example.ouc.demo.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zero on 2015/7/21.
 */
public class TimeUtil {

    public interface ITimer {
        public void onCompelete();
        public void onPerSecond(int time);
    }

    Timer timer;
    private ITimer ITimer;
    private int time;

    public TimeUtil(ITimer ITimer, int time){
        this.ITimer = ITimer;
        this.time = time;
    }

    public void start(){
        if(timer == null){
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        }
    }

    public void stop(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0x01);
            if(time==0){
                handler.sendEmptyMessage(0x02);
            }
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x01:
                    time--;
                    ITimer.onPerSecond(time);
                    break;
                case 0x02:
                    ITimer.onCompelete();
                    break;
            }
        }
    };


}
