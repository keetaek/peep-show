package com.kakaw.peepshow.module;

import android.content.Context;
import android.location.LocationManager;

import com.kakaw.peepshow.activity.BaseActivity;
import com.kakaw.peepshow.application.PeepShowApplication;
import com.kakaw.peepshow.helper.AnalyticsHelper;
import com.kakaw.peepshow.manager.BaseManager;
import com.kakaw.peepshow.manager.ManagerProxyFactory;
import com.squareup.otto.Bus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by keetaekhong on 10/12/14.
 */

//@formatter:off
@Module(library = true, injects = {BaseManager.class, BaseActivity.class, ManagerProxyFactory.class})
public class AppModule {

    private final PeepShowApplication application;

    public AppModule(PeepShowApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    PeepShowApplication provideApplication() { return application; }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(4);
    }

    @Provides
    @Singleton
    AnalyticsHelper provideAnalyticsHelper() {
        return new AnalyticsHelper();
    }
}