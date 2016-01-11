package com.example.maguoqing.androiddemo.activity;

import android.os.Handler;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.view.OverScrollView;

/**
 * Created by maguoqing on 2016/1/11.
 */
public class ScrollviewActivity extends BaseActivity {

    @ViewId(R.id.detail_title)
    private TextView titleTextView;
    @ViewId(R.id.detail_body)
    private TextView detailBodyTextView;
    @ViewId(R.id.overscroll_view)
    private OverScrollView scrollView;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        titleTextView.setText("Hello World");

        scrollView.setOverScrollListener(new OverScrollView.OverScrollListener() {
            int translationThreshold = 120;
            @Override
            public boolean onOverScroll(int yDistance, boolean isReleased) {
                int cp = 1;
                if (yDistance < 0) {
                    cp = 3;
                }
                if (Math.abs(yDistance) > translationThreshold*cp) {
                    if (isReleased) {
                        onBackPressed();
                        return true;
                    }
                }
                return false;
            }
        });

        detailBodyTextView.setAlpha(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                detailBodyTextView.animate().alpha(1).start();
            }
        }, 500);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_scrollview;
    }
}
