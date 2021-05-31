package com.example.muse.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.muse.model.DeviceModel;
import com.example.muse.repository.Repository;

import java.util.List;

public class MuseViewModel extends AndroidViewModel {
    private Repository repository;

    public MuseViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }

    public void insertDevice(DeviceModel device){
        repository.insertDevice(device);
    }

    public void updateDevice(DeviceModel device){
        repository.updateDevice(device);
    }

    public void deleteDevice(DeviceModel device){
        repository.deleteDevice(device);
    }

    public void deleteAllDevice(){
        repository.deleteAllDevices();
    }

    public LiveData<List<DeviceModel>> getAllDevices(){
        return repository.getAllDevices();
    }

    public LiveData<List<DeviceModel>> getDevicesGoals(){
        return repository.getDevicesGoals();
    }
}
