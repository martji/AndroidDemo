package com.example.maguoqing.androiddemo.activity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.wight.okhttp.OkHttp3Stack;
import com.example.maguoqing.androiddemo.wight.okhttp.PersistentCookieStore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
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
    private TextView tvContent;

    private RequestQueue requestQueue;

    private OkHttpClient client;

    private String url = "http://101.201.113.136:8080/api/account";

    private PersistentCookieStore cookieStore;
    private List<Cookie> cookieList = new ArrayList<>();
    private String token = "token";

    private boolean isLogin = false;

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
                StringRequest stringRequest = new StringRequest(url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                tvContent.setText(response);
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("X-CSRF-TOKEN", token);
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                requestQueue.add(stringRequest);

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
                        Log.d(TAG, "init: " + response.code() + response.body().string());
                    }
                });
                break;
            case R.id.btn_login:
                if (isLogin) {
                    return;
                }
                FormBody body = new FormBody.Builder()
                        .add("j_username", "admin")
                        .add("j_password", "admin")
                        .add("remember-me", "true")
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
                        Log.d(TAG, "login: " + response.code() + response.body().string());
                        if (response.code() == 200) {
                            isLogin = true;
                        }
                    }
                });
                break;
            case R.id.btn_upload:
                File file = new File("sdcard/DCIM/test.jpg");
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
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
                        Log.d(TAG, "upload: " + response.code() + response.body().string());
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
        cookieStore = new PersistentCookieStore(getApplicationContext());

        client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (cookies.size() > 0) {
                            for (Cookie cookie : cookies) {
                                if (cookie.name().contains("TOKEN")) {
                                    if (!token.equals(cookie.value())) {
                                        token = cookie.value();
                                        Log.d(TAG, "update token: " + token);
                                    }
                                    break;
                                }
                            }
                        }
                        if (!isLogin) {
                            cookieList = cookies;
                            for (Cookie item : cookies) {
                                cookieStore.add(url, item);
                            }
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        cookieList = cookieStore.get(url);
                        return cookieList;
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .build();

        requestQueue = Volley.newRequestQueue(this, new OkHttp3Stack(client));
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_volley;
    }
}
