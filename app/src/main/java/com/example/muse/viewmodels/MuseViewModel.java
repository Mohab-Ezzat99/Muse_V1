package com.example.muse.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.muse.model.AuthModel;
import com.example.muse.model.DeviceModel;
import com.example.muse.model.LoginResponseModel;
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

    public Call<ResponseBody> register(AuthModel authModel){
        return repository.register(authModel);
    }

    public Call<LoginResponseModel> login(AuthModel authModel){
        return repository.login(authModel);
    }

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

    public LiveData<List<DeviceModel>> getDevicesSchedules() {
        return repository.getDevicesSchedules();
    }

    public LiveData<List<DeviceModel>> getDevicesCustomAlerts(){
        return repository.getDevicesCustomAlerts();
    }

    public MutableLiveData<Integer> getNewAlerts() {
        return SaveState.getNewAlerts();
    }
}
