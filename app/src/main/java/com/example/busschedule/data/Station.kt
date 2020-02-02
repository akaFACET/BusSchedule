package com.example.busschedule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class Station(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "codes_yandex_code")
    val yandex_code: String?,
    @ColumnInfo(name = "stations_direction")
    val station_direction: String?,
    @ColumnInfo(name = "stations_codes_esr_code")
    val station_esr_code: String?,
    @ColumnInfo(name = "stations_codes_yandex_code")
    val station_yandex_code: String?,
    @ColumnInfo(name = "stations_station_type")
    val station_type: String?,
    @ColumnInfo(name = "stations_title")
    val station_title: String?,
    @ColumnInfo(name = "stations_longitude")
    val station_longitude: String?,
    @ColumnInfo(name = "stations_latitude")
    val station_latitude: String?,
    @ColumnInfo(name = "stations_transport_type")
    val station_transport_type: String?
)