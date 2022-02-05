package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPostUser {
    @GET("user/stats/{email}")
    Call<UserDto> getUserStats(@Path(value = "email") String email);


    @GET("people/fetch/{email}")
    Call<List<UserDto>> getUsersList(@Path(value= "email") String email);

}
