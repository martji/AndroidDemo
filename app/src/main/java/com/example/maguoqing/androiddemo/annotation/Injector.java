package com.example.maguoqing.androiddemo.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 
 * 根据Annotation，绑定Activity相关layout，view，listener
 * 
 * @author jinqiang
 *
 */
public class Injector {

    /**
     * 注入绑定view
     * 
     * @param fieldOwner
     * @param activity
     */
    public static void inject(Object fieldOwner, Activity activity) {
        try {
            // 绑定View
            injectView(fieldOwner, activity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注入绑定view
     * 
     * @param fieldOwner
     * @param view
     */
    public static void inject(Object fieldOwner, View view) {
        try {
            // 绑定View
            injectView(fieldOwner, view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void injectView(Object fieldOwner, Object viewProvider) throws Exception {
        List<Field> fields = ReflectUtils.getFields(fieldOwner.getClass());
        for (Field field : fields) {
            ViewId viewId = field.getAnnotation(ViewId.class);
            if (viewId == null) {
                continue;
            }
            Object view = null;
            if (viewProvider instanceof Activity) {
                view = ((Activity) viewProvider).findViewById(viewId.value());
            } else if (viewProvider instanceof View) {
                view = ((View) viewProvider).findViewById(viewId.value());
            }
            if (view != null) {
                field.setAccessible(true);
                field.set(fieldOwner, view);
            }
        }
    }
}
