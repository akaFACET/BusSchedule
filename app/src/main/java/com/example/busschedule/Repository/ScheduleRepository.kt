package com.example.busschedule.Repository

import com.example.busschedule.App
import com.example.busschedule.BuildConfig
import com.example.busschedule.data.BusStation
import com.example.busschedule.data.SavedSchedule
import com.example.busschedule.data.SavedScheduleByStation
import com.example.busschedule.data.SavedStations
import com.example.busschedule.databases.SavedScheduleDB
import com.example.busschedule.databases.StationsDB
import com.example.busschedule.network.NetworkModule
import com.example.busschedule.network.Schedule
import com.example.busschedule.network.Segments
import kotlinx.coroutines.ExperimentalCoroutinesApi

object ScheduleRepository {

    private val API_KEY = BuildConfig.API_KEY
    private val LIMIT = 300

    val db = SavedScheduleDB.getInstance(App.instance).getSavedScheduleDAO()


    suspend fun saveData(savedStations: SavedStations, savedSchedule: List<SavedSchedule>) {
        db.saveData(
            savedStations, savedSchedule
        )
    }

    @ExperimentalCoroutinesApi
    suspend fun getDataByOneStation(station:String): List<Schedule>{
        val theSheduleApiService = NetworkModule.theSheduleApiService
        val result = theSheduleApiService.getBuses(API_KEY,station, LIMIT)
        result.await()
        return result.getCompleted().schedule?: emptyList()
    }

    @ExperimentalCoroutinesApi
    suspend fun getDataByTwoStations(from: String, to: String): List<Segments> {
        val theSheduleApiService = NetworkModule.theSheduleApiService
        val result = theSheduleApiService.getScheudle(API_KEY, from, to, LIMIT)
        result.await()
        return result.getCompleted().segments ?: emptyList()
    }


    fun getBusStations(): List<BusStation> {
        val result = StationsDB.getInstance(App.instance).getStations().getBusStations()
        return result
    }



    fun getAllSavedSchedule():List<SavedScheduleByStation>{
        return db.getAllSavedScheduleList()
    }

    suspend fun deleteStations(savedStations: SavedStations, savedSchedule: List<SavedSchedule>){
        db.delete(savedStations, savedSchedule)
    }

}