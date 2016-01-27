package com.example.maguoqing.androiddemo.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
    @ViewId(R.id.btn_back_notification)
    private Button btnBackNotification;
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
            case R.id.btn_back_notification:
                showNotification();
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
        btnBackNotification.setOnClickListener(this);
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
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(0, notification);
    }

    public void showNotification() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Hello World")
                .setContentText("This is a simple notification.")
                .setSmallIcon(R.drawable.ic_launcher);
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent backIntent = new Intent(this, MainActivity.class);
//        Intent intent = new Intent(this, NotificationActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
//                new Intent[] {backIntent, intent}, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(0, notification);
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
