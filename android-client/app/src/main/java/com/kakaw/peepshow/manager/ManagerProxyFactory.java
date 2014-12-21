package com.kakaw.peepshow.manager;

import android.util.Log;

import com.squareup.otto.Bus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * Created by keetaekhong on 11/9/14.
 */
public class ManagerProxyFactory {

    private ExecutorService mExecutorService;
    private String TAG = "ManagerProxyFactory";
    private Bus mBus;

    @Inject
    public ManagerProxyFactory(ExecutorService executorService, Bus bus) {
        this.mExecutorService = executorService;
        this.mBus = bus;
    }

    public <T> T createManagerProxy(T manager) {
        Log.d("KEETAEK", "Creating proxy for manager " + manager.getClass().getName());
        InvocationHandler handler = new ManagerInvocationHandler(manager, mBus, mExecutorService);
        Class<?>[] interfaces = manager.getClass().getInterfaces();
        T proxy = (T) Proxy.newProxyInstance(
                manager.getClass().getClassLoader(),
                interfaces,
                handler);
        return proxy;
    }


}
