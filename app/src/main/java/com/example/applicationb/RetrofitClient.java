package com.example.applicationb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
