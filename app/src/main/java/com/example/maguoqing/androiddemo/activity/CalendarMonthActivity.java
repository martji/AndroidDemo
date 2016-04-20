package com.example.maguoqing.androiddemo.activity;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseFragmentActivity;
import com.example.maguoqing.androiddemo.fragment.CalendarFragment;
import com.example.maguoqing.androiddemo.utils.Utils;

import java.util.Calendar;

/**
 * Created by magq on 16/4/19.
 */
public class CalendarMonthActivity extends BaseFragmentActivity {

    @ViewId(R.id.vp_calendar_month)
    private ViewPager viewPager;
    @ViewId(R.id.tv_month)
    private TextView tvMonth;

    private String month;

    @Override
    protected void setListeners() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Calendar calendar = Utils.getSelectCalendar(position);
                month = calendar.get(Calendar.YEAR)
                        + "-"
                        + Utils.LeftPad_Tow_Zero(calendar.get(Calendar.MONTH) + 1);
                tvMonth.setText(month);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar_month;
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        viewPager.setAdapter(screenSlidePagerAdapter);
        viewPager.setCurrentItem(500);
        tvMonth = (TextView) this.findViewById(R.id.tv_month);
        month = Calendar.getInstance().get(Calendar.YEAR)
                + "-"
                + Utils.LeftPad_Tow_Zero(Calendar.getInstance().get(Calendar.MONTH) + 1);
        tvMonth.setText(month);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CalendarFragment.create(position);
        }

        @Override
        public int getCount() {
            return 1000;
        }
    }
}
