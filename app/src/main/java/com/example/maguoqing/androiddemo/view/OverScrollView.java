package com.example.maguoqing.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

public class OverScrollView extends ScrollView {

    public OverScrollView(Context context) {
        super(context);
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int lastEventY;
    private int moveflag = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int eventY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int yDistance = (int) getTranslationY();
                if (yDistance != 0 && listener != null) {
                    if (!listener.onOverScroll(yDistance, true)) { //only do this if listener returns false
                        animate().translationY(0)
                                .setDuration(0)
                                .setInterpolator(new DecelerateInterpolator(1))
                                .start();
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                lastEventY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() == 0) {
                    handleOverscroll(event, false);
                } else {
                    View view = getChildAt(getChildCount() - 1);
                    if (view.getHeight() <= (getHeight() + getScrollY())) {
                        handleOverscroll(event, true);
                    }
                }
                break;
        }

        if (getTranslationY() != 0) {
            return true;
        }
        return super.onTouchEvent(event);

    }

    public static interface OverScrollListener {
        public boolean onOverScroll(int yDistance, boolean isReleased);
    }

    private OverScrollListener listener;
    public void setOverScrollListener(OverScrollListener listener) {
        this.listener = listener;
    }

    private void handleOverscroll(MotionEvent ev, boolean isBottom) {
        int pointerCount = ev.getHistorySize();
        moveflag = 0;
        for (int p = 0; p < pointerCount; p++) {
            int historicalY = (int) ev.getHistoricalY(p);
            int yDistance = (historicalY - lastEventY) / 1;

            if ((isBottom && yDistance < 0) || (!isBottom && yDistance > 0)) {
                if (moveflag == 0) {
                    setTranslationY(yDistance);
                    moveflag = 1;
                }
                if (listener != null) listener.onOverScroll(yDistance, false);
            }
        }
    }
}
