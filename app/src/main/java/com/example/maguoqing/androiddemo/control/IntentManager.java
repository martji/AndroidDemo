package com.example.maguoqing.androiddemo.control;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ViewDragHelper;

import com.example.maguoqing.androiddemo.activity.AnimationActivity;
import com.example.maguoqing.androiddemo.activity.AppBarDetailActivity;
import com.example.maguoqing.androiddemo.activity.AudioPlayerActivity;
import com.example.maguoqing.androiddemo.activity.BottomTabActivity;
import com.example.maguoqing.androiddemo.activity.ListActivity;
import com.example.maguoqing.androiddemo.activity.NotificationActivity;
import com.example.maguoqing.androiddemo.activity.PinnedSectionListActivity;
import com.example.maguoqing.androiddemo.activity.ScrollviewActivity;
import com.example.maguoqing.androiddemo.activity.ServiceActivity;
import com.example.maguoqing.androiddemo.activity.StickyActivity;
import com.example.maguoqing.androiddemo.activity.SugarActivity;
import com.example.maguoqing.androiddemo.activity.SwipeBackDemoActivity;
import com.example.maguoqing.androiddemo.activity.TabHostActivity;
import com.example.maguoqing.androiddemo.activity.ViewDragerActivity;
import com.example.maguoqing.androiddemo.activity.ViewTouchActivity;
import com.example.maguoqing.androiddemo.view.AudioPlayerView;

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

    public static void startAppBarActivity (Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AppBarDetailActivity.class);
        context.startActivity(intent);
    }

    public static void startBottomTabActivity (Context context) {
        Intent intent = new Intent();
        intent.setClass(context, TabHostActivity.class);
        context.startActivity(intent);
    }

    public static void startScrollviewActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ScrollviewActivity.class);
        context.startActivity(intent);
    }

    public static void startSwipeBackActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SwipeBackDemoActivity.class);
        context.startActivity(intent);
    }

    public static void startViewDragerActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ViewDragerActivity.class);
        context.startActivity(intent);
    }

    public static void startServiceActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ServiceActivity.class);
        context.startActivity(intent);
    }

    public static void startNotificationActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NotificationActivity.class);
        context.startActivity(intent);
    }

    public static void startAnimationActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AnimationActivity.class);
        context.startActivity(intent);
    }

    public static void startViewTouchActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ViewTouchActivity.class);
        context.startActivity(intent);
    }

    public static void startAudioPlayerActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AudioPlayerActivity.class);
        context.startActivity(intent);
    }

    public static void startSugarActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SugarActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(int i, Context context) {
        switch (i) {
            case 0:
                startListActivity(context);
                break;
            case 1:
                startBottomTabActivity(context);
                break;
            case 2:
                startPinnedActivity(context);
                break;
            case 3:
                startScrollviewActivity(context);
                break;
            case 4:
                startStickyActivity(context);
                break;
            case 5:
                startSwipeBackActivity(context);
                break;
            case 6:
                startAppBarActivity(context);
                break;
            case 7:
                startViewDragerActivity(context);
                break;
            case 8:
                startServiceActivity(context);
                break;
            case 9:
                startNotificationActivity(context);
                break;
            case 10:
                startAnimationActivity(context);
                break;
            case 11:
                startViewTouchActivity(context);
                break;
            case 12:
                startAudioPlayerActivity(context);
                break;
            case 13:
                startSugarActivity(context);
                break;
            default:
                break;
        }
    }
}
