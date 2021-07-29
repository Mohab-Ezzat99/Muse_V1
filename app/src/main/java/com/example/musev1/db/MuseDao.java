package com.example.musev1.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.musev1.model.AlertModel;
import com.example.musev1.model.CustomAlertModel;
import com.example.musev1.model.DeviceModel;
import com.example.musev1.model.GoalModel;
import com.example.musev1.model.ScheduleModel;

import java.util.List;

@Dao
public interface MuseDao {
    //devices
    @Insert
    long insertDevice(DeviceModel device);

    @Update
    void updateDevice(DeviceModel device);

    @Delete
    void deleteDevice(DeviceModel device);

    @Query("select * from devices_table")
    LiveData<List<DeviceModel>> getAllDevices();

    @Query("select * from devices_table where id=:deviceId")
    LiveData<DeviceModel> getDevice(Long deviceId);

    //____________________________________________________________________________________________//
    //alerts
    @Insert
    void insertAlert(AlertModel alert);

    @Delete
    void deleteAlert(AlertModel alert);

    @Query("select * from alerts_table")
    LiveData<List<AlertModel>> getAllAlerts();

    //____________________________________________________________________________________________//
    //custom alerts
    @Insert
    void insertCustomAlert(CustomAlertModel customAlert);

    @Delete
    void deleteCustomAlert(CustomAlertModel customAlert);

    @Query("select * from customAlerts_table")
    LiveData<List<CustomAlertModel>> getAllCustomAlerts();

    //____________________________________________________________________________________________//
    //goals
    @Insert
    void insertGoal(GoalModel goal);

    @Delete
    void deleteGoal(GoalModel goal);

    @Query("select * from goals_table")
    LiveData<List<GoalModel>> getAllGoals();

    //____________________________________________________________________________________________//
    //schedule
    @Insert
    void insertSchedule(ScheduleModel schedule);

    @Delete
    void deleteSchedule(ScheduleModel schedule);

    @Query("select * from schedules_table")
    LiveData<List<ScheduleModel>> getAllSchedules();
}