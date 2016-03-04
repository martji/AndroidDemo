package com.example.maguoqing.androiddemo.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by magq on 16/2/21.
 */
public class MyAnimView extends View {
    public static final float RADIUS = 50f;

    private Point currentPoint;
    private Paint mPaint;

    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    public void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    public void startAnimation() {
        Point startPoint = new Point(getWidth() / 2, RADIUS);
        Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
        final ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPoint = (Point) animator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(5000);
        animator.start();
    }
}
