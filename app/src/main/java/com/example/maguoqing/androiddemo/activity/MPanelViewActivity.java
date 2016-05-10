package com.example.maguoqing.androiddemo.activity;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.view.MPanelView;

/**
 * Created by Guoqing on 2016/5/10.
 */
public class MPanelViewActivity extends BaseActivity {

    @ViewId(R.id.pv_test)
    private MPanelView mPanelView;

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
        return R.layout.activity_mpanelview;
    }
}
