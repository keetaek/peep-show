package com.kakaw.peepshow.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    public void inject(Object object) {
        activityGraph.inject(object);
    }

    protected abstract List<Object> getModules();
}
