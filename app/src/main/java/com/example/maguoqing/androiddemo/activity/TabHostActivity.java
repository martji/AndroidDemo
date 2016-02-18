package com.example.maguoqing.androiddemo.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseFragmentActivity;
import com.example.maguoqing.androiddemo.fragment.AccountFragment;
import com.example.maguoqing.androiddemo.fragment.ExploreFragment;
import com.example.maguoqing.androiddemo.fragment.HomeFragment;
import com.example.maguoqing.androiddemo.view.FragmentTabHost;

/**
 * Created by magq on 16/2/18.
 */
public class TabHostActivity extends BaseFragmentActivity implements
        RadioGroup.OnCheckedChangeListener {

    @ViewId(R.id.tabhost)
    private FragmentTabHost mTabHost;

    @ViewId(R.id.tab_rg_menu)
    private RadioGroup tabGroup;

    @ViewId(R.id.tab_home)
    private RadioButton homeTab;

    @ViewId(R.id.tab_explore)
    private RadioButton exploreTab;

    @ViewId(R.id.tab_account)
    private RadioButton accountTab;

    private int curTabIndex = 0;
    private int preTabIndex = 0;

    private final Class<?>[] fragments = {HomeFragment.class, ExploreFragment.class, AccountFragment.class};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab_host;
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.lv_tab_content);
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
            mTabHost.addTab(tabSpec, fragments[i], null);
        }
    }

    @Override
    protected void setListeners() {
        tabGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.tab_home:
                mTabHost.setCurrentTab(0);
                curTabIndex = 0;
                break;
            case R.id.tab_explore:
                mTabHost.setCurrentTab(1);
                preTabIndex = curTabIndex;
                curTabIndex = 1;
                break;
            case R.id.tab_account:
                mTabHost.setCurrentTab(2);
                curTabIndex = 2;
                break;
        }
    }
}
