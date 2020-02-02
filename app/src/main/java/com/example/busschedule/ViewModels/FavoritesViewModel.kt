package com.example.busschedule.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busschedule.App
import com.example.busschedule.Repository.ScheduleRepository
import com.example.busschedule.data.SavedSchedule
import com.example.busschedule.data.SavedScheduleByStation
import com.example.busschedule.data.SavedStations
import com.example.busschedule.databases.SavedScheduleDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel : ViewModel() {

    var savedScheduleByStation = MutableLiveData<List<SavedScheduleByStation>>()

    init {
        getDataFromDB()
    }



    private fun getDataFromDB() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                ScheduleRepository.getAllSavedSchedule()
            }
            savedScheduleByStation.value = result
        }
    }

    fun delete(savedStations: SavedStations, savedSchedule: List<SavedSchedule>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ScheduleRepository.deleteStations(savedStations, savedSchedule)
            }
        }
    }
}