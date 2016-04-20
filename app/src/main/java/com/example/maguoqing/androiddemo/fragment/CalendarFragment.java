package com.example.maguoqing.androiddemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.adapter.CalendarAdapter;

public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private CalendarAdapter adapter;

    private int mPageNumber;

    public static Fragment create(int pageNumber) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.mPageNumber = pageNumber;
        return fragment;
    }

    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_calendar_month, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_calendar_month);
        GridLayoutManager mgr=new GridLayoutManager(getActivity(), 7);
        recyclerView.setLayoutManager(mgr);
        adapter = new CalendarAdapter();
        adapter.setPageNumber(mPageNumber);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
