package com.example.maguoqing.androiddemo.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.service.MDownloadTask;

/**
 * Created by magq on 16/1/26.
 */
public class NotificationActivity extends BaseActivity {

    @ViewId(R.id.btn_simple_notification)
    private Button btnSimpleNotification;
    @ViewId(R.id.btn_clear_notification)
    private Button btnClearNotification;
    @ViewId(R.id.btn_progress_notification)
    private Button btnProgressNotification;
    @ViewId(R.id.btn_start_download)
    private Button btnStartDownload;

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    private int progress = 0;
    private boolean indeterminate = false;

    private Context mContext;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_notification:
                showSimpleNotification();
                break;
            case R.id.btn_progress_notification:
                showProgressNotification();
                break;
            case R.id.btn_clear_notification:
                clearNotification();
                break;
            case R.id.btn_start_download:
                startDownload();
                break;
            default:
                break;
        }
    }

    @Override
    protected void setListeners() {
        btnSimpleNotification.setOnClickListener(this);
        btnClearNotification.setOnClickListener(this);
        btnProgressNotification.setOnClickListener(this);
        btnStartDownload.setOnClickListener(this);
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        mContext = this;
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_notification;
    }

    public void showSimpleNotification() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Hello World")
                .setContentText("This is a simple notification.")
                .setSmallIcon(R.drawable.ic_launcher);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public void showProgressNotification() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Download")
                .setContentText("Progress rate:")
                .setSmallIcon(R.drawable.ic_launcher);
        if(indeterminate){
            mBuilder.setProgress(0, 0, true);
        } else{
            mBuilder.setProgress(100, progress, false);
        }
        mNotificationManager.notify(1, mBuilder.build());
    }

    public void clearNotification() {
        mNotificationManager.cancelAll();
    }

    public void startDownload() {
        showProgressNotification();
        MDownloadTask asyncTask = new MDownloadTask(mNotificationManager, mBuilder, mContext);
        asyncTask.execute();
    }
}
