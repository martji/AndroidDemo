package com.example.maguoqing.androiddemo.control;

import android.content.Context;
import android.content.Intent;

import com.example.maguoqing.androiddemo.activity.ListActivity;
import com.example.maguoqing.androiddemo.activity.PinnedSectionListActivity;
import com.example.maguoqing.androiddemo.activity.StickyActivity;

/**
 * Created by Guoqing on 2015/12/18.
 */
public class IntentManager {

    public static void startListActivity (Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ListActivity.class);
        context.startActivity(intent);
    }

    public static void startPinnedActivity (Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PinnedSectionListActivity.class);
        context.startActivity(intent);
    }

    public static void startStickyActivity (Context context) {
        Intent intent = new Intent();
        intent.setClass(context, StickyActivity.class);
        context.startActivity(intent);
    }
}
