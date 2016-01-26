package com.example.maguoqing.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by magq on 16/1/26.
 */
public class MService extends Service {
    private final String TAG = this.getClass().getSimpleName();

    private MBinder binder = new MBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MBinder extends Binder {

        private final Intent intent;
        private int i;

        public MBinder() {
            this.i = 0;
            intent = new Intent("com.example.maguoqing.androiddemo");
            intent.setAction("updateSeekBar");
        }

        public void startTask() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "startTask");
                    while (i < 100) {
                        try {
                            Thread.sleep(100);
                            i++;
                            intent.putExtra("CurrentLoading", i);
                            sendBroadcast(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
