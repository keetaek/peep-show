package com.kakaw.peepshow.dao;

import com.kakaw.peepshow.dao.dto.DropInfoDTO;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by keetaekhong on 11/16/14.
 */
public interface UserServiceClient {

    // http://localhost/peepshow/users/1/dropinfo.json
    @GET("/peepshow/users/{userId}/dropinfo.json")
    DropInfoDTO getUserDropInfo(@Path("userId") String userId);
}
