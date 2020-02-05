package com.example.busschedule.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busschedule.Repository.ScheduleRepository
import com.example.busschedule.Util.Mapper
import com.example.busschedule.data.BusStation
import com.example.busschedule.data.Exceptions
import com.example.busschedule.network.Segments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StationsViewModel : ViewModel() {

    var busStations = MutableLiveData<List<BusStation>>()
    var schedule = MutableLiveData<List<Segments>>()
    var isLoading = MutableLiveData<Boolean>()
    var exceptions = MutableLiveData<Exceptions>()
    private var stationCodeTo = ""
    private var stationCodeFrom = ""

    init {
        getStations()
    }

    fun saveData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val savedStations = Mapper.mapToSavedStation(schedule.value!!)
                val savedSchedule = Mapper.mapSegmentsToSavedSchedule(schedule.value!!)
                ScheduleRepository.saveData(
                    savedStations, savedSchedule
                )
            }
        }
    }


    @ExperimentalCoroutinesApi
    fun getSchedule(from: String, to: String) {
        if (stationCodeTo != to || stationCodeFrom != from) {
            viewModelScope.launch {
                try {
                    stationCodeFrom = from
                    stationCodeTo = to
                    isLoading.value = true

                    val response = withContext(Dispatchers.IO) {
                        ScheduleRepository.getDataByTwoStations(from, to)
                    }

                    if (response.isEmpty())
                        exceptions.value = Exceptions.noSchedule
                    else
                        exceptions.value = Exceptions.noException

                    schedule.value = response
                    isLoading.value = false

                } catch (ex: java.net.UnknownHostException) {
                    isLoading.value = false
                    stationCodeFrom = ""
                    stationCodeTo = ""
                    exceptions.value = Exceptions.noInternet
                    schedule.value = emptyList()
                } catch (ex: Throwable) {
                    exceptions.value = Exceptions.noSchedule
                    schedule.value = emptyList()
                    isLoading.value = false
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