package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.OrganisationDto;
import com.example.android.Retorfit.Model.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPostSearch {
    @GET("org/searchorg/{query}")
    Call<List<OrganisationDto>> searchInOrg(@Path("query") String query);


    @GET("user/searchuser/{query}")
    Call<List<UserDto>> searchUser(@Path("query") String query);
}
