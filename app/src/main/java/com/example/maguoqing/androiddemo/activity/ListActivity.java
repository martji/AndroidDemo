package com.example.maguoqing.androiddemo.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.adapter.MExpandAdapter;
import com.example.maguoqing.androiddemo.model.CourseItem;
import com.example.maguoqing.androiddemo.model.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;

public class ListActivity extends BaseActivity {

//    @ViewId(R.id.id_listview)
//    private ListView mRecyclerView;

    @ViewId(R.id.expand_list)
    private ExpandableListView expandableListView;

    private List<Map<String, Object>> mDatas = new ArrayList<>();
    private ArrayList<CourseItem> mCourses = new ArrayList<>();

    private Myadapter mAdapter;
    private MExpandAdapter mExpandAdapter;

    private Context mContext;
    final public static int  SIGN_UP = 1;

    private Handler mUIhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case SIGN_UP:
//                    ConfirmDialog confirmDialog = new ConfirmDialog(ListActivity.this, R.style.MyDialog);
//                    confirmDialog.setMsg(getString(R.string.start_list_activity));
//                    confirmDialog.setPositiveButton(mContext.getResources().getString(R.string.confirm), new ConfirmDialog.CustomOnClick() {
//                        @Override
//                        public void onClick() {
//                        }
//                    });
//                    confirmDialog.setNegativeButton(mContext.getResources().getString(R.string.cancel), null);
//                    confirmDialog.show();

                    final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
                    mMaterialDialog.setTitle("MaterialDialog");
                    mMaterialDialog.setMessage("Hello world!");
                    mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });
                    mMaterialDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });

                    mMaterialDialog.show();

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        mContext = this;
        initData();
//        mAdapter = new Myadapter(this);
//        mRecyclerView.setAdapter(mAdapter);

        mExpandAdapter = new MExpandAdapter(this);
        mExpandAdapter.setData(mCourses);
        expandableListView.setAdapter(mExpandAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mUIhandler.sendEmptyMessage(ListActivity.SIGN_UP);
                return false;
            }
        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_list;
    }

    protected void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "G1");
        map.put("info", "item1");
        TextDrawable drawable = TextDrawable.builder()
                .buildRect("M", Color.RED);
        map.put("img", drawable);
        mDatas.add(map);
        mDatas.add(map);
        mDatas.add(map);

        CourseItem courseItem = new CourseItem();
        courseItem.setCourseName("Test");
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("lesson1", "Mary"));
        lessons.add(new Lesson("lesson2", "Jack"));
        lessons.add(new Lesson("lesson3", "Tom"));
        lessons.add(new Lesson("lesson4", "Jack"));
        courseItem.setLessons(lessons);
        mCourses.add(courseItem);
        mCourses.add(courseItem);
    }

    class Myadapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public Myadapter (Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.view_custom_item, null);
                holder.img = (ImageView) convertView.findViewById(R.id.imageView);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.info = (TextView) convertView.findViewById(R.id.info);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.img.setImageDrawable((TextDrawable) mDatas.get(position).get("img"));
            holder.title.setText((String) mDatas.get(position).get("title"));
            holder.info.setText((String) mDatas.get(position).get("info"));

            return convertView;
        }
    }

    class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView info;
    }
}