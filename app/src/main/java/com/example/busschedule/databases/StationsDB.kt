package com.example.busschedule.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busschedule.DAO.StationDao
import com.example.busschedule.data.Station

@Database(entities = [Station::class], version = 1, exportSchema = false)
abstract class StationsDB : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "stations"
        private const val DATABASE_DIR = "database/sqlite.db"


        fun getInstance(context: Context): StationsDB {
            return Room
                .databaseBuilder(context, StationsDB::class.java, DATABASE_NAME)
                .createFromAsset(DATABASE_DIR)
                .build()
        }
    }

    abstract fun getStations(): StationDao

}