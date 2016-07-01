package com.example.maguoqing.androiddemo.activity;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by magq on 16/6/29.
 */
public class RXActivity extends BaseActivity {

    @ViewId(R.id.btn_start)
    private Button btnStart;

    @ViewId(R.id.tv_message)
    private TextView tvMessage;

    private Context mContext;

    @Override
    protected void setListeners() {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        });
    }

    public void click() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("RXJava");
            }
        }).subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "HELLO" + s.toUpperCase();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tvMessage.setText(s);
                    }
                });
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
        return R.layout.activity_rxjava;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
