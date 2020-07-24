package com.elichy.baby_d.Models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ResAPIHandler {

    @POST("register")
    Call<String> registartParent(@Body ParentRegistration parentRegistration);

    @POST("login")
    Call<String> loginParent(@Body ParentLogin parentLogin);
}
