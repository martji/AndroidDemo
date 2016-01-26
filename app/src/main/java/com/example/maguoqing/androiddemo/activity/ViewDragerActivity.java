package com.example.maguoqing.androiddemo.activity;

import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;

/**
 * Created by magq on 16/1/26.
 */
public class ViewDragerActivity extends BaseActivity {

    @ViewId(R.id.tv_one)
    private TextView textView;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_view_drag;
    }
}
