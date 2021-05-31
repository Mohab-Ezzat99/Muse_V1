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

    @Query("delete from devices_table")
    void deleteAllDevice();

    @Query("select * from devices_table")
    LiveData<List<DeviceModel>> getAllDevices();

    @Query("select * from devices_table where hasGoal=1")
    LiveData<List<DeviceModel>> getDevicesGoals();
}
