package com.example.maguoqing.androiddemo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.maguoqing.androiddemo.R;

import java.util.List;

/**
 * Created by magq on 16/7/10.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<String> data;

    public MyFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;

    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position);
    }

    public static class PageFragment extends Fragment {

        private int page;

        private LinearLayoutManager mgr;
        private InfoAdapter adapter;

        public static PageFragment newInstance(int page) {
            PageFragment fragment = new PageFragment();
            fragment.page = page;
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_view_page,container,false);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_page);
            mgr = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mgr);
            adapter = new InfoAdapter(new InfoAdapter.OnItemClick() {
                @Override
                public void click(int index) {

                }
            });
            recyclerView.setAdapter(adapter);

            LinearLayout layoutInput = (LinearLayout) view.findViewById(R.id.ll_input);
            layoutInput.setVisibility(page == 0 ? View.VISIBLE : View.GONE);

            return view;
        }
    }
}
