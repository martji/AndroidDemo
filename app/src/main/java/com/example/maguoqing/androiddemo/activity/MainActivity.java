package com.example.maguoqing.androiddemo.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.control.IntentManager;

import java.util.ArrayList;


public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @ViewId(R.id.lv_list)
    private ListView listView;

    private SwipeRefreshLayout mSwipeLayout;

    private Context mContext;

    @Override
    protected int getActivityLayout() { return R.layout.activity_main; }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimaryTransparent);
        mSwipeLayout.setDistanceToTriggerSync(300);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);

        ArrayList<String> list = new ArrayList<>();
        list.add("ListActivity");
        list.add("BottomTabActivity");
        list.add("PinnedSectionListActivity");
        list.add("ScrollviewActivity");
        list.add("StickyActivity");
        list.add("SwipeBackDemoActivity");
        list.add("AppBarDetailActivity");
        list.add("ViewDragHelperActivity");
        list.add("ServiceActivity");
        list.add("NotificationActivity");
        list.add("AnimationActivity");
        list.add("ViewTouchActivity");
        list.add("AudioPlayerActivity");
        list.add("SugarActivity");
        list.add("VolleyActivity");
        list.add("IJKPlayerActivity");
        list.add("CalendarMonthActivity");
        list.add("MPanelActivity");
        list.add("WeekPagerActivity");
        list.add("LoginActivity");
        list.add("RXJavaActivity");

        MAdapter adapter = new MAdapter(mContext, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentManager.startActivity(i, mContext);
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
                Toast.makeText(mContext, "Refresh succeed!", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    class MAdapter extends BaseAdapter {

        private ArrayList<String> data;
        private LayoutInflater mInflater;

        public MAdapter (Context context, ArrayList<String> data) {
            this.mInflater = LayoutInflater.from(context);
            setData(data);
        }

        public void setData(ArrayList<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = mInflater.inflate(R.layout.view_list_item, null);
                holder.textView = (TextView) view.findViewById(R.id.tv_aty_name);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.textView.setText(data.get(i));
            return view;
        }
    }

    class ViewHolder{
        TextView textView;
    }
}