package com.example.muse.network;

import androidx.lifecycle.LiveData;

import com.example.muse.model.AlertModel;
import com.example.muse.model.AuthModel;
import com.example.muse.model.DeviceRequestModel;
import com.example.muse.model.DeviceResponseModel;
import com.example.muse.model.GoalModel;
import com.example.muse.model.InsightModel;
import com.example.muse.model.LoginResponseModel;
import com.example.muse.model.ScheduleModel;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    //________________________________________________________________________________________//
    //Alerts

    @GET("api/Alerts")
    LiveData<Call<List<AlertModel>>> getAllAlertsRequest();

    @GET("api/Alerts/{deviceId}")
    LiveData<Call<List<AlertModel>>> getAlertsById(@Path("deviceId") int deviceId);

    @DELETE("api/Alerts/{alertId}")
    Call<ResponseBody> deleteAlertById(@Path("alertId") int alertId);

    //________________________________________________________________________________________//
    //Custom Alerts

    @GET("api/CustomAlerts")
    LiveData<Call<List<AlertModel>>> getAllCustomAlertsRequest();

    @POST("api/CustomAlerts")
    Call<AlertModel> addCustomAlert(@Body AlertModel scheduleModel);

    @GET("api/CustomAlerts/{deviceId}")
    LiveData<Call<List<AlertModel>>> getCustomAlertsById(@Path("deviceId") int deviceId);

    @DELETE("api/CustomAlerts/{customAlertId}")
    Call<ResponseBody> deleteCustomAlertById(@Path("customAlertId") int customAlertId);

    //________________________________________________________________________________________//
    //Devices

    @POST("AddHouse")
    Call<DeviceResponseModel> addHouse(@Body DeviceRequestModel requestModel);

    @GET("GetHouse")
    LiveData<Call<DeviceResponseModel>> getHouse();

    @POST("api/Devices")
    Call<DeviceResponseModel> addDevice(@Body DeviceRequestModel requestModel);

    @GET("api/Devices")
    LiveData<Call<List<DeviceRequestModel>>> getAllDevicesRequest(@Query("aggregation") int aggregation, @Query("unit") int unit);

    @GET("api/Devices/{deviceId}")
    LiveData<Call<DeviceResponseModel>> getDeviceById(@Path("deviceId") int deviceId);

    @PUT("api/Devices/{deviceId}")
    Call<DeviceResponseModel> editDeviceById(@Path("deviceId") int deviceId, @Body DeviceRequestModel requestModel);

    @DELETE("api/Devices/{deviceId}")
    Call<ResponseBody> deleteDeviceById(@Path("deviceId") int deviceId);

    @GET("SetState/{deviceId}/{state}")
    LiveData<Call<ResponseBody>> setState(@Path("deviceId") int deviceId, @Path("state") int state);

    //________________________________________________________________________________________//
    //Goals

    @GET("api/Goals")
    LiveData<Call<List<GoalModel>>> getAllGoalsRequest();

    @POST("api/Goals")
    Call<GoalModel> addGoal(@Body GoalModel goalModel);

    @GET("api/Goals/{deviceId}")
    LiveData<Call<List<GoalModel>>> getGoalsById(@Path("deviceId") int deviceId);

    @DELETE("api/Goals/{goalId}")
    Call<ResponseBody> deleteGoalById(@Path("goalId") int goalId);

    //________________________________________________________________________________________//
    //Insights

    @GET("Insights/GetInsight")
    LiveData<Call<InsightModel>> getInsightRequest(@Query("id") int id, @Query("aggregation") int aggregation, @Query("unit") int unit);

    @GET("Insights/GetCustomInsight")
    LiveData<Call<InsightModel>> getCustomInsightRequest(@Query("id") int id, @Query("unit") String unit, @Query("year") String year
            , @Query("month") String month, @Query("day") String day);

    @GET("Insights/GetCurrentUsage")
    LiveData<Call<ResponseBody>> getCurrentUsageRequest(@Query("id") int id, @Query("unit") String unit);

    //________________________________________________________________________________________//
    //Schedules

    @GET("api/Schedules")
    LiveData<Call<List<ScheduleModel>>> getAllSchedulesRequest();

    @POST("api/Schedules")
    Call<ScheduleModel> addSchedule(@Body ScheduleModel scheduleModel);

    @GET("api/Schedules/{deviceId}")
    LiveData<Call<List<ScheduleModel>>> getSchedulesById(@Path("deviceId") int deviceId);

    @DELETE("api/Schedules/{scheduleId}")
    Call<ResponseBody> deleteScheduleById(@Path("scheduleId") int scheduleId);
}
