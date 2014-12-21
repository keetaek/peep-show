package com.kakaw.peepshow.helper;

import android.content.Context;

import com.facebook.AppEventsLogger;

/**
 * Created by keetaekhong on 12/19/14.
 */
public class AnalyticsHelper {

    public AnalyticsHelper() {

    }

    public void resumeAppTrigger(Context context) {
        AppEventsLogger.activateApp(context);
    }

    public void pauseAppTrigger(Context context) {
        AppEventsLogger.deactivateApp(context);
    }

}
