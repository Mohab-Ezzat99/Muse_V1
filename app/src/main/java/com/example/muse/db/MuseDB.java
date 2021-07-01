package com.example.muse.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.muse.model.DeviceModel;

@Database(entities = DeviceModel.class,version = 8,exportSchema = false)
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
