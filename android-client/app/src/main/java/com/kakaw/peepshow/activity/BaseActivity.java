package com.kakaw.peepshow.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.kakaw.peepshow.application.PeepShowApplication;
import com.kakaw.peepshow.helper.AnalyticsHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by keetaekhong on 10/14/14.
 */
public abstract class BaseActivity extends FragmentActivity {
    private ObjectGraph activityGraph;
    private Session mSession;

    @Inject
    AnalyticsHelper analyticsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the activity graph by .plus-ing our modules onto the application graph.
        PeepShowApplication application = (PeepShowApplication) getApplication();

        activityGraph = application.getApplicationGraph().plus(getModules().toArray());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        activityGraph.inject(this);

    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        activityGraph = null;

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // FACEBOOK - Logs 'app deactivate' App Event.
        analyticsHelper.pauseAppTrigger(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // FACEBOOK - Logs 'install' and 'app activate' App Events.
        analyticsHelper.resumeAppTrigger(this);

        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        mSession = Session.getActiveSession();
        if (mSession != null &&
                (mSession.isOpened() || mSession.isClosed()) ) {
            onSessionStateChange(mSession, mSession.getState(), null);
        }
    }

    protected void onSessionStateChange(Session session, SessionState state, Exception exception) {
//        if (state == SessionState.OPENED
//                && !session.getPermissions().containsAll(Lists.newArrayList(Constants.Login.FB_PUBLISH_PERMISSIONS))) {
//            session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
//                    LoginActivity.this,
//                    Constants.Login.FB_PUBLISH_PERMISSIONS));
//        }
        if (state.isOpened()) {
            Log.i(LoginActivity.TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(LoginActivity.TAG, "Logged out...");
        }
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    public void inject(Object object) {
        activityGraph.inject(object);
    }

    protected abstract List<Object> getModules();
}
