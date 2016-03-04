package com.example.maguoqing.androiddemo.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.annotation.ViewId;
import com.example.maguoqing.androiddemo.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by magq on 16/2/18.
 */
public class AccountFragment extends BaseFragment {

    @ViewId(R.id.text)
    private TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        textView.setText("Account");
    }

    @Override
    protected void setListeners() {

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(AccountFragment.class.getName());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(AccountFragment.class.getName());
    }
}
