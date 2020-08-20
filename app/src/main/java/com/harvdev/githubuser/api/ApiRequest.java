package com.harvdev.githubuser.api;

import com.harvdev.githubuser.model.ResponsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {


    @GET("users")
    Call<ResponsModel> getUsers(@Query("q") String query,
                                @Query("page") int page);

}
