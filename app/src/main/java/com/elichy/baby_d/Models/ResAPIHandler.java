/*
 * //----------------------------------------------------------------------------
 * // (C) Copyright Elichy Barak 2020
 * //
 * // The source code for this program is not published or other-
 * // wise divested of its trade secrets, irrespective of what has
 * // been deposited with the U.S. Copyright Office.
 * //
 * //----------------------------------------------------------------------------
 */

package com.elichy.baby_d.Models;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ResAPIHandler {

    @POST("register")
    Call<String> registertParent(@Body ParentRegistration parentRegistration);

    @POST("login")
    Call<Token> loginParent(@Body ParentLogin parentLogin);

    @GET("get_my_babies")
    Call<List<Baby>> getMyBabies(@Header("Authorization") String token);

    @POST("get_baby_full_info_for_today")
    Call<List<BabyFullInfo>> getBabyFullInfo(@Header("Authorization") String token, @Body BabyF babyF);

    @POST("add_baby")
    Call<String> addNewBaby(@Header("Authorization") String token, @Body Baby baby);

    @POST("set_weight")
    Call<String> setBabyWeight(@Header("Authorization") String token, @Body Weight weight);

    @POST("set_diaper")
    Call<String> setBabyDiaper(@Header("Authorization") String token, @Body Diaper diaper);

    @POST("set_sleep")
    Call<String> setBabySleep(@Header("Authorization") String token, @Body Sleep sleep);

    @POST("set_formula")
    Call<String> setBabyEatFormula(@Header("Authorization") String token, @Body Formula formula);

    @POST("set_breast")
    Call<String> setBabyEatBreast(@Header("Authorization") String token, @Body BreastFeed breastFeed);
}
