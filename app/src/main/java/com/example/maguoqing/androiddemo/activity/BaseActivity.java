package com.example.maguoqing.androiddemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.maguoqing.androiddemo.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Created by Guoqing on 2015/12/24.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getActivityLayout();
        if (layoutId != -1) {
            setContentView(layoutId);
        }

        readIntent();
        if (findViewById(R.id.toolbar) != null){
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setControl();
        initControls();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void setListeners();

    protected abstract void readIntent();

    protected abstract void initControls();

    protected abstract int getActivityLayout();

    @Override
    public void onClick(View v) {

    }

    @Target({ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface ViewId {
        int value();
    }

    private void setControl() {
        Class clazz = this.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (!View.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                ViewId viewId = field.getAnnotation(ViewId.class);
                if (viewId == null) {
                    continue;
                }
                field.set(this, this.findViewById(viewId.value()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
