package com.example.maguoqing.androiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Created by Guoqing on 2015/12/24.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        setControl();
        initControls();
    }

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
}
