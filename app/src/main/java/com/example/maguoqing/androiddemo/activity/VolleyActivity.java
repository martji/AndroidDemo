package com.example.maguoqing.androiddemo.activity;


import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by magq on 16/3/9.
 */
public class VolleyActivity extends BaseActivity {

    private static String TAG = "VolleyActivity";

    @ViewId(R.id.btn_load)
    private Button btnLoad;
    @ViewId(R.id.btn_login)
    private Button btnLogin;
    @ViewId(R.id.btn_upload)
    private Button btnUpload;

    @ViewId(R.id.tv_content)
    private WebView tvContent;

    private RequestQueue requestQueue;

    private OkHttpClient client;

    private String url = "http://101.201.113.136:8080/api/account";

    private List<Cookie> cookieList = new ArrayList<>();
    private String token = "token";

    @Override
    protected void setListeners() {
        btnLoad.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Headers headers = new Headers.Builder()
                .add("X-CSRF-TOKEN", token)
                .build();

        switch (v.getId()) {
            case R.id.btn_load:
//                StringRequest stringRequest = new StringRequest(etAddress.getText().toString(),
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                tvContent.loadData(response, "text/html", "UTF-8");
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//                requestQueue.add(stringRequest);

                Request request = new Request.Builder()
                        .url(url)
                        .headers(headers)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        Log.d(TAG, response.code() + response.body().string());
                    }
                });
                break;
            case R.id.btn_login:
                FormBody body = new FormBody.Builder()
                        .add("j_username", "admin")
                        .add("j_password", "admin")
                        .add("remember", "true")
                        .add("submit", "Login")
                        .build();
                Request request0 = new Request.Builder()
                        .url("http://101.201.113.136:8080/api/authentication")
                        .headers(headers)
                        .post(body)
                        .build();
                Call call0 = client.newCall(request0);
                call0.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, response.code() + response.body().string());
                    }
                });
                break;
            case R.id.btn_upload:
                File file = new File("sdcard/DCIM/test.jpg");
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("hello", "android")
                        .addFormDataPart("photo", file.getName(), RequestBody.create(null, file))
                        .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""),
                                RequestBody.create(MediaType.parse("application/octet-stream"), file))
                        .build();

                Request request1 = new Request.Builder()
                        .url("http://101.201.113.136:8080/api/uploadFile")
                        .headers(headers)
                        .post(requestBody)
                        .build();
                Call call1 = client.newCall(request1);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, response.code() + response.body().string());
                    }
                });

                break;
        }
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        requestQueue = Volley.newRequestQueue(this);

        client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (cookies.size() > 0) {
                            for (Cookie cookie : cookies) {
                                if (cookie.name().contains("TOKEN")) {
                                    if (!token.equals(cookie.value())) {
                                        token = cookie.value();
                                        Log.d(TAG, token);
                                    }
                                    break;
                                }
                            }
                        }
                        cookieList = cookies;
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return cookieList;
                    }
                })
                .build();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_volley;
    }
}
