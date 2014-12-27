package com.kakaw.peepshow.dao;

import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

/**
 * Created by keetaekhong on 12/25/14.
 *
 * This class manages any interaction with Facebook. Most of the operations here are either
 */
public class FacebookDAO {

    private static final String TAG = "FacebookDAO";

    /**
     * Synchronously fetch current user's profile from Facebook
     * @return
     */
    public Response getFacebookUserProfile() {
        Session session = Session.getActiveSession();
        if (session == null || !session.isOpened()) {
            Log.e(TAG, "Session is null or not same as active session");
            //TODO - create a custom exception to handle Facebook session error.
            throw new RuntimeException("Facebook session is null or out of sync");
        }
        // Synchronous operation
        Request request = Request.newMeRequest(session, null);
        return request.executeAndWait();
    }
}
