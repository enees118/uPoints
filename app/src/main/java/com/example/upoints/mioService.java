package com.example.upoints;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;


import androidx.annotation.Nullable;

public class mioService extends Service {
    private String TAG="mioService";

    public static final String COUNTDOWN_BR="com.example.upoints";
    Intent intentt=new Intent(COUNTDOWN_BR);
    CountDownTimer timer=null;
    Intent classifica,menu;
    private boolean isRunning=false;
    private int n_gioc;
    private long mymillis;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intentt=new Intent(COUNTDOWN_BR);
        int sec=intent.getIntExtra("Input",20);
        Log.d(TAG,"Starting timer");
        timer=new CountDownTimer(((sec+1)*1000),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG,"Countdown rimanente:"+millisUntilFinished);
                intentt.putExtra("Count",millisUntilFinished);
                intentt.putExtra("End?",false);
                sendBroadcast(intentt);
            }

            @Override
            public void onFinish() {
                intentt.putExtra("Count",0);
                intentt.putExtra("End?",true);
                sendBroadcast(intentt);

                Log.d(TAG,"Timer finito!");
                stopSelf();

            }
        };
        timer.start();

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Starting timer");

    }


    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
