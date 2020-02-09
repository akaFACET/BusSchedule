package com.example.busschedule.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.busschedule.data.SavedSchedule
import com.example.busschedule.data.SavedScheduleByStation
import com.example.busschedule.data.SavedStations

@Dao
interface SavedScheduleDAO {

    @Transaction
    suspend fun saveData(station: SavedStations, schedule: List<SavedSchedule>) {
        saveStation(station)
        for (sched: SavedSchedule in schedule) {
            saveSchedule(sched)
        }

    }

    @Insert(onConflict = REPLACE)
    fun saveStation(station: SavedStations)

    @Insert(onConflict = REPLACE)
    fun saveSchedule(schedule: SavedSchedule)

    @Transaction
    @Query("SELECT * FROM SavedStations WHERE nameCode = :nameCode")
    fun getScheduleByCodeStation(nameCode: String): SavedScheduleByStation

    @Transaction
    @Query("SELECT * from SavedStations ORDER BY nameCode")
    fun getAllSavedSchedule(): LiveData<List<SavedScheduleByStation>>

    @Transaction
    @Query("SELECT * from SavedStations ORDER BY nameCode")
    fun getAllSavedScheduleList(): List<SavedScheduleByStation>

    @Query("SELECT * FROM SavedSchedule")
    fun getSchedule(): List<SavedSchedule>

    @Delete
    fun deleteStation(savedStations: SavedStations)

    @Delete
    fun deleteSchedule(savedSchedule: List<SavedSchedule>)

    @Transaction
    suspend fun delete(savedStations: SavedStations, savedSchedule: List<SavedSchedule>) {
        deleteStation(savedStations)
        deleteSchedule(savedSchedule)
    }

}