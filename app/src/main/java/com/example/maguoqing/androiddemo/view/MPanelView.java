package com.example.maguoqing.androiddemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.maguoqing.androiddemo.R;

/**
 * Created by Guoqing on 2016/5/10.
 */
public class MPanelView extends View {

    private Direction mCurrentScrollDirection = Direction.NONE;
    private Direction mCurrentFlingDirection = Direction.NONE;
    private PointF mCurrentOrigin = new PointF(0f, 0f);

    private float mDistanceX = 0;
    private float mDistanceY = 0;

    private final String TAG = "MPanelView";

    private Context mContext;

    private int lineColor = getResources().getColor(R.color.answer_gray);
    private Paint redPaint;
    private Paint linePaint;
    private Paint timeTextPaint;
    private Paint courseTextPaint;
    private Paint coursePaint;
    private Paint courseDarkPaint;

    private GestureDetectorCompat mGestureDetector;
    private Scroller mScroller;

    private int totalHeight = 1000;
    private int lineHeight = 200;
    private int marginTop = 50;

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
            lineHeight = ta.getInt(R.styleable.MPanelView_line_height, lineHeight);
        } finally {
            ta.recycle();
        }
        init();
    }

    public void init() {
        totalHeight = lineHeight*24 + 100;

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
    }

    @Override
    public void onDraw(Canvas canvas) {
        changeLocation();

        drawTimeAndLines(canvas);
        drawCourses(canvas);
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
            canvas.drawText((i % 24 < 10 ? "0" : "") + i % 24 + ":00", 90, marginTop + 11 + lineHeight*i, timeTextPaint);
        }

        for (int i = 0; i <= 24; i++) {
            canvas.drawLine(100, marginTop + lineHeight*i, getWidth(), marginTop + lineHeight*i, linePaint);
        }
        canvas.drawLine(100, 200, getWidth(), 200, redPaint);
        canvas.drawCircle(120, 200, 10, redPaint);
    }

    public void drawCourses(Canvas canvas) {
        canvas.drawRect(new Rect(110, marginTop + 400, 120, marginTop + 600), courseDarkPaint);
        Rect courseRect = new Rect(120, marginTop + 400, getWidth(), marginTop + 600);
        canvas.drawRect(courseRect, coursePaint);
        canvas.drawText("新建事件", courseRect.left + 10, courseRect.top + 40, courseTextPaint);
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
}
