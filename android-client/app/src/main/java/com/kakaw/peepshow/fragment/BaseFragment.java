package com.kakaw.peepshow.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.kakaw.peepshow.activity.BaseActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by keetaekhong on 11/16/14.
 */
public class BaseFragment extends Fragment {

    @Inject
    Bus mBus;

    /**
     * Base fragment which performs injection using the activity object graph of its parent.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).inject(this);
    }


}
