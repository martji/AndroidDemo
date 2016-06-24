package com.example.maguoqing.androiddemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.view.LVCircularRing;

/**
 * Created by magq on 16/6/24.
 */
public class ViewUtils {

    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);
        LVCircularRing lvCircularRing = (LVCircularRing) view.findViewById(R.id.lv_ring);
        lvCircularRing.startAnim();

        Dialog loadingDialog = new Dialog(context, R.style.DialogLoadingTheme);
        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }

}
