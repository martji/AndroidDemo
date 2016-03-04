package com.example.maguoqing.androiddemo.activity;

import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.layout.MyFrameLayout;

/**
 * Created by magq on 16/2/22.
 */
public class ViewTouchActivity extends BaseActivity {

    private MyFrameLayout frameLayout;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        frameLayout = (MyFrameLayout) findViewById(R.id.frame_layout);

        TextView textView = new TextView(this);
        textView.setText("Hello");
        frameLayout.addView(textView);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_view_touch;
    }
}
