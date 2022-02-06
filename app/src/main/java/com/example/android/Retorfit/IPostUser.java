package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.FollowerDto;
import com.example.android.Retorfit.Model.OrganizationDto;
import com.example.android.Retorfit.Model.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPostUser {
    @GET("user/stats/{email}")
    Call<UserDto> getUserStats(@Path(value = "email") String email);

    @GET("follower/fetch/{type}/{id}")
    Call<List<FollowerDto>> fetchFollowerData(@Path(value = "type") String type, @Path(value = "id") String id);

    @GET("people/fetch/{email}")
    Call<List<UserDto>> getUsersList(@Path(value= "email") String email);

    @GET("organizations/{id}")
    Call<OrganizationDto> getAnOrganization(@Path(value = "id")String orgId);

    @GET("organizations/email/{id}")
    Call<List<OrganizationDto>> fetchOrganization(@Path(value = "id")String Id);

    @POST("organizations/add")
    Call<Void> addOrg(@Body OrganizationDto organizationDto);

}
