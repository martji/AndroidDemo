package com.example.maguoqing.androiddemo.activity;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.control.IntentManager;

/**
 * Created by Guoqing on 2016/6/24.
 */
public class LoginActivity extends BaseActivity {

    private Context mContext;

    @ViewId(R.id.tl_name)
    private TextInputLayout tlName;
    @ViewId(R.id.tt_name)
    private TextInputEditText ttName;

    @ViewId(R.id.tl_pwd)
    private TextInputLayout tlPwd;
    @ViewId(R.id.tt_pwd)
    private TextInputEditText ttPwd;

    @ViewId(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void setListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tlName.getEditText().getText().toString();
                String password = tlPwd.getEditText().getText().toString();

                if (username.equals(password)) {
                    IntentManager.startDataBindingActivity(mContext);
                } else {
                    tlPwd.setErrorEnabled(true);
                    tlPwd.setError("wrong password");
                }
            }
        });

        ttPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tlPwd.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        return R.layout.activity_login;
    }
}
