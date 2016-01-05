package com.example.maguoqing.androiddemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;


/**
 * Created by Yafei on 2015/5/6.
 */
public class ConfirmDialog extends Dialog {

    private TextView msgTv;

    private TextView positiveTv;

    private TextView negativeTv;

    private CustomOnClick positiveOnClick = null;

    private CustomOnClick negativeOnClick = null;

    private String msg = "";

    private String positiveMsg = "";

    private String negativeMsg = "";

    private boolean isPositiveColorSet = false;

    private boolean isPositiveColorRes = false;

    private int posColor = -1;

    private int negColor = -1;

    private boolean isNegativeColorSet = false;

    private boolean isNegativeColorRes = false;

    private boolean positiveSet = false;

    private boolean negativeSet = false;

    private View divider;

    private View dividerHorizontal;

    private Context mContext;

    private View.OnClickListener positveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (positiveOnClick !=null) {
                positiveOnClick.onClick();
            }
            dismiss();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (negativeOnClick != null){
                negativeOnClick.onClick();
            }
            dismiss();
        }
    };

    public ConfirmDialog(Context context) {
        super(context);
        mContext = context;
    }

    public ConfirmDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    protected ConfirmDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        msgTv = (TextView) findViewById(R.id.tv_msg);
        positiveTv = (TextView) findViewById(R.id.positive_btn);
        negativeTv = (TextView) findViewById(R.id.negative_btn);
        divider = findViewById(R.id.divider);
        dividerHorizontal =  findViewById(R.id.divider_horizontal);
        if (!TextUtils.isEmpty(msg)){
            msgTv.setText(msg);
        }
        if (!TextUtils.isEmpty(positiveMsg)){
            positiveTv.setText(positiveMsg);
        }
        if (!TextUtils.isEmpty(negativeMsg)){
            negativeTv.setText(negativeMsg);
        }
        positiveTv.setOnClickListener(positveListener);
        negativeTv.setOnClickListener(negativeListener);
        if (isPositiveColorSet){
            if (isPositiveColorRes){
                positiveTv.setTextColor(mContext.getResources().getColorStateList(R.color.btn_text_green_color_selector));
            } else {
                positiveTv.setTextColor(posColor);
            }
        }
        if (isNegativeColorSet){
            if (isNegativeColorRes){
                negativeTv.setTextColor(mContext.getResources().getColorStateList(R.color.btn_text_green_color_selector));
            } else {
                negativeTv.setTextColor(negColor);
            }
        }
        if (positiveSet && negativeSet){
            positiveTv.setVisibility(View.VISIBLE);
            negativeTv.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        } else if (positiveSet) {
            positiveTv.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            positiveTv.setLayoutParams(p);
            positiveTv.setBackgroundResource(R.drawable.btn_single_selector);
            negativeTv.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        } else if (negativeSet) {
            positiveTv.setVisibility(View.GONE);
            negativeTv.setVisibility(View.VISIBLE);
            divider.setVisibility(View.GONE);
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            negativeTv.setLayoutParams(p);
            negativeTv.setBackgroundResource(R.drawable.btn_single_selector);
        } else {
            positiveTv.setVisibility(View.GONE);
            negativeTv.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            dividerHorizontal.setVisibility(View.GONE);
        }
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setPositiveButton(String text, CustomOnClick positiveOnClick){
        this.positiveSet = true;
        this.positiveMsg = text;
        this.positiveOnClick = positiveOnClick;
    }

    public void setNegativeButton(String text, CustomOnClick negativeOnClick){
        this.negativeSet = true;
        this.negativeMsg = text;
        this.negativeOnClick = negativeOnClick;
    }

    public void setPositiveColorRes(@ColorRes int color){
        posColor = color;
        isPositiveColorSet = true;
        isPositiveColorRes = true;
    }

    public void setPositiveColor(int color){
        posColor = color;
        isPositiveColorSet = true;
    }

    public void setNegativeColorRes(@ColorRes int color){
        negColor = color;
        isNegativeColorSet = true;
        isNegativeColorRes = true;
    }

    public void setNegativeColor(int color){
        //negativeTv.setTextColor(color);
        negColor = color;
        isNegativeColorSet = true;
    }

    public interface CustomOnClick{
        void onClick();
    }

    @Override
    public void show() {
        super.show();
    }

}
