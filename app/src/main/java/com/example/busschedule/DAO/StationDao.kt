package com.example.busschedule.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.busschedule.data.BusStation
import com.example.busschedule.data.Station

@Dao
interface StationDao {
    @Query("SELECT * FROM data ORDER BY title DESC")
    fun getAllStations(): List<Station>

    @Query("SELECT stations_title, stations_codes_yandex_code FROM data WHERE stations_transport_type = 'bus'  ")
    fun getBusStations(): List<BusStation>
}