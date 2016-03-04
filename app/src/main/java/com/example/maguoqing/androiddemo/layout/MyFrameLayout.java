package com.example.maguoqing.androiddemo.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by magq on 16/2/22.
 */
public class MyFrameLayout extends FrameLayout {
    private Context mContext;
    private GestureDetector gestureDetector;

    private static String TAG = "Touch";

    private double lastFingerDis;

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        init();
    }

    public void init() {
        gestureDetector = new GestureDetector(mContext, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                Log.d(TAG, "onDown -- " + getActionName(motionEvent.getAction()));
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Log.d(TAG, "onSingleTapUp -- " + getActionName(motionEvent.getAction()));
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float x, float y) {
                Log.d(TAG, "onScroll -- " + getActionName(motionEvent.getAction()) +
                        " (" + x + ", " + y + ")");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float x, float y) {
                Log.d(TAG, "onFling -- " + getActionName(motionEvent.getAction()));
                return true;
            }
        });
        gestureDetector.setIsLongpressEnabled(true);
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                Log.d(TAG, "onSingleTapConfirmed -- " + getActionName(motionEvent.getAction()));
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                Log.d(TAG, "onDoubleTap -- " + getActionName(motionEvent.getAction()));
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                Log.d(TAG, "onDoubleTapEvent -- " + getActionName(motionEvent.getAction()));
                return true;
            }
        });
    }

    private String getActionName(int action) {
        String name = "";
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                name = "ACTION_DOWN";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                name = "ACTION_MOVE";
                break;
            }
            case MotionEvent.ACTION_UP: {
                name = "ACTION_UP";
                break;
            }
            default:
                name += action;
                break;
        }
        return name;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

        @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        Log.d(TAG, "onTouch -- " + getActionName(action));

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 2) {
                    lastFingerDis = distanceBetweenFingers(event);
                } else if (event.getPointerCount() == 1) {
                    Button button = (Button) getChildAt(0);
                    button.animate().translationX(event.getX()).translationY(event.getY()).setDuration(500);

                    TextView textView = (TextView) getChildAt(1);
                    textView.setText("(" + event.getX() + ", " + event.getY() + ")");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    double fingerDis = distanceBetweenFingers(event);
                    if (lastFingerDis == 0) {
                        lastFingerDis = fingerDis;
                    }
                    double scale = fingerDis / lastFingerDis;
                    Button button = (Button) getChildAt(0);
                    ViewGroup.LayoutParams  lp = button.getLayoutParams();
                    lp.width *= scale;
                    lp.height *= scale;
                    lp.width = lp.width <= 400 ? lp.width : 400;
                    lp.width = lp.width >= 10 ? lp.width : 10;
                    lp.height = lp.height <= 400 ? lp.height : 400;
                    lp.height = lp.height >= 10 ? lp.height : 10;
                    button.setLayoutParams(lp);
                    invalidate();
                }
        }

        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return false;
    }

    public double distanceBetweenFingers(MotionEvent event) {
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY = Math.abs(event.getY(0) - event.getY(1));
        return Math.sqrt(disX * disX + disY * disY);
    }
}
