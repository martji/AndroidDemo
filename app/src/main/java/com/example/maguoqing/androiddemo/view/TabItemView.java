package com.example.maguoqing.androiddemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;

/**
 * Created by maguoqing on 2016/1/7.
 */
public class TabItemView extends LinearLayout {

    private TextView tv;
    private ImageView image;

    private String text;
    private int imageResId;

    public TabItemView(Context context) {
        super(context);
        initView(context);
    }

    public TabItemView(Context context, String text, int imageResId) {
        super(context);
        initView(context);
        this.text = text;
        this.imageResId = imageResId;
        setParams();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_custom_tab, this);
        tv = (TextView) findViewById(R.id.textView);
        image = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void setParams() {
        this.tv.setText(text);
        this.image.setImageResource(imageResId);
    }

    public void setImage (int imageResId) {
        this.image.setImageResource(imageResId);
    }
}
