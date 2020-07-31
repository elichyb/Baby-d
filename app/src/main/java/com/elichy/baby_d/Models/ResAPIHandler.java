package com.elichy.baby_d.Models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ResAPIHandler {

    @POST("register")
    Call<String> registartParent(@Body ParentRegistration parentRegistration);

    @POST("login")
    Call<Token> loginParent(@Body ParentLogin parentLogin);

    @GET("get_my_babies")
    Call<List<Baby>> getMyBabies(@Header("Authorization") String token);

    @POST("add_baby")
    Call<String> addNewBaby(@Header("Authorization") String token, @Body Baby baby);
}
