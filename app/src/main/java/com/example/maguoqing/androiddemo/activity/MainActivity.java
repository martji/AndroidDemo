package com.example.maguoqing.androiddemo.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.control.IntentManager;

import java.util.ArrayList;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @ViewId(R.id.id_listview)
    private ListView listView;

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

        ArrayList<String> list = new ArrayList<>();
        list.add(ListActivity.class.getSimpleName());
        list.add(BottomTabActivity.class.getSimpleName());
        list.add(PinnedSectionListActivity.class.getSimpleName());
        list.add(ScrollviewActivity.class.getSimpleName());
        list.add(StickyActivity.class.getSimpleName());
        list.add(SwipeBackDemoActivity.class.getSimpleName());

        MAdapter adapter = new MAdapter(mContext, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        IntentManager.startActivity(i, mContext);
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
            return null;
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
                view = mInflater.inflate(R.layout.view_item, null);
                holder.textView = (TextView) view.findViewById(R.id.id_num);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (holder != null) {
                holder.textView.setText(data.get(i));
                holder.textView.setBackgroundColor(Color.WHITE);
            }

            return view;
        }
    }

    class ViewHolder{
        TextView textView;
    }
}