package com.kakaw.peepshow.manager;

import com.kakaw.peepshow.annotation.AsyncMethod;

/**
 * Created by keetaekhong on 11/15/14.
 */
public interface UserActivitySummaryManager {

    @AsyncMethod
    public UserActivitySummaryManagerImpl.UserRetrieveCompleteEvent getUser();

}
