package com.example.maguoqing.androiddemo.view.daypager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.maguoqing.androiddemo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Guoqing on 2016/5/10.
 */
public class MPanelView extends View {

    private final String TAG = "MPanelView";

    private Context mContext;

    private Direction mCurrentScrollDirection = Direction.NONE;
    private Direction mCurrentFlingDirection = Direction.NONE;
    private PointF mCurrentOrigin = new PointF(0f, 0f);
    private float mDistanceX = 0;
    private float mDistanceY = 0;

    private GestureDetectorCompat mGestureDetector;
    private Scroller mScroller;

    private int totalHeight = 1000;
    private int rowHeight = 200;
    private int mPaddingTop = 50;
    private int mTimeColumnWidth = 100;
    private int lineColor = getResources().getColor(R.color.answer_gray);
    private Paint redPaint;
    private Paint linePaint;
    private Paint timeTextPaint;
    private Paint courseTextPaint;
    private Paint coursePaint;
    private Paint courseDarkPaint;

    private Calendar mDate = null;
    private List<EventRect> mEventRects = null;
    private List<ViewEvent> mEvents = null;

    private EventClickListener mEventClickListener;
    private EventLongPressListener mEventLongPressListener;
    private EmptyViewClickListener mEmptyViewClickListener;
    private EmptyViewLongPressListener mEmptyViewLongPressListener;

    private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "mGestureListener / onDown");
            mScroller.forceFinished(true);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "mGestureListener / onScroll");
            if (mCurrentScrollDirection == Direction.NONE) {
                if (Math.abs(distanceX) < Math.abs(distanceY)) {
                    mCurrentFlingDirection = Direction.VERTICAL;
                    mCurrentScrollDirection = Direction.VERTICAL;
                }
            }
            mDistanceX = distanceX;
            mDistanceY = distanceY;
            mScroller.startScroll(0, (int)mCurrentOrigin.y, 0, (int)mDistanceY);
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "mGestureListener / onFling");
            mScroller.forceFinished(true);

            if (mCurrentFlingDirection == Direction.VERTICAL){
                mScroller.fling(0, (int) mCurrentOrigin.y, 0, (int) velocityY, 0, 0, -(totalHeight - getHeight()), 0);
            }

            ViewCompat.postInvalidateOnAnimation(MPanelView.this);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed");
            // If the tap was on an event then trigger the callback.
            if (mEventRects != null && mEventClickListener != null) {
                List<EventRect> reversedEventRects = mEventRects;
                Collections.reverse(reversedEventRects);
                for (EventRect event : reversedEventRects) {
                    if (event.rectF != null && e.getX() > event.rectF.left && e.getX() < event.rectF.right &&
                            e.getY() > event.rectF.top && e.getY() < event.rectF.bottom) {
                        mEventClickListener.onEventClick(event.originalEvent, event.rectF);
                        playSoundEffect(SoundEffectConstants.CLICK);
                        return super.onSingleTapConfirmed(e);
                    }
                }
            }

            // If the tap was on in an empty space, then trigger the callback.
            if (mEmptyViewClickListener != null && e.getX() > mTimeColumnWidth && e.getY() > mPaddingTop) {
                Calendar selectedTime = getTimeFromPoint(e.getY());
                if (selectedTime != null) {
                    playSoundEffect(SoundEffectConstants.CLICK);
                    mEmptyViewClickListener.onEmptyViewClicked(selectedTime);
                }
            }

            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);

            if (mEventLongPressListener != null && mEventRects != null) {
                List<EventRect> reversedEventRects = mEventRects;
                Collections.reverse(reversedEventRects);
                for (EventRect event : reversedEventRects) {
                    if (event.rectF != null && e.getX() > event.rectF.left && e.getX() < event.rectF.right &&
                            e.getY() > event.rectF.top && e.getY() < event.rectF.bottom) {
                        mEventLongPressListener.onEventLongPress(event.originalEvent, event.rectF);
                        performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        return;
                    }
                }
            }

            if (mEmptyViewClickListener != null && e.getX() > mTimeColumnWidth && e.getY() > mPaddingTop) {
                Calendar selectedTime = getTimeFromPoint(e.getY());
                if (selectedTime != null) {
                    performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    mEmptyViewLongPressListener.onEmptyViewLongPress(selectedTime);
                }
            }
        }
    };

    private enum Direction {
        NONE, VERTICAL
    }

    public MPanelView(Context context) {
        this(context, null);
    }

    public MPanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MPanelView);
        try {
            lineColor = ta.getColor(R.styleable.MPanelView_line_color, lineColor);
            rowHeight = ta.getInt(R.styleable.MPanelView_line_height, rowHeight);
        } finally {
            ta.recycle();
        }
        init();
    }

    public void init() {
        totalHeight = rowHeight *24 + 100;

        mGestureDetector = new GestureDetectorCompat(mContext, mGestureListener);
        mScroller = new Scroller(mContext);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(2);

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(1);

        timeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timeTextPaint.setTextAlign(Paint.Align.RIGHT);
        timeTextPaint.setStrokeWidth(3);
        timeTextPaint.setTextSize(28);
        timeTextPaint.setColor(Color.BLACK);

        coursePaint = new Paint();
        coursePaint.setColor(getResources().getColor(R.color.course_bg));
        coursePaint.setStrokeWidth(1);
        courseDarkPaint = new Paint();
        courseDarkPaint.setColor(getResources().getColor(R.color.course_bg_dark));
        courseDarkPaint.setStrokeWidth(1);
        courseTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        courseTextPaint.setTextAlign(Paint.Align.LEFT);
        courseTextPaint.setStrokeWidth(3);
        courseTextPaint.setTextSize(38);
        courseTextPaint.setColor(getResources().getColor(R.color.course_bg_dark));

        // test
        mEventRects = new ArrayList<>();
        RectF rectf = new RectF(100, 400, 1000, 600);
        EventRect event = new EventRect(null, null, rectf);
        mEventRects.add(event);
    }

    public void setmDate(Calendar date) {
        this.mDate = date;
    }

    @Override
    public void onDraw(Canvas canvas) {
        changeLocation();

        drawTimeAndLines(canvas);
        drawEvents(canvas);
    }

    public void changeLocation() {
        if (mCurrentScrollDirection == Direction.VERTICAL) {
            if (mCurrentOrigin.y - mDistanceY > 0) {
                mCurrentOrigin.y = 0;
            } else if (mCurrentOrigin.y - mDistanceY < -(totalHeight - getHeight())) {
                mCurrentOrigin.y = -(totalHeight - getHeight());
            } else {
                mCurrentOrigin.y -= mDistanceY;
            }
        }
    }

    public void drawTimeAndLines(Canvas canvas) {
        for (int i = 0; i <= 24; i++) {
            canvas.drawText((i % 24 < 10 ? "0" : "") + i % 24 + ":00", 90, mPaddingTop + 11 + rowHeight *i, timeTextPaint);
        }

        for (int i = 0; i <= 24; i++) {
            canvas.drawLine(mTimeColumnWidth, mPaddingTop + rowHeight *i, getWidth(), mPaddingTop + rowHeight *i, linePaint);
        }
        canvas.drawLine(mTimeColumnWidth, 200, getWidth(), 200, redPaint);
        canvas.drawCircle(120, 200, 10, redPaint);
    }

    public void drawEvents(Canvas canvas) {
        if (mEventRects != null) {
            for (EventRect eventRect : mEventRects) {
                drawSingleEvent(canvas, eventRect);
            }
        }
    }

    public void drawSingleEvent(Canvas canvas, EventRect eventRect) {
        RectF rectf = eventRect.rectF;
        canvas.drawRoundRect(new RectF(110, mPaddingTop + rectf.top, 120, mPaddingTop + rectf.bottom), 0, 0, courseDarkPaint);
        canvas.drawRoundRect(new RectF(120, mPaddingTop + rectf.top, getWidth(), mPaddingTop + rectf.bottom), 0, 0, coursePaint);
        canvas.drawText("新建事件", 130, mPaddingTop + rectf.top + 40, courseTextPaint);
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), -mScroller.getCurrY());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public interface EventClickListener {
        void onEventClick(ViewEvent event, RectF eventRect);
    }

    public interface EventLongPressListener {
        void onEventLongPress(ViewEvent event, RectF eventRect);
    }

    public interface EmptyViewClickListener {
        void onEmptyViewClicked(Calendar time);
    }

    public interface EmptyViewLongPressListener {
        void onEmptyViewLongPress(Calendar time);
    }

    public void setmEventClickListener(EventClickListener mEventClickListener) {
        this.mEventClickListener = mEventClickListener;
    }

    public void setmEventLongPressListener(EventLongPressListener mEventLongPressListener) {
        this.mEventLongPressListener = mEventLongPressListener;
    }

    public void setmEmptyViewClickListener(EmptyViewClickListener mEmptyViewClickListener) {
        this.mEmptyViewClickListener = mEmptyViewClickListener;
    }

    public void setmEmptyViewLongPressListener(EmptyViewLongPressListener mEmptyViewLongPressListener) {
        this.mEmptyViewLongPressListener = mEmptyViewLongPressListener;
    }

    private class EventRect {
        public ViewEvent event;
        public ViewEvent originalEvent;
        public RectF rectF;
        public float left;
        public float width;
        public float top;
        public float bottom;

        public EventRect(ViewEvent event, ViewEvent originalEvent, RectF rectF) {
            this.event = event;
            this.rectF = rectF;
            this.originalEvent = originalEvent;
        }
    }

    private Calendar getTimeFromPoint(float y){
        float pixels = y - mPaddingTop;
        Calendar day = today();
        int hour = (int)(pixels / rowHeight);
        int minute = (int) (60 * (pixels - hour * rowHeight) / rowHeight);
        day.setTime(mDate.getTime());
        day.add(Calendar.HOUR, hour);
        day.set(Calendar.MINUTE, minute);
        return day;
    }

    private Calendar today(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    private void getEvents() {
        if (mDate == null) {
            return;
        }
    }
}
