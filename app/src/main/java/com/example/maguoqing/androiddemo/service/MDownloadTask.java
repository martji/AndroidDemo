package com.example.maguoqing.androiddemo.service;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by magq on 16/1/26.
 */
public class MDownloadTask extends AsyncTask<Void, Integer, Boolean> {
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Context context;

    public MDownloadTask(NotificationManager notificationManager,
                         NotificationCompat.Builder builder, Context context) {
        super();
        this.notificationManager = notificationManager;
        this.builder = builder;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "start download", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int i = 0;
        while (i < 100) {
            try {
                Thread.sleep(100);
                i++;
                publishProgress(i);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        builder.setProgress(100, values[0], false);
        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            builder.setContentText("Download finish")
                    .setProgress(0, 0, false);
            notificationManager.notify(1, builder.build());
            Toast.makeText(context, "download finish", Toast.LENGTH_SHORT).show();
        } else {
            builder.setContentText("Download failed")
                    .setProgress(0, 0, false);
            notificationManager.notify(1, builder.build());
            Toast.makeText(context, "download failed", Toast.LENGTH_SHORT).show();
        }
    }
}
