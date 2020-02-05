package com.example.busschedule.data

data class BusStation(
    val stations_title: String?,
    val stations_codes_yandex_code: String?,
    val stations_longitude: String?,
    val stations_latitude: String?
) {
    override fun toString(): String {
        return stations_title!!.replace("\\", "")
    }

}