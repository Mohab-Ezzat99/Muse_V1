package com.example.muse.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import com.example.muse.db.MuseDB;
import com.example.muse.db.MuseDao;
import com.example.muse.model.DeviceModel;

import java.util.List;

public class Repository {
    private MuseDB museDB;
    private MuseDao museDao;

    public Repository(Application application) {
        this.museDB = MuseDB.getInstance(application);
        museDao=museDB.museDao();
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

    public void deleteAllDevices(){
        new InsertDeviceAsyncTask(museDao).doInBackground();
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

    public static class DeleteAllDeviceAsyncTask extends AsyncTask<Void,Void,Void>{
        private MuseDao museDao;

        public DeleteAllDeviceAsyncTask(MuseDao museDao) {
            this.museDao = museDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            museDao.deleteAllDevice();
            return null;
        }
    }
}
