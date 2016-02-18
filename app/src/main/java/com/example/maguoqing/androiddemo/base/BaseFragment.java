package com.example.maguoqing.androiddemo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maguoqing.androiddemo.annotation.Injector;


public abstract class BaseFragment extends Fragment {

    protected View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null);
            Injector.inject(this, rootView);
            readIntent();
            initControls(savedInstanceState);
            setListeners();
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    /**
     * get layout id for fragment
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * read intent from last object
     *
     * @return
     */
    protected abstract void readIntent();

    /**
     * init base values
     *
     * @return
     */
    protected abstract void initControls(Bundle savedInstanceState);

    /**
     * set listener for fragment views
     */
    protected abstract void setListeners();
}
