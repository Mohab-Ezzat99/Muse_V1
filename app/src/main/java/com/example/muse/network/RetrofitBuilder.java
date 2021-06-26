package com.example.muse.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static final String PLUG_URL = "http://192.168.33.1/";
    public static final String BASE_URL = "https://192.168.1.9:5001/";
    private static Retrofit instance = null;

    public static Retrofit getInstance(String url) {
        if (instance == null) {
//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
//                Request newRequest  = chain.request().newBuilder()
//                        .addHeader("Authorization", "Bearer " + token)
//                        .build();
//                return chain.proceed(newRequest);
//            }).build();

            instance = new Retrofit.Builder()
//                    .client(client)
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
