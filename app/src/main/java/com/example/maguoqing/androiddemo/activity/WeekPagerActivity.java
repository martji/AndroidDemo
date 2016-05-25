package com.example.maguoqing.androiddemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.adapter.SimplePagerAdapter;
import com.example.maguoqing.androiddemo.utils.ChineseCalendarGB;
import com.example.maguoqing.androiddemo.view.weekpager.adapter.WeekViewAdapter;
import com.example.maguoqing.androiddemo.view.weekpager.model.CalendarDay;
import com.example.maguoqing.androiddemo.view.weekpager.util.DayUtils;
import com.example.maguoqing.androiddemo.view.weekpager.view.WeekDayViewPager;
import com.example.maguoqing.androiddemo.view.weekpager.view.WeekRecyclerView;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tudou on 15-5-19.
 */
public class WeekPagerActivity extends ActionBarActivity implements WeekDayViewPager.DayScrollListener  {

  private final static String TAG = "WeekPagerActivity";

  @InjectView(R.id.view_pager)
  WeekDayViewPager mViewPagerContent;
  @InjectView(R.id.header_recycler_view)
  WeekRecyclerView mWeekRecyclerView;
  @InjectView(R.id.text_day_label)
  TextView mTextView;

  private SimplePagerAdapter mPagerAdapter;
  private WeekViewAdapter mWeekViewAdapter;
  private static final int OFFSCREEN_PAGE_LIMIT = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_week_pager);
    ButterKnife.inject(this);
    setUpPager();
    setUpData();
  }

  private void setUpPager() {
    mPagerAdapter = new SimplePagerAdapter(getSupportFragmentManager());
    mViewPagerContent.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
    mViewPagerContent.setAdapter(mPagerAdapter);
    mViewPagerContent.setWeekRecyclerView(mWeekRecyclerView);
    mViewPagerContent.setDayScrollListener(this);
    mWeekViewAdapter = new WeekViewAdapter(this, mViewPagerContent);
    mWeekViewAdapter.setTextNormalColor(getResources().getColor(android.R.color.darker_gray));
    mWeekRecyclerView.setAdapter(mWeekViewAdapter);
  }

  private void setUpData() {
    CalendarDay start = new CalendarDay(2016, 1, 1);
    CalendarDay end = new CalendarDay(2017, 12, 31);
    mWeekViewAdapter.setData(start, end, null);
    mPagerAdapter.setData(start, end);
    Calendar today = Calendar.getInstance();
    mViewPagerContent.setCurrentPosition(DayUtils.calculateDayPosition(mWeekViewAdapter.getFirstShowDay(),
            new CalendarDay(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DATE))));
  }

  @Override public void onDayPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    Calendar date = mPagerAdapter.getDatas().get(position).getCalendar();
    mTextView.setText(DayUtils.formatEnglishTime(date) + " " + ChineseCalendarGB.getChineseDay(date));
  }

  @Override public void onDayPageSelected(int position) {}

  @Override public void onDayPageScrollStateChanged(int state) {}

}
