package com.kakaw.peepshow.manager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.common.base.Preconditions;
import com.kakaw.peepshow.annotation.AsyncMethod;
import com.squareup.otto.Bus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by keetaekhong on 11/16/14.
 */
public class ManagerInvocationHandler<T> implements InvocationHandler {

    private T mManager;
    private Bus mBus;
    private ExecutorService mExecutorService;

    public ManagerInvocationHandler(T manager, Bus bus, ExecutorService executorService) {
        this.mManager = manager;
        this.mBus = bus;
        this.mExecutorService = executorService;
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args)
            throws Throwable {
        Preconditions.checkNotNull(method);
        Preconditions.checkNotNull(args);

        // Creating a callable to execute
        Callable<Object> callable = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Exception exception;
                try {
                    return method.invoke(mManager, args);
                } catch (IllegalAccessException e) {
                    exception = e;
                    Log.e("ManagerInvocationHandler", "IllegalAccessException for the method " + method.getName(), e);
                } catch (InvocationTargetException e) {
                    exception = e;
                    Log.e("ManagerInvocationHandler", "Method must not exist " + method.getName(), e);
                }
                throw exception;
            }
        };
        if (isAsync(method)) {
            AsyncCallable asyncCallable = new AsyncCallable(callable, mBus);
            // TODO This will return Future, should I just return null?
            mExecutorService.submit(asyncCallable);
            return null;
        } else {
            return callable.call();
        }
    }

    private boolean isAsync(Method method) {
        AsyncMethod a = method.getAnnotation(AsyncMethod.class);
        return a != null;
    }

    public class AsyncCallable implements Callable<Object> {

        Callable mCallable;
        Bus mBus;

        public AsyncCallable(Callable callable, Bus bus) {
            this.mCallable = callable;
            this.mBus = bus;
        }

        @Override
        public Object call() throws Exception {
            Handler handler = new Handler(Looper.getMainLooper());
            final Object finishEvent = mCallable.call();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mBus.post(finishEvent);
                }
            });
            return null;
        }
    }
}
