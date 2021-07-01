package com.example.muse.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.muse.model.DeviceModel;
import java.util.List;

@Dao
public interface MuseDao {
    @Insert
    void insertDevice(DeviceModel device);

    @Update
    void updateDevice(DeviceModel device);

    @Delete
    void deleteDevice(DeviceModel device);

    @Query("select * from devices_table")
    LiveData<List<DeviceModel>> getAllDevices();

    @Query("select * from devices_table where isAdded=1")
    LiveData<List<DeviceModel>> getDevicesAdded();

    @Query("select * from devices_table where hasGoal=1")
    LiveData<List<DeviceModel>> getDevicesGoals();

    @Query("select * from devices_table where hasAlert=1")
    LiveData<List<DeviceModel>> getDevicesAlerts();

    @Query("select * from devices_table where hasSchedules=1")
    LiveData<List<DeviceModel>> getDevicesSchedules();

    @Query("select * from devices_table where hasCustomAlert=1")
    LiveData<List<DeviceModel>> getDevicesCustomAlerts();

    @Query("select * from devices_table where isAdded=1 and hasGoal=0")
    LiveData<List<DeviceModel>> getDevicesWithoutGoal();

    @Query("select * from devices_table where isAdded=1 and hasSchedules=0")
    LiveData<List<DeviceModel>> getDevicesWithoutSchedule();

    @Query("select * from devices_table where isAdded=1 and hasCustomAlert=0")
    LiveData<List<DeviceModel>> getDevicesWithoutCustomAlert();
}
