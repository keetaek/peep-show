package com.kakaw.peepshow.application;

import android.app.Application;

import com.kakaw.peepshow.module.AppModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by keetaekhong on 10/11/14.
 */
public class PeepShowApplication extends Application {

    private ObjectGraph applicationGraph;
    private static PeepShowApplication mApplication;

    @Override public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(getModules().toArray());
        mApplication = this;
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    public static PeepShowApplication getApplication() {
        return mApplication;
    }
}
