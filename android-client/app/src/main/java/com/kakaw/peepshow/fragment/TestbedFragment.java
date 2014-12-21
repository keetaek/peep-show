package com.kakaw.peepshow.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kakaw.peepshow.R;
import com.kakaw.peepshow.dao.dto.UserDTO;
import com.kakaw.peepshow.manager.UserActivitySummaryManager;
import com.kakaw.peepshow.manager.UserActivitySummaryManagerImpl;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by keetaekhong on 11/16/14.
 */
public class TestbedFragment extends BaseFragment {


    @Inject
    UserActivitySummaryManager userActivitySummaryManager;

    private TextView mTextView;

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button button = (Button) rootView.findViewById(R.id.button);
        mTextView = (TextView) rootView.findViewById(R.id.TextView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userActivitySummaryManager.getUser();
            }
        });
        return rootView;
    }

    @Subscribe
    public void onUserRetrieveCompleteEvent(UserActivitySummaryManagerImpl.UserRetrieveCompleteEvent event) {
        UserDTO userDto = event.getmUserDTO();
        mTextView.setText(userDto.getName());
    }
}
