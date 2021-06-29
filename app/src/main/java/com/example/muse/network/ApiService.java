package com.example.muse.network;

import com.example.muse.model.AuthModel;
import com.example.muse.model.LoginResponseModel;

import org.json.JSONObject;

import okhttp3.ResponseBody;
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
    Call<ResponseBody> register(@Body AuthModel authModel);

    @POST("api/Authenticate/login")
    Call<LoginResponseModel> login(@Body AuthModel authModel);
}
