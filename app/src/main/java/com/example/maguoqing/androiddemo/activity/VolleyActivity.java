package com.example.maguoqing.androiddemo.activity;


import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;

/**
 * Created by magq on 16/3/9.
 */
public class VolleyActivity extends BaseActivity {

    @ViewId(R.id.btn_load)
    private Button btnLoad;

    @ViewId(R.id.et_address)
    private EditText etAddress;

    @ViewId(R.id.tv_content)
    private WebView tvContent;

    private RequestQueue requestQueue;

    @Override
    protected void setListeners() {
        btnLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load:
                StringRequest stringRequest = new StringRequest(etAddress.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                tvContent.loadData(response, "text/html", "UTF-8");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                requestQueue.add(stringRequest);
                break;
        }
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_volley;
    }
}
