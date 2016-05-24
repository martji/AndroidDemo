/**
 * Created by YuGang Yang on April 01, 2015.
 * Copyright 2007-2015 Laputapp.com. All rights reserved.
 */
package com.example.maguoqing.androiddemo.view.weekpager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.maguoqing.androiddemo.view.weekpager.model.CalendarDay;
import com.example.maguoqing.androiddemo.view.weekpager.util.DayUtils;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class WeekPagerAdapter extends FragmentPagerAdapter {

  public CalendarDay mStartDay;
  public CalendarDay mEndDay;

  public ArrayList<CalendarDay> mDays;
  public int mCount;

  public WeekPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void setData(CalendarDay startDay, CalendarDay endDay) {
    mStartDay = startDay;
    mEndDay = endDay;
    mCount = DayUtils.calculateWeekCount(mStartDay, mEndDay) * DayUtils.DAY_IN_WEEK;
    mDays = new ArrayList<>(mCount);
    createWeekCalendardays();
    notifyDataSetChanged();
  }

  private void createWeekCalendardays() {
    mDays.clear();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(DayUtils.calculateFirstShowDay(mStartDay).getTime());
    for (int i = 0; i < mCount; i++) {
      mDays.add(new CalendarDay(calendar));
      calendar.roll(Calendar.DAY_OF_YEAR, 1);
    }
  }

  @Override public Fragment getItem(int position) {
    return createFragmentPager(position);
  }

  protected abstract Fragment createFragmentPager(int position);

  @Override public int getCount() {
    return mCount;
  }

  public ArrayList<CalendarDay> getDatas() {
    return mDays;
  }
}
