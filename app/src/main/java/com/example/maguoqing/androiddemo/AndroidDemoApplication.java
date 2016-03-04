package com.example.maguoqing.androiddemo;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by magq on 16/3/3.
 */
public class AndroidDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);
    }
}
