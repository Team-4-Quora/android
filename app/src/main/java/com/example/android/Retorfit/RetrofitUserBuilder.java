package com.example.android.Retorfit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUserBuilder {

    private  static Retrofit instance;

    private RetrofitUserBuilder(){

    }

    public static  Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitUserBuilder.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl("http://10.1.177.115:8082/")
                            .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }
}
