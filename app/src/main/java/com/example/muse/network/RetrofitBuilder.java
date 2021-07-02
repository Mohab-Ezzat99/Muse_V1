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
    public static final String BASE_URL = "https://museapirg.azurewebsites.net/";
    private static Retrofit plugInstance = null,instance=null,authInstance=null;

    public static Retrofit getPlugInstance() {
        if(plugInstance== null){
            plugInstance = new Retrofit.Builder()
                    .baseUrl(PLUG_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return plugInstance;
    }

    public static Retrofit getInstance() {
        if(instance== null){
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    public static Retrofit getAuthInstance(String token) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }).build();
            authInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return authInstance;
    }
}
