package com.example.muse.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.muse.db.MuseDB;
import com.example.muse.db.MuseDao;
import com.example.muse.model.AuthModel;
import com.example.muse.model.DeviceModel;
import com.example.muse.model.LoginResponseModel;
import com.example.muse.network.ApiService;
import com.example.muse.network.RetrofitBuilder;

import java.util.List;

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

    public Call<ResponseBody> register(AuthModel authModel) {
        apiService = RetrofitBuilder.getInstance().create(ApiService.class);
        return apiService.register(authModel);
    }

    public Call<LoginResponseModel> login(AuthModel authModel){
        apiService= RetrofitBuilder.getInstance().create(ApiService.class);
        return apiService.login(authModel);
    }

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
