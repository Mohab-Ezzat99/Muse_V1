package com.example.muse.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
import com.example.muse.repository.Repository;
import com.example.muse.utility.SaveState;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class MuseViewModel extends AndroidViewModel {
    private Repository repository;

    public MuseViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }

    //===========================================================================================//
    //API

    public Call<ResponseBody> register(AuthModel authModel){return repository.register(authModel); }

    public Call<LoginResponseModel> login(AuthModel authModel){return repository.login(authModel);}

    public void setSecretToken(String token) {
        repository.setSecretToken(token);
    }

    //________________________________________________________________________________________//
    //Alerts

    public LiveData<Call<List<AlertModel>>> getAllAlertsRequest(){
        return repository.getAllAlertsRequest();
    }

    public LiveData<Call<List<AlertModel>>> getAlertsById(int deviceId){
        return repository.getAlertsById(deviceId);
    }

    public Call<ResponseBody> deleteAlertById(int alertId){
        return repository.deleteAlertById(alertId);
    }

    //________________________________________________________________________________________//
    //Custom Alerts

    public LiveData<Call<List<AlertModel>>> getAllCustomAlertsRequest(){
        return repository.getAllCustomAlertsRequest();
    }

    public Call<AlertModel> addCustomAlert(AlertModel scheduleModel){
        return repository.addCustomAlert(scheduleModel);
    }

    public LiveData<Call<List<AlertModel>>> getCustomAlertsById(int deviceId){
        return repository.getCustomAlertsById(deviceId);
    }

    public Call<ResponseBody> deleteCustomAlertById(int customAlertId){
        return repository.deleteCustomAlertById(customAlertId);
    }

    //________________________________________________________________________________________//
    //Devices

    public Call<DeviceResponseModel> addHouse(DeviceRequestModel requestModel) {
        return repository.addHouse(requestModel);
    }

    public LiveData<Call<DeviceResponseModel>> getHouse(){
        return repository.getHouse();
    }

    public Call<DeviceResponseModel> addDevice(DeviceRequestModel requestModel){
        return repository.addDevice(requestModel);
    }

    public LiveData<Call<List<DeviceRequestModel>>> getAllDevicesRequest(int aggregation,int unit){
        return repository.getAllDevicesRequest(aggregation,unit);
    }

    public LiveData<Call<DeviceResponseModel>> getDeviceById(int deviceId){
        return repository.getDeviceById(deviceId);
    }

    public Call<DeviceResponseModel> editDeviceById(int deviceId,DeviceRequestModel requestModel){
        return repository.editDeviceById(deviceId,requestModel);
    }

    public Call<ResponseBody> deleteDeviceById(int deviceId){
        return repository.deleteDeviceById(deviceId);
    }

    public LiveData<Call<ResponseBody>> setState(int deviceId,int state){
        return repository.setState(deviceId,state);
    }

    //________________________________________________________________________________________//
    //Goals

    public LiveData<Call<List<GoalModel>>> getAllGoalsRequest(){
        return repository.getAllGoalsRequest();
    }

    public Call<GoalModel> addGoal(GoalModel goalModel){
        return repository.addGoal(goalModel);
    }

    public LiveData<Call<List<GoalModel>>> getGoalsById(int deviceId){
        return repository.getGoalsById(deviceId);
    }

    public Call<ResponseBody> deleteGoalById(int goalId){
        return repository.deleteGoalById(goalId);
    }

    //________________________________________________________________________________________//
    //Insights

    public LiveData<Call<InsightModel>> getInsightRequest(int id, int aggregation, int unit){
        return repository.getInsightRequest(id,aggregation,unit);
    }

    public LiveData<Call<InsightModel>> getCustomInsightRequest(int id,String unit,String year
            ,String month,String day){
        return repository.getCustomInsightRequest(id,unit,year,month,day);
    }

    public LiveData<Call<ResponseBody>> getCurrentUsageRequest(int id,String unit){
        return repository.getCurrentUsageRequest(id,unit);
    }

    //________________________________________________________________________________________//
    //Schedules

    public LiveData<Call<List<ScheduleModel>>> getAllSchedulesRequest(){
        return repository.getAllSchedulesRequest();
    }

    public Call<ScheduleModel> addSchedule(ScheduleModel scheduleModel){
        return repository.addSchedule(scheduleModel);
    }

    public LiveData<Call<List<ScheduleModel>>> getSchedulesById(int deviceId){
        return repository.getSchedulesById(deviceId);
    }

    public Call<ResponseBody> deleteScheduleById(int scheduleId){
        return repository.deleteScheduleById(scheduleId);
    }

    //===========================================================================================//
    //Room

    public void insertDevice(DeviceModel device){
        repository.insertDevice(device);
    }

    public void updateDevice(DeviceModel device){
        repository.updateDevice(device);
    }

    public void deleteDevice(DeviceModel device) {
        repository.deleteDevice(device);
    }

    public LiveData<List<DeviceModel>> getAllDevices() {
        return repository.getAllDevices();
    }

    public LiveData<List<DeviceModel>> getDevicesAdded() {
        return repository.getDevicesAdded();
    }

    public LiveData<List<DeviceModel>> getDevicesGoals() {
        return repository.getDevicesGoals();
    }

    public LiveData<List<DeviceModel>> getDevicesAlerts() {
        return repository.getDevicesAlerts();
    }

    public LiveData<List<DeviceModel>> getDevicesSchedules() { return repository.getDevicesSchedules();}

    public LiveData<List<DeviceModel>> getDevicesCustomAlerts(){return repository.getDevicesCustomAlerts();}

    public MutableLiveData<Integer> getNewAlerts() {
        return SaveState.getNewAlerts();
    }

    public LiveData<List<DeviceModel>> getDevicesCWithoutGoal() {
        return repository.getDevicesWithoutGoal();
    }

    public LiveData<List<DeviceModel>> getDevicesWithoutSchedule() {
        return repository.getDevicesWithoutSchedule();
    }

    public LiveData<List<DeviceModel>> getDevicesWithoutCustomAlerts() {
        return repository.getDevicesWithoutCustomAlert();
    }
}
