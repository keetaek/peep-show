package com.kakaw.peepshow.manager;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.kakaw.peepshow.annotation.AsyncMethod;
import com.kakaw.peepshow.dao.dto.DropInfoDTO;

/**
 * Created by keetaekhong on 11/15/14.
 */
public interface UserActivitySummaryManager {

    /**
     * Login to Facebook system, then fetch the DropInfo from PeepShow service.
     */
    @AsyncMethod
    public UserLoginCompleteEvent loginAndFetchDropInfo();


    public static class UserLoginCompleteEvent {
        private DropInfoDTO mDropInfoDto;
        private GraphUser mGraphUser;
        private Response mFacebookResponse;
        private boolean mSuccess;

        public UserLoginCompleteEvent(DropInfoDTO dropInfoDto, GraphUser graphUser, Response facebookResponse, boolean success) {
            this.mDropInfoDto = dropInfoDto;
            this.mGraphUser = graphUser;
            this.mFacebookResponse = facebookResponse;
            this.mSuccess = success;
        }

        public UserLoginCompleteEvent(boolean success) {
            this.mDropInfoDto = null;
            this.mGraphUser = null;
            this.mFacebookResponse = null;
            this.mSuccess = success;
        }

        public boolean ismSuccess() {
            return mSuccess;
        }

        public Response getmFacebookResponse() {
            return mFacebookResponse;
        }

        public DropInfoDTO getmDropInfoDto() {
            return mDropInfoDto;
        }

        public GraphUser getmGraphUser() {
            return mGraphUser;
        }
    }
}
