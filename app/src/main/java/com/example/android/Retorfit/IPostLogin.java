package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.CommentDto;
import com.example.android.Retorfit.Model.InterestDto;
import com.example.android.Retorfit.Model.LoginDto;
import com.example.android.Retorfit.Model.LogoutDto;
import com.example.android.Retorfit.Model.SigninResponse;
import com.example.android.Retorfit.Model.SignupResponse;
import com.example.android.Retorfit.Model.StatusDto;
import com.example.android.Retorfit.Model.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IPostLogin {
    @POST("/authentication/authenticate/login")
    Call<SigninResponse> loginquora(@Body LoginDto loginDto);

    @POST("authentication/authenticate/register")
    Call<SignupResponse> signupquora(@Body UserRegister userRegister);

    @POST("authentication/authenticate/interest")
    Call<Void> updateInterest(@Body InterestDto interestDto);

    @POST("/authentication/authenticate/logout")
    Call<SigninResponse> logout(@Body LogoutDto logoutDto);
}
