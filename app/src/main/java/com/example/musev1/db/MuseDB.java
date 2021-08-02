package com.example.musev1.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.musev1.model.AlertModel;
import com.example.musev1.model.CustomAlertModel;
import com.example.musev1.model.DeviceModel;
import com.example.musev1.model.GoalModel;
import com.example.musev1.model.ScheduleModel;

@Database(entities = {DeviceModel.class, AlertModel.class, CustomAlertModel.class, GoalModel.class, ScheduleModel.class}
        , version = 18, exportSchema = false)
public abstract class MuseDB extends RoomDatabase {
    private static MuseDB instance;
    public abstract MuseDao museDao();

    public static synchronized MuseDB getInstance(Context context){
        if(instance==null)
        {
            instance= Room.databaseBuilder(context,MuseDB.class,"Muse_DB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
