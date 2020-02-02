package com.example.busschedule.data

data class BusStation(
    val stations_title: String?,
    val stations_codes_yandex_code: String?
) {
    override fun toString(): String {
        return stations_title!!.replace("\\", "")
    }
}