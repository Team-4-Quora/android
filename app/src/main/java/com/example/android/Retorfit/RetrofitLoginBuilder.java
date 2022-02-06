package com.example.android.Retorfit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLoginBuilder {
    private  static Retrofit instance;

    public RetrofitLoginBuilder() {
    }

    public static  Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitLoginBuilder.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder().baseUrl("http://10.177.1.200:8000/")
                            .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }


}
