package com.kakaw.peepshow.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakaw.peepshow.activity.BaseActivity;
import com.kakaw.peepshow.activity.LoginActivity;
import com.kakaw.peepshow.activity.MainActivity;
import com.kakaw.peepshow.dao.FacebookDAO;
import com.kakaw.peepshow.dao.UserServiceClient;
import com.kakaw.peepshow.fragment.MapFragment;
import com.kakaw.peepshow.manager.ManagerProxyFactory;
import com.kakaw.peepshow.manager.UserActivitySummaryManager;
import com.kakaw.peepshow.manager.UserActivitySummaryManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by keetaekhong on 10/12/14.
 */
//@formatter:off
@Module(injects = {LoginActivity.class, MainActivity.class, MapFragment.class, UserActivitySummaryManagerImpl.class }, complete = false, addsTo = AppModule.class, library = true)
public class UserModule {

//    private static final String API_URL = "http://10.0.2.2";
    private static final String API_URL = "http://localhost";
    private final BaseActivity activity;

    public UserModule(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with
     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
     */
    @Provides
    @Singleton
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

//    Managers

    @Provides
    @Singleton
    UserActivitySummaryManager provideUserActivitySummaryManager(ManagerProxyFactory proxyFactory, UserActivitySummaryManagerImpl userActivitySummaryManager) {
        return proxyFactory.createManagerProxy(userActivitySummaryManager);
    }


//    Service client

    @Provides
    @Singleton
    UserServiceClient provideUserServiceClient() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        // TODO - Extract RestAdaptor to its own provider
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();




        return restAdapter.create(UserServiceClient.class);
    }

    @Provides
    @Singleton
    FacebookDAO provideFacebookDAO() {
        return new FacebookDAO();
    }

}
