package com.example.muse.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.muse.db.MuseDB;
import com.example.muse.db.MuseDao;
import com.example.muse.model.AlertModel;
import com.example.muse.model.AuthModel;
import com.example.muse.model.DeviceModel;
import com.example.muse.model.DeviceRequestModel;
import com.example.muse.model.DeviceResponseModel;
import com.example.muse.model.GoalModel;
import com.example.muse.model.InsightModel;
import com.example.muse.model.LoginResponseModel;
import com.example.muse.model.ScheduleModel;
import com.example.muse.network.ApiService;
import com.example.muse.network.RetrofitBuilder;

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

public class Repository {
    private ApiService apiService;
    private final MuseDB museDB;
    private final MuseDao museDao;

    public Repository(Application application) {
        this.museDB = MuseDB.getInstance(application);
        museDao = museDB.museDao();
    }

    //==========================================================================================//
    //API

    public Call<ResponseBody> register(AuthModel authModel) {
        apiService = RetrofitBuilder.getInstance().create(ApiService.class);
        return apiService.register(authModel);
    }

    public Call<LoginResponseModel> login(AuthModel authModel){
        apiService= RetrofitBuilder.getInstance().create(ApiService.class);
        return apiService.login(authModel);
    }

    public void setSecretToken(String token) {
        this.apiService = RetrofitBuilder.getAuthInstance(token).create(ApiService.class);
    }

    //________________________________________________________________________________________//
    //Alerts

    public LiveData<Call<List<AlertModel>>> getAllAlertsRequest(){
        return apiService.getAllAlertsRequest();
    }

    public LiveData<Call<List<AlertModel>>> getAlertsById(int deviceId){
        return apiService.getAlertsById(deviceId);
    }

    public Call<ResponseBody> deleteAlertById(int alertId){
        return apiService.deleteAlertById(alertId);
    }

    //________________________________________________________________________________________//
    //Custom Alerts

    public LiveData<Call<List<AlertModel>>> getAllCustomAlertsRequest(){
        return apiService.getAllCustomAlertsRequest();
    }

    public Call<AlertModel> addCustomAlert(AlertModel scheduleModel){
        return apiService.addCustomAlert(scheduleModel);
    }

    public LiveData<Call<List<AlertModel>>> getCustomAlertsById(int deviceId){
        return apiService.getCustomAlertsById(deviceId);
    }

    public Call<ResponseBody> deleteCustomAlertById(int customAlertId){
        return apiService.deleteCustomAlertById(customAlertId);
    }

    //________________________________________________________________________________________//
    //Devices

    public Call<DeviceResponseModel> addHouse(DeviceRequestModel requestModel) {
        return apiService.addHouse(requestModel);
    }

    public LiveData<Call<DeviceResponseModel>> getHouse(){
        return apiService.getHouse();
    }

    public Call<DeviceResponseModel> addDevice(DeviceRequestModel requestModel){
        return apiService.addDevice(requestModel);
    }

    public LiveData<Call<List<DeviceRequestModel>>> getAllDevicesRequest(int aggregation,int unit){
        return apiService.getAllDevicesRequest(aggregation,unit);
    }

    public LiveData<Call<DeviceResponseModel>> getDeviceById(int deviceId){
        return apiService.getDeviceById(deviceId);
    }

    public Call<DeviceResponseModel> editDeviceById(int deviceId,DeviceRequestModel requestModel){
        return apiService.editDeviceById(deviceId,requestModel);
    }

    public Call<ResponseBody> deleteDeviceById(int deviceId){
        return apiService.deleteDeviceById(deviceId);
    }

    public LiveData<Call<ResponseBody>> setState(int deviceId,int state){
        return apiService.setState(deviceId,state);
    }

    //________________________________________________________________________________________//
    //Goals

    public LiveData<Call<List<GoalModel>>> getAllGoalsRequest(){
        return apiService.getAllGoalsRequest();
    }

    public Call<GoalModel> addGoal(GoalModel goalModel){
        return apiService.addGoal(goalModel);
    }

    public LiveData<Call<List<GoalModel>>> getGoalsById(int deviceId){
        return apiService.getGoalsById(deviceId);
    }

    public Call<ResponseBody> deleteGoalById(int goalId){
        return apiService.deleteGoalById(goalId);
    }

    //________________________________________________________________________________________//
    //Insights

    public LiveData<Call<InsightModel>> getInsightRequest(int id,int aggregation,int unit){
        return apiService.getInsightRequest(id,aggregation,unit);
    }

    public LiveData<Call<InsightModel>> getCustomInsightRequest(int id,String unit,String year
            ,String month,String day){
        return apiService.getCustomInsightRequest(id,unit,year,month,day);
    }

    public LiveData<Call<ResponseBody>> getCurrentUsageRequest(int id,String unit){
        return apiService.getCurrentUsageRequest(id,unit);
    }

    //________________________________________________________________________________________//
    //Schedules

    public LiveData<Call<List<ScheduleModel>>> getAllSchedulesRequest(){
        return apiService.getAllSchedulesRequest();
    }

    public Call<ScheduleModel> addSchedule(ScheduleModel scheduleModel){
        return apiService.addSchedule(scheduleModel);
    }

    public LiveData<Call<List<ScheduleModel>>> getSchedulesById(int deviceId){
        return apiService.getSchedulesById(deviceId);
    }

    public Call<ResponseBody> deleteScheduleById(int scheduleId){
        return apiService.deleteScheduleById(scheduleId);
    }

    //===========================================================================================//
    //Room

    public void insertDevice(DeviceModel device){
        new InsertDeviceAsyncTask(museDao).doInBackground(device);
    }

    public void updateDevice(DeviceModel device){
        new UpdateDeviceAsyncTask(museDao).doInBackground(device);
    }

    public void deleteDevice(DeviceModel device){
        new DeleteDeviceAsyncTask(museDao).doInBackground(device);
    }

    public LiveData<List<DeviceModel>> getAllDevices(){
        return museDao.getAllDevices();
    }

    public LiveData<List<DeviceModel>> getDevicesAdded(){
        return museDao.getDevicesAdded();
    }

    public LiveData<List<DeviceModel>> getDevicesGoals(){
        return museDao.getDevicesGoals();
    }

    public LiveData<List<DeviceModel>> getDevicesAlerts(){
        return museDao.getDevicesAlerts();
    }

    public LiveData<List<DeviceModel>> getDevicesSchedules() {
        return museDao.getDevicesSchedules();
    }

    public LiveData<List<DeviceModel>> getDevicesCustomAlerts() {
        return museDao.getDevicesCustomAlerts();
    }

    public LiveData<List<DeviceModel>> getDevicesWithoutGoal() {
        return museDao.getDevicesWithoutGoal();
    }

    public LiveData<List<DeviceModel>> getDevicesWithoutSchedule() {
        return museDao.getDevicesWithoutSchedule();
    }

    public LiveData<List<DeviceModel>> getDevicesWithoutCustomAlert() {
        return museDao.getDevicesWithoutCustomAlert();
    }

    public static class InsertDeviceAsyncTask extends AsyncTask<DeviceModel,Void,Void>{
        private final MuseDao museDao;

        public InsertDeviceAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(DeviceModel... deviceModels) {
            museDao.insertDevice(deviceModels[0]);
            return null;
        }
    }

    public static class UpdateDeviceAsyncTask extends AsyncTask<DeviceModel,Void,Void>{
        private final MuseDao museDao;

        public UpdateDeviceAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(DeviceModel... deviceModels) {
            museDao.updateDevice(deviceModels[0]);
            return null;
        }
    }

    public static class DeleteDeviceAsyncTask extends AsyncTask<DeviceModel,Void,Void>{
        private final MuseDao museDao;

        public DeleteDeviceAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(DeviceModel... deviceModels) {
            museDao.deleteDevice(deviceModels[0]);
            return null;
        }
    }
}
