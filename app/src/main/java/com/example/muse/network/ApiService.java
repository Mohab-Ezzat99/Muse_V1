package com.example.muse.network;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    // http://192.168.33.1/settings?mqtt_enable=1&mqtt_server=broker.hivemq.com:1883
    // mqtt_server=broker.hivemq.com:1883
    // mqtt_enable=1


    @GET("settings")
    Call<JSONObject> setMqtt(@Query("mqtt_enable") int mqtt_enable, @Query("mqtt_server") String mqtt_server);

    // http://192.168.33.1/settings/sta?enabled=1&ssid=Phenomenal32&key=MedoPasswooooord
    // enabled=1
    // ssid=Phenomenal32
    // key=Medotwo3210Fo
    @GET("settings/sta")
    Call<JSONObject> setWifi(@Query("enabled") int enabled, @Query("ssid") String ssid, @Query("key") String key);
}
