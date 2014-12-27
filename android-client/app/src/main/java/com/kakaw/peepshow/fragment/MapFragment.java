package com.kakaw.peepshow.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.kakaw.peepshow.R;
import com.kakaw.peepshow.dao.dto.DropInfoDTO;
import com.kakaw.peepshow.manager.UserActivitySummaryManager;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by keetaekhong on 11/16/14.
 */
public class MapFragment extends BaseFragment {


    @Inject
    UserActivitySummaryManager userActivitySummaryManager;

    private TextView mTextView;
    private TextView mTestTextView;


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
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        Button button = (Button) rootView.findViewById(R.id.button);
        mTextView = (TextView) rootView.findViewById(R.id.TextView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userActivitySummaryManager.loginAndFetchDropInfo();
            }
        });

        mTestTextView = (TextView) rootView.findViewById(R.id.drop_info);
        return rootView;
    }

    /**
     * TODO - Response and GraphUser should not bleed into Fragments. Ensure to refactor out GraphUser and Response from the bus events.
     * @param event
     */
    @Subscribe
    public void onUserLoginCompleteEvent(UserActivitySummaryManager.UserLoginCompleteEvent event) {

        boolean success = event.ismSuccess();
        if (!success) {
            Log.e("MapFragment", "User login has failed");
        }
        GraphUser graphUser = event.getmGraphUser();
        Response response = event.getmFacebookResponse();
        DropInfoDTO dropInfo = event.getmDropInfoDto();

        mTextView.setText("name: " + graphUser.getName() + " id: " + graphUser.getId());
    }
}
