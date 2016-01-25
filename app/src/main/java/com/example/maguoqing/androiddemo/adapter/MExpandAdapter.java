package com.example.maguoqing.androiddemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.model.CourseItem;
import com.example.maguoqing.androiddemo.model.Lesson;

import java.util.ArrayList;

/**
 * Created by maguoqing on 2016/1/4.
 */
public class MExpandAdapter extends BaseExpandableListAdapter{

    private static final int[] COLORS = new int[] {
            Color.GREEN, Color.YELLOW,
            Color.BLUE, Color.RED };

    private final LayoutInflater mInflater;
    private Context context;

    private ArrayList<CourseItem> group = new ArrayList<>();
    private ArrayList<ArrayList<Lesson>> children = new ArrayList<>();

    public MExpandAdapter (Context context) {
        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
    }

    public void setData(ArrayList<CourseItem> courseItems) {
        this.group = courseItems;
        for (CourseItem courseItem : courseItems) {
            children.add(courseItem.getLessons());
        }
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.view_expand_item, null);
        }
        TextView courseName = (TextView) view.findViewById(R.id.name);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        courseName.setText(group.get(groupPosition).getCourseName());
        if (isExpanded) {
            imageView.setImageResource(R.drawable.unfold);
        } else {
            imageView.setImageResource(R.drawable.fold);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.view_custom_item, null);
        }
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        TextView lessonTitle = (TextView) view.findViewById(R.id.title);
        TextView teacherName = (TextView) view.findViewById(R.id.info);

        String name = children.get(groupPosition).get(childPosition).getTeacherName();
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(name.charAt(0)), COLORS[childPosition % COLORS.length]);
        image.setImageDrawable(drawable);
        lessonTitle.setText(children.get(groupPosition).get(childPosition).getLessonTitle());
        teacherName.setText(name);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
