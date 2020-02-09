package com.example.busschedule.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busschedule.Repository.ScheduleRepository
import com.example.busschedule.Util.Mapper
import com.example.busschedule.data.Exceptions
import com.example.busschedule.data.SavedSchedule
import com.example.busschedule.data.SavedScheduleByStation
import com.example.busschedule.data.SavedStations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel : ViewModel() {

    var savedScheduleByStation = MutableLiveData<List<SavedScheduleByStation>>()
    var isLoading = MutableLiveData<Boolean>()
    var exceptions = MutableLiveData<Exceptions>()

    init {
        getDataFromDB()
    }

    @ExperimentalCoroutinesApi
    fun refreshData(position: Int) {

        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {

                    val stations = ScheduleRepository
                        .getDataByTwoStations(
                            savedScheduleByStation.value!!.get(position).stations.nameCode
                                .substring(0, 8),
                            savedScheduleByStation.value!!.get(position).stations.nameCode
                                .substring(8, 16)
                        )
                    val savedStations = Mapper.mapToSavedStation(stations)
                    val savedSchedule = Mapper.mapSegmentsToSavedSchedule(stations)
                    if (!stations.isEmpty()) {
                        ScheduleRepository.saveData(savedStations, savedSchedule)
                    } else {
                        exceptions.value = Exceptions.noInternet
                    }
                    getDataFromDB()
                }
                isLoading.value = false
            } catch (ex: Throwable) {
                exceptions.value = Exceptions.noInternet
                isLoading.value = false
            }
        }
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