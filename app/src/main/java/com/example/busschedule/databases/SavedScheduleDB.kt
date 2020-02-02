package com.example.busschedule.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busschedule.DAO.SavedScheduleDAO
import com.example.busschedule.data.SavedSchedule
import com.example.busschedule.data.SavedStations

@Database(entities = arrayOf(SavedSchedule::class, SavedStations::class), version = 1)
abstract class SavedScheduleDB : RoomDatabase() {

    companion object {
        private const val DB_NAME: String = "SavedSchedule.db"

        var instance: SavedScheduleDB? = null

        fun getInstance(context: Context): SavedScheduleDB {
            if (instance == null) {
                instance = createInstance(context)
            }
            return instance as SavedScheduleDB
        }

        private fun createInstance(context: Context): SavedScheduleDB {
            return Room.databaseBuilder(
                context,
                SavedScheduleDB::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()
        }

    }

    abstract fun getSavedScheduleDAO(): SavedScheduleDAO
}