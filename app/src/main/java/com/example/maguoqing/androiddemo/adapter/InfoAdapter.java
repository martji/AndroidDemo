package com.example.maguoqing.androiddemo.adapter;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.model.Lesson;

import java.util.ArrayList;

/**
 * Created by magq on 16/6/21.
 */
public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int NORMAL = 0;
    public final static int LOADING = 1;

    private boolean showProgressbar = true;
    private ArrayList<Lesson> data;
    private OnItemClick listener = null;

    public InfoAdapter() {
        data = new ArrayList<>();
        addData();
    }

    public InfoAdapter(OnItemClick listener) {
        this.listener = listener;
        data = new ArrayList<>();
        addData();
    }

    public InfoAdapter(boolean showProgressbar, OnItemClick listener) {
        this.showProgressbar = showProgressbar;
        this.listener = listener;
        data = new ArrayList<>();
        addData();
    }

    public void addData() {
        for (int i = 0; i < 10; i++) {
            data.add(new Lesson("Lesson" + i, "Jone"));
        }
    }

    public void hideProgress() {
        showProgressbar = false;
    }

    public void showProgress() {
        showProgressbar = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.viewholder_info_item, parent, false);
            return new InfoHolder(v);
        } else if (viewType == LOADING) {
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.viewholder_progressbar, parent, false);
            return new ProgressHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == NORMAL) {
            InfoHolder infoHolder = (InfoHolder) holder;
            infoHolder.tvLesson.setText(data.get(position).getLessonTitle());
            infoHolder.tvTeacher.setText(data.get(position).getTeacherName());
            infoHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.click(position);
                    }
                }
            });
        } else if (holder.getItemViewType() == LOADING) {
            ProgressHolder progressHolder = (ProgressHolder) holder;
            progressHolder.progressBar.show();
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + (showProgressbar ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < data.size()) {
            return NORMAL;
        } else {
            return LOADING;
        }
    }

    public class InfoHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView tvLesson;
        TextView tvTeacher;

        public InfoHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvLesson = (TextView) itemView.findViewById(R.id.tv_lesson);
            tvTeacher = (TextView) itemView.findViewById(R.id.tv_teacher);
        }
    }

    public class ProgressHolder extends RecyclerView.ViewHolder{

        ContentLoadingProgressBar progressBar;

        public ProgressHolder(View itemView) {
            super(itemView);
            progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    public interface OnItemClick {
        void click(int index);
    }
}
