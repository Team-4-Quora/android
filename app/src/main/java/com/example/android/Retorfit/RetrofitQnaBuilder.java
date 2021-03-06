package com.example.android.Retorfit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitQnaBuilder {
    private  static Retrofit instance;

    private RetrofitQnaBuilder(){

    }

    public static  Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitQnaBuilder.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl("http://10.177.1.115:8081/")
                            .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }
}
