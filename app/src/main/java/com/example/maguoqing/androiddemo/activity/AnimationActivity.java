package com.example.maguoqing.androiddemo.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;

/**
 * Created by magq on 16/2/20.
 */
public class AnimationActivity extends BaseActivity {

    @ViewId(R.id.btn_animation)
    private Button button;
    @ViewId(R.id.btn_point_animation)
    private Button buttonPoint;
    @ViewId(R.id.text_animation)
    private TextView textview;

    private Context mContext;

    @Override
    protected void setListeners() {
        button.setOnClickListener(this);
        buttonPoint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_animation:
                startAnimation();
                break;
            case R.id.btn_point_animation:
                Intent intent = new Intent();
                intent.setClass(mContext, AnimatorViewActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        mContext = this;
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_animation;
    }

    public void startAnimation() {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
//        animator.setDuration(5000);
//        animator.start();

//        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textview, "translationX", 0f - textview.getWidth(), 0f);
//        ObjectAnimator rotate = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
//        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.play(rotate).with(fadeInOut).after(moveIn);
//        animSet.setDuration(5000);
//        animSet.start();

        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.animator_text);
        animator.setTarget(textview);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Toast.makeText(mContext, "start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Toast.makeText(mContext, "finish", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
