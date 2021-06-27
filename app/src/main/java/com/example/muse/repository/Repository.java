package com.example.muse.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Update;

import com.example.muse.db.MuseDB;
import com.example.muse.db.MuseDao;
import com.example.muse.model.DeviceModel;
import com.example.muse.model.RegisterModel;
import com.example.muse.network.ApiService;
import com.example.muse.network.RetrofitBuilder;
import com.example.muse.utility.SaveState;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

public class Repository {
    private ApiService apiService;
    private MuseDB museDB;
    private MuseDao museDao;

    public Repository(Application application) {
        this.museDB = MuseDB.getInstance(application);
        museDao=museDB.museDao();
    }

    public Call<JSONObject> registerUser(RegisterModel registerModel){
        apiService= RetrofitBuilder.getInstance(RetrofitBuilder.BASE_URL).create(ApiService.class);
        return apiService.registerUser(registerModel);
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

    public LiveData<List<DeviceModel>> getDevicesGoals(){
        return museDao.getDevicesGoals();
    }

    public LiveData<List<DeviceModel>> getDevicesAlerts(){
        return museDao.getDevicesAlerts();
    }

    public static class InsertDeviceAsyncTask extends AsyncTask<DeviceModel,Void,Void>{
        private MuseDao museDao;

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
        private MuseDao museDao;

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
        private MuseDao museDao;

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
