package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.FollowerDto;
import com.example.android.Retorfit.Model.OrganisationDto;
import com.example.android.Retorfit.Model.PointRequest;
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
    Call<OrganisationDto> getAnOrganization(@Path(value = "id")String orgId);

    @POST("user/add")
    Call<Void> saveuser(@Body UserDto userDto);

    @POST("user/points")
    Call<Void> incUserPoints(@Body PointRequest pointRequest);


    @GET("organizations/email/{id}")
    Call<OrganisationDto> fetchOrganization(@Path(value = "id")String Id);

    @POST("organizations/add")
    Call<Void> addOrg(@Body OrganisationDto organisationDto);

    @GET("follower/fetch/org/followers/{id}")
    Call<List<FollowerDto>> getFollowers(@Path(value="id") String orgId);

    @POST("follower/accept/{id}/{requesterId}")
    Call<Void> acceptRequest(@Path(value="id") String id, @Path(value = "requesterId") String reqId);

}
