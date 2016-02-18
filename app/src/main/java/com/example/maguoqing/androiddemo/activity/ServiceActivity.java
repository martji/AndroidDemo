package com.example.maguoqing.androiddemo.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.service.MService;

/**
 * Created by magq on 16/1/26.
 */
public class ServiceActivity extends BaseActivity {
    @ViewId(R.id.btn_start_service)
    private Button btn_start_service;
    @ViewId(R.id.btn_finish_service)
    private Button btn_finish_service;
    @ViewId(R.id.btn_bind_service)
    private Button btn_bind_service;
    @ViewId(R.id.btn_unbind_service)
    private Button btn_unbind_service;
    @ViewId(R.id.seekBar)
    private SeekBar seekBar;
    @ViewId(R.id.text_percent)
    private TextView textPercent;

    private Context mContext;
    private final String MYACTION = "updateSeekBar";

    private MService.MBinder mBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = (MService.MBinder) iBinder;
            mBinder.startTask();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private int curLoad = 0;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MYACTION.equals(intent.getAction())){
                curLoad = intent.getIntExtra("CurrentLoading", 0);
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        }
    };


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(curLoad < 0) {
                return;
            }
            seekBar.setProgress(curLoad);
            textPercent.setText(curLoad+"%");
            if (curLoad == 100) {
                unbindService(serviceConnection);
                Toast.makeText(mContext, "Download finished!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MYACTION);
        registerReceiver(receiver, filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

        @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_service:
                Intent startServiceIntent = new Intent(this, MService.class);
                startService(startServiceIntent);
                break;
            case R.id.btn_finish_service:
                Intent stopServiceIntent = new Intent(this, MService.class);
                stopService(stopServiceIntent);
                break;
            case R.id.btn_bind_service:
                Intent bindIntent = new Intent(this, MService.class);
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                unbindService(serviceConnection);
                break;
            default:
                break;
        }
    }

    @Override
    protected void setListeners() {
        btn_start_service.setOnClickListener(this);
        btn_finish_service.setOnClickListener(this);
        btn_bind_service.setOnClickListener(this);
        btn_unbind_service.setOnClickListener(this);
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        mContext = this;
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_service;
    }
}
