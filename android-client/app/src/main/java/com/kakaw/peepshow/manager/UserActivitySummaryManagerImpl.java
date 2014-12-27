package com.kakaw.peepshow.manager;

import android.util.Log;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.kakaw.peepshow.dao.FacebookDAO;
import com.kakaw.peepshow.dao.UserServiceClient;
import com.kakaw.peepshow.dao.dto.DropInfoDTO;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by keetaekhong on 11/15/14.
 */
public class UserActivitySummaryManagerImpl extends BaseManager implements UserActivitySummaryManager {

    private static final String TAG = "UserActivitySummaryManagerImpl";

    @Inject
    Bus bus;

    @Inject
    UserServiceClient mUserServiceClient;

    @Inject
    FacebookDAO mFacebookDao;

    /**
     * TODO - This can be implemented using RxJava. This method has chained service calls.
     * <p/>
     * Service call 1 - Facebook Login
     * Service call 2 - Fetch Drop info
     *
     * @return
     */
    @Override
    public UserLoginCompleteEvent loginAndFetchDropInfo() {

        //Synchronous service call.
        Response facebookProfileResponse = mFacebookDao.getFacebookUserProfile();
        if (facebookProfileResponse == null || facebookProfileResponse.getError() != null) {
            Log.e(TAG, "Login failed. Facebook Profile returned null");
            return new UserLoginCompleteEvent(false);
        }
        GraphUser user = facebookProfileResponse.getGraphObjectAs(GraphUser.class);
        if (user == null) {
            Log.e(TAG, "Login failed. User is null");
            return new UserLoginCompleteEvent(false);
        }

        String userId = user.getId();
        DropInfoDTO dropInfo = mUserServiceClient.getUserDropInfo(userId);

        return new UserLoginCompleteEvent(dropInfo, user, facebookProfileResponse, true);

    }
}
