package com.example.maguoqing.androiddemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.adapter.InfoAdapter;
import com.example.maguoqing.androiddemo.databinding.ActivityDataBinding;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by magq on 16/6/21.
 */
public class DataBindingActivity extends AppCompatActivity {

    private ActivityDataBinding binding;
    private Toolbar toolbar;

    private InfoAdapter adapter;
    private LinearLayoutManager mgr;
    private int visibleThreshold = 2;
    private boolean loading = false;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_data);

        if (findViewById(R.id.toolbar) != null){
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initView();
    }

    public void initView() {
        getSupportActionBar().setTitle("Data Binding");

        RecyclerView recyclerView = binding.recyclerView;
        mgr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mgr);
        adapter = new InfoAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mgr.getItemCount();
                int lastVisibleItem = mgr.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    loading = true;
                    loadMore();
                }
            }
        });
    }

    public void loadMore() {
        adapter.showProgress();
        adapter.notifyDataSetChanged();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isFirst) {
                            adapter.addData();
                            isFirst = false;
                        }
                        adapter.hideProgress();
                        adapter.notifyDataSetChanged();
                        loading = false;
                    }
                });
            }
        }, 3000);
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
