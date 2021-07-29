package com.example.musev1.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.musev1.db.MuseDB;
import com.example.musev1.db.MuseDao;
import com.example.musev1.model.AlertModel;
import com.example.musev1.model.AuthModel;
import com.example.musev1.model.CustomAlertModel;
import com.example.musev1.model.DeviceModel;
import com.example.musev1.model.DeviceRequestModel;
import com.example.musev1.model.DeviceResponseModel;
import com.example.musev1.model.GoalModel;
import com.example.musev1.model.InsightModel;
import com.example.musev1.model.LoginResponseModel;
import com.example.musev1.model.ScheduleModel;
import com.example.musev1.network.ApiService;
import com.example.musev1.network.RetrofitBuilder;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;

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

    public Observable<List<AlertModel>> getAllAlertsRequest(){
        return apiService.getAllAlertsRequest();
    }

    public Observable<Call<List<AlertModel>>> getAlertsById(int deviceId){
        return apiService.getAlertsById(deviceId);
    }

    public Call<ResponseBody> deleteAlertById(int alertId){
        return apiService.deleteAlertById(alertId);
    }

    //________________________________________________________________________________________//
    //Custom Alerts

    public Observable<List<AlertModel>> getAllCustomAlertsRequest(){
        return apiService.getAllCustomAlertsRequest();
    }

    public Call<AlertModel> addCustomAlert(AlertModel scheduleModel){
        return apiService.addCustomAlert(scheduleModel);
    }

    public Observable<Call<List<AlertModel>>> getCustomAlertsById(int deviceId){
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

    public Observable<DeviceResponseModel> getHouse(){
        return apiService.getHouse();
    }

    public Call<DeviceResponseModel> addDevice(DeviceRequestModel requestModel){
        return apiService.addDevice(requestModel);
    }

    public Observable<List<DeviceRequestModel>> getAllDevicesRequest(int aggregation, int unit){
        return apiService.getAllDevicesRequest(aggregation,unit);
    }

    public Observable<DeviceResponseModel> getDeviceById(int deviceId){
        return apiService.getDeviceById(deviceId);
    }

    public Call<DeviceResponseModel> editDeviceById(int deviceId,DeviceRequestModel requestModel){
        return apiService.editDeviceById(deviceId,requestModel);
    }

    public Call<ResponseBody> deleteDeviceById(int deviceId){
        return apiService.deleteDeviceById(deviceId);
    }

    public Call<ResponseBody> setState(int deviceId, int state){
        return apiService.setState(deviceId,state);
    }

    //________________________________________________________________________________________//
    //Goals

    public Observable<List<GoalModel>> getAllGoalsRequest(){
        return apiService.getAllGoalsRequest();
    }

    public Call<GoalModel> addGoal(GoalModel goalModel){
        return apiService.addGoal(goalModel);
    }

    public Observable<Call<List<GoalModel>>> getGoalsById(int deviceId){
        return apiService.getGoalsById(deviceId);
    }

    public Call<ResponseBody> deleteGoalById(int goalId){
        return apiService.deleteGoalById(goalId);
    }

    //________________________________________________________________________________________//
    //Insights

    public Observable<InsightModel> getInsightRequest(int id, int aggregation, int unit){
        return apiService.getInsightRequest(id,aggregation,unit);
    }

    public Observable<Call<InsightModel>> getCustomInsightRequest(int id, String unit, String year
            , String month, String day){
        return apiService.getCustomInsightRequest(id,unit,year,month,day);
    }

    public Observable<ResponseBody> getCurrentUsageRequest(int id, String unit){
        return apiService.getCurrentUsageRequest(id,unit);
    }

    //________________________________________________________________________________________//
    //Schedules

    public Observable<List<ScheduleModel>> getAllSchedulesRequest(){
        return apiService.getAllSchedulesRequest();
    }

    public Call<ScheduleModel> addSchedule(ScheduleModel scheduleModel){
        return apiService.addSchedule(scheduleModel);
    }

    public Observable<Call<List<ScheduleModel>>> getSchedulesById(int deviceId){
        return apiService.getSchedulesById(deviceId);
    }

    public Call<ResponseBody> deleteScheduleById(int scheduleId){
        return apiService.deleteScheduleById(scheduleId);
    }

    //===========================================================================================//
    //Room

    //devices
    public Long insertDevice(DeviceModel device){
        return new InsertDeviceAsyncTask(museDao).doInBackground(device);
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

    public LiveData<DeviceModel> getDevice(Long deviceId){
        return museDao.getDevice(deviceId);
    }

    public static class InsertDeviceAsyncTask extends AsyncTask<DeviceModel,Void, Long>{
        private final MuseDao museDao;

        public InsertDeviceAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Long doInBackground(DeviceModel... deviceModels) {
            return museDao.insertDevice(deviceModels[0]);
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

    //____________________________________________________________________________________________//
    //alerts

    public void insertAlert(AlertModel alert){
        new InsertAlertAsyncTask(museDao).doInBackground(alert);
    }

    public void deleteAlert(AlertModel alert){
        new DeleteAlertAsyncTask(museDao).doInBackground(alert);
    }

    public LiveData<List<AlertModel>> getAllAlerts(){
        return museDao.getAllAlerts();
    }

    public static class InsertAlertAsyncTask extends AsyncTask<AlertModel,Void,Void>{
        private final MuseDao museDao;

        public InsertAlertAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(AlertModel... alertModels) {
            museDao.insertAlert(alertModels[0]);
            return null;
        }
    }

    public static class DeleteAlertAsyncTask extends AsyncTask<AlertModel,Void,Void>{
        private final MuseDao museDao;

        public DeleteAlertAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(AlertModel... alertModels) {
            museDao.deleteAlert(alertModels[0]);
            return null;
        }
    }

    //____________________________________________________________________________________________//
    //custom alerts

    public void insertCustomAlert(CustomAlertModel customAlert){
        new InsertCustomAlertAsyncTask(museDao).doInBackground(customAlert);
    }

    public void deleteCustomAlert(CustomAlertModel customAlert){
        new DeleteCustomAlertAsyncTask(museDao).doInBackground(customAlert);
    }

    public LiveData<List<CustomAlertModel>> getAllCustomAlerts(){
        return museDao.getAllCustomAlerts();
    }

    public static class InsertCustomAlertAsyncTask extends AsyncTask<CustomAlertModel,Void,Void>{
        private final MuseDao museDao;

        public InsertCustomAlertAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(CustomAlertModel... customAlertModels) {
            museDao.insertCustomAlert(customAlertModels[0]);
            return null;
        }
    }

    public static class DeleteCustomAlertAsyncTask extends AsyncTask<CustomAlertModel,Void,Void>{
        private final MuseDao museDao;

        public DeleteCustomAlertAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(CustomAlertModel... customAlertModels) {
            museDao.deleteCustomAlert(customAlertModels[0]);
            return null;
        }
    }

    //____________________________________________________________________________________________//
    //goal

    public void insertGoal(GoalModel goal){
        new InsertGoalAsyncTask(museDao).doInBackground(goal);
    }

    public void deleteGoal(GoalModel goal){
        new DeleteGoalAsyncTask(museDao).doInBackground(goal);
    }

    public LiveData<List<GoalModel>> getAllGoals(){
        return museDao.getAllGoals();
    }

    public static class InsertGoalAsyncTask extends AsyncTask<GoalModel,Void,Void>{
        private final MuseDao museDao;

        public InsertGoalAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(GoalModel... goalModels) {
            museDao.insertGoal(goalModels[0]);
            return null;
        }
    }

    public static class DeleteGoalAsyncTask extends AsyncTask<GoalModel,Void,Void>{
        private final MuseDao museDao;

        public DeleteGoalAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(GoalModel... goalModels) {
            museDao.deleteGoal(goalModels[0]);
            return null;
        }
    }

    //____________________________________________________________________________________________//
    //schedule

    public void insertSchedule(ScheduleModel schedule){
        new InsertScheduleAsyncTask(museDao).doInBackground(schedule);
    }

    public void deleteSchedule(ScheduleModel schedule){
        new DeleteScheduleAsyncTask(museDao).doInBackground(schedule);
    }

    public LiveData<List<ScheduleModel>> getAllSchedules(){
        return museDao.getAllSchedules();
    }

    public static class InsertScheduleAsyncTask extends AsyncTask<ScheduleModel,Void,Void>{
        private final MuseDao museDao;

        public InsertScheduleAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(ScheduleModel... scheduleModels) {
            museDao.insertSchedule(scheduleModels[0]);
            return null;
        }
    }

    public static class DeleteScheduleAsyncTask extends AsyncTask<ScheduleModel,Void,Void>{
        private final MuseDao museDao;

        public DeleteScheduleAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(ScheduleModel... scheduleModels) {
            museDao.deleteSchedule(scheduleModels[0]);
            return null;
        }
    }
}
