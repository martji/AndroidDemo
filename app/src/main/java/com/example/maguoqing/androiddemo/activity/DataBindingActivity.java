package com.example.maguoqing.androiddemo.activity;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.adapter.InfoAdapter;
import com.example.maguoqing.androiddemo.databinding.ActivityDataBinding;
import com.example.maguoqing.androiddemo.utils.CustomDialogFragment;
import com.example.maguoqing.androiddemo.utils.ViewUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by magq on 16/6/21.
 */
public class DataBindingActivity extends AppCompatActivity {

    private Context mContext;

    private ActivityDataBinding binding;
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

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
        mContext = this;

        drawerLayout = binding.drawer;
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {}

            @Override
            public void onDrawerOpened(View view) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
            }

            @Override
            public void onDrawerStateChanged(int i) {}

            @Override
            public void onDrawerClosed(View view) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
            }
        });

        RecyclerView recyclerView = binding.recyclerView;
        mgr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mgr);
        adapter = new InfoAdapter(new InfoAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                if (index % 3 == 0) {
                    showDialog();
                } else if (index % 3 == 1) {
                    Dialog dialog = ViewUtils.createLoadingDialog(mContext);
                    dialog.show();
                } else {
                    CustomDialogFragment dialog = CustomDialogFragment.newInstance("Demo");
                    dialog.show(getFragmentManager(), "");
                }
            }
        });
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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isFirst) {
                            adapter.addData();
                            isFirst = false;
                            loading = false;
                        } else {
                            adapter.hideProgress();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, 3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        } else if (id == R.id.action_filter) {
            if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                drawerLayout.closeDrawers();
            } else{
                drawerLayout.openDrawer(GravityCompat.END);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rv_list);

        tvName.setText("SELECT LESSON");
        rvList.setLayoutManager(new LinearLayoutManager(this));
        InfoAdapter adapter = new InfoAdapter(false, new InfoAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                dialog.dismiss();
                Toast.makeText(mContext, "Lesson " + index, Toast.LENGTH_SHORT).show();
            }
        });
        rvList.setAdapter(adapter);

        dialog.setContentView(view);
        dialog.show();
    }
}
