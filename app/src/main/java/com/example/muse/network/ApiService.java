package com.example.muse.network;

import com.example.muse.model.RegisterModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("settings")
    Call<JSONObject> setMqtt(@Query("mqtt_enable") int mqtt_enable, @Query("mqtt_server") String mqtt_server);

    @GET("settings/sta")
    Call<JSONObject> setWifi(@Query("enabled") int enabled, @Query("ssid") String ssid, @Query("key") String key);

    @POST("api/Authenticate/register")
    Call<JSONObject> registerUser(@Body RegisterModel registerModel);
}
