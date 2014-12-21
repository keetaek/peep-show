package com.kakaw.peepshow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.UserSettingsFragment;
import com.kakaw.peepshow.R;
import com.kakaw.peepshow.helper.Constants;
import com.kakaw.peepshow.module.UserModule;

import java.util.Arrays;


public class LoginActivity extends BaseActivity {

    public static String TAG = "LoginActivity";

    private UserSettingsFragment userSettingsFragment;
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback sessionStatusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
//        if (state == SessionState.OPENED
//                && !session.getPermissions().containsAll(Lists.newArrayList(Constants.Login.FB_PUBLISH_PERMISSIONS))) {
//            session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
//                    LoginActivity.this,
//                    Constants.Login.FB_PUBLISH_PERMISSIONS));
//        }
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        uiHelper = new UiLifecycleHelper(this, sessionStatusCallback);
        uiHelper.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            userSettingsFragment = new UserSettingsFragment();

            // FB permissions - https://developers.facebook.com/docs/facebook-login/permissions/v2.2
            userSettingsFragment.setReadPermissions(Constants.Login.FB_READ_PERMISSIONS);
            userSettingsFragment
                    .setSessionStatusCallback(sessionStatusCallback);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, userSettingsFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            userSettingsFragment = (UserSettingsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    /**
     * A list of modules to use for the individual activity graph. Subclasses can override this
     * method to provide additional modules provided they call and include the modules returned by
     * calling {@code super.getModules()}.
     */
    @Override
    protected java.util.List<Object> getModules() {
        return Arrays.<Object>asList(new UserModule(this));
    }

}
