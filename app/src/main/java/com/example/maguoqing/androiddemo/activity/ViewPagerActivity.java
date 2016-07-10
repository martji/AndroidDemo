package com.example.maguoqing.androiddemo.activity;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.*;
import android.support.v4.view.ViewPager;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.adapter.MyFragmentPagerAdapter;
import com.example.maguoqing.androiddemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magq on 16/7/10.
 */
public class ViewPagerActivity extends BaseActivity {

    @ViewId(R.id.tab_layout)
    private TabLayout tabLayout;
    @ViewId(R.id.viewpager)
    private ViewPager viewPager;

    MyFragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        fragmentPagerAdapter = new MyFragmentPagerAdapter(this, getSupportFragmentManager());

        List<String> data = new ArrayList<>();
        data.add("Chat Room");
        data.add("Notification");

        fragmentPagerAdapter.setData(data);

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_viewpager;
    }
}
