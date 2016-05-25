package com.example.maguoqing.androiddemo.view.weekpager.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.maguoqing.androiddemo.view.weekpager.model.CalendarDay;


/**
 * Created by tudou on 15-5-1.
 */
public class WeekDayViewPager extends ViewPager implements WeekView.OnDayClickListener {

  private final static int PAGE_SCROLL_DELAY = 24;

  public interface DayScrollListener {
    public void onDayPageScrolled(int position, float positionOffset,
                                  int positionOffsetPixels);
    public void onDayPageSelected(int position);
    public void onDayPageScrollStateChanged(int state);
  }

  private RecyclerView mRecyclerView;
  private boolean isSmoothScroll = false;
  private DayScrollListener mDayScrollListener;

  public WeekDayViewPager(Context context) {
    this(context, null);
  }

  public WeekDayViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public void onDayClick(WeekView simpleMonthView, CalendarDay calendarDay, int position) {
    setCurrentItem(position);
  }

  public void setWeekRecyclerView(RecyclerView recyclerView) {
    mRecyclerView = recyclerView;
    updateListener();
  }

  public void setDayScrollListener(DayScrollListener dayScrollListener) {
    mDayScrollListener = dayScrollListener;
  }

  private void updateListener() {
    setOnPageChangeListener(new OnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset,
          int positionOffsetPixels) {
        onDayPaegScrolled(position, positionOffset, positionOffsetPixels);
        int i = 0;
        View child = mRecyclerView.getChildAt(i);

        if (child == null) return;
        while (child != null && child.getRight() <= 0) {
          child = mRecyclerView.getChildAt(++i);
        }
        ((WeekView) child).onViewPageScroll(position, positionOffset, positionOffsetPixels);
      }

      @Override public void onPageSelected(int position) {
        onDayPageSelected(position);
        if (isSmoothScroll) {
          mRecyclerView.smoothScrollToPosition(position / 7);
        } else {
          mRecyclerView.scrollToPosition(position / 7);
          isSmoothScroll = true;
        }
        for (int j = 0; j < mRecyclerView.getChildCount(); j++) {
          View week = mRecyclerView.getChildAt(j);
          if (week instanceof WeekView) {
            week.invalidate();
          }
        }
      }

      @Override public void onPageScrollStateChanged(int state) {
        onDayPageScrollStateChanged(state);
      }
    });
  }

  private void onDayPaegScrolled(int position, float positionOffset,
      int positionOffsetPixels) {
    if (mDayScrollListener != null) {
      mDayScrollListener.onDayPageScrolled(position, positionOffset, positionOffsetPixels);
    }
  }

  private void onDayPageSelected(int position) {
    if (mDayScrollListener != null) {
      mDayScrollListener.onDayPageSelected(position);
    }
  }

  private void onDayPageScrollStateChanged(int state) {
    if (mDayScrollListener != null) {
      mDayScrollListener.onDayPageScrollStateChanged(state);
    }
  }

  public void setCurrentPosition(final int item) {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        setCurrentItem(item);
      }
    }, PAGE_SCROLL_DELAY);
  }
}
