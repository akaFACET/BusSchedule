package com.example.busschedule.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busschedule.App
import com.example.busschedule.Repository.ScheduleRepository
import com.example.busschedule.data.BusStation
import com.example.busschedule.data.Exceptions
import com.example.busschedule.databases.StationsDB
import com.example.busschedule.network.NetworkModule
import com.example.busschedule.network.Schedule
import com.example.busschedule.network.Segments
import com.example.busschedule.network.Station
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TheBusesViewModel : ViewModel() {

    var busStations = MutableLiveData<List<BusStation>>()
    var buses = MutableLiveData<List<Schedule>>()
    var isLoading = MutableLiveData<Boolean>()
    var exceptions = MutableLiveData<Exceptions>()
    private var stationCode = ""

    init {
        getStations()
    }

    @ExperimentalCoroutinesApi
    fun getBuses(station: String) {
        if (station != stationCode) {
            viewModelScope.launch {
                try {
                    isLoading.value = true
                    stationCode = station
                    val response = withContext(Dispatchers.IO) {
                        ScheduleRepository.getDataByOneStation(station)
                    }
                    exceptions.value = Exceptions.noException
                    isLoading.value = false
                    buses.value = response
                } catch (ex: java.net.UnknownHostException) {
                    isLoading.value = false
                    exceptions.value = Exceptions.noInternet
                    buses.value = emptyList()
                } catch (ex: Throwable) {
                    exceptions.value = Exceptions.noSchedule
                    buses.value = emptyList()

                }
            }

        }
    }

    private fun getStations() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) { ScheduleRepository.getBusStations() }
            busStations.value = response
        }
    }

}