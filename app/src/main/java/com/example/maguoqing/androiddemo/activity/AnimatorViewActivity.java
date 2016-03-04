package com.example.maguoqing.androiddemo.activity;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.animation.MyAnimView;
import com.example.maguoqing.androiddemo.base.BaseActivity;

/**
 * Created by magq on 16/2/21.
 */
public class AnimatorViewActivity extends BaseActivity {
    @ViewId(R.id.view)
    private MyAnimView view;

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
        return R.layout.activity_animator_view;
    }
}
