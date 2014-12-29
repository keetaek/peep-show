package com.kakaw.peepshow.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class DropInfoMapFragment extends BaseFragment {

    @Inject
    UserActivitySummaryManager userActivitySummaryManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
        View rootView = inflater.inflate(R.layout.drop_info_map_fragment, container, false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.drop_info_map_fragment_actions, menu);
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

//        mTextView.setText("name: " + graphUser.getName() + " id: " + graphUser.getId());
    }
}
