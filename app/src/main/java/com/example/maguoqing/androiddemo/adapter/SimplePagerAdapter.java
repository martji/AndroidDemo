/**
 * Created by YuGang Yang on April 01, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.example.maguoqing.androiddemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.maguoqing.androiddemo.fragment.SimpleFragment;
import com.example.maguoqing.androiddemo.view.weekpager.adapter.WeekPagerAdapter;


public class SimplePagerAdapter extends WeekPagerAdapter {

  public SimplePagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override protected Fragment createFragmentPager(int position) {
    return SimpleFragment.newInstance(mDays.get(position));
  }
}
