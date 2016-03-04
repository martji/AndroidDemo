package com.example.maguoqing.androiddemo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.maguoqing.androiddemo.R;
import com.umeng.analytics.MobclickAgent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public abstract class BaseFragmentActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId != -1) {
            setContentView(layoutId);
        }
        if (findViewById(R.id.toolbar) != null){
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        readIntent();
        setControl();
        initControls(savedInstanceState);
        setListeners();

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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * get layout id for fragment
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * read intent from last object
     *
     * @return
     */
    protected abstract void readIntent();

    /**
     * init base values
     *
     * @return
     */
    protected abstract void initControls(Bundle savedInstanceState);

    /**
     * set listener for fragment views
     */
    protected abstract void setListeners();
}
