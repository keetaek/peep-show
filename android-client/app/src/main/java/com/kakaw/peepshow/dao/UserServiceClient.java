package com.kakaw.peepshow.dao;

import com.kakaw.peepshow.dao.dto.UserDTO;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by keetaekhong on 11/16/14.
 */
public interface UserServiceClient {

    // http://localhost/peepshow/users/1/dropinfo.json
    @GET("/peepshow/users/{userId}/dropinfo.json")
    UserDTO getUser(@Path("userId") String userId);
}
