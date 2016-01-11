package com.example.maguoqing.androiddemo.activity;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.control.IntentManager;


public class MainActivity extends BaseActivity{

    @ViewId(R.id.btn_list_activity)
    private Button mButtonListActivity;

    @ViewId(R.id.btn_pinned_activity)
    private Button mButtonSpinnedActivity;

    @ViewId(R.id.btn_sticky_activity)
    private Button mButtonStickyActivity;

    private Context mContext;

    @Override
    protected int getActivityLayout() { return R.layout.activity_btn; }

    @Override
    protected void setListeners() {
        mButtonListActivity.setOnClickListener(this);
        mButtonSpinnedActivity.setOnClickListener(this);
        mButtonStickyActivity.setOnClickListener(this);
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_activity:
                IntentManager.startListActivity(mContext);
                break;
            case R.id.btn_pinned_activity:
                IntentManager.startPinnedActivity(mContext);
                break;
            case R.id.btn_sticky_activity:
                IntentManager.startScrollviewActivity(mContext);
                break;
        }
    }
}