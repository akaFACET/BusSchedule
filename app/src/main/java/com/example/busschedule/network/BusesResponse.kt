package com.example.busschedule.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BusesResponse(
    @Json(name = "pagination")
    val pagination: Pagination?,
    @Json(name = "schedule")
    val schedule: List<Schedule>?,
    @Json(name = "station")
    val station: Station?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "interval_schedule")
    val interval_schedule: List<String>?,
    @Json(name = "event")
    val event: String?
)


@JsonClass(generateAdapter = true)
data class Schedule(
    @Json(name = "except_days")
    val except_days: String?,
    @Json(name = "arrival")
    val arrival: String?,
    @Json(name = "thread")
    val thread: Thread?,
    @Json(name = "is_fuzzy")
    val is_fuzzy: Boolean?,
    @Json(name = "days")
    val days: String?,
    @Json(name = "stops")
    val stops: String?,
    @Json(name = "departure")
    val departure: String?,
    @Json(name = "terminal")
    val terminal: String?,
    @Json(name = "platform")
    val platform: String?
)


@JsonClass(generateAdapter = true)
data class Station(
    @Json(name = "code")
    val code: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "station_type")
    val station_type: String?,
    @Json(name = "popular_title")
    val popular_title: String?,
    @Json(name = "short_title")
    val short_title: String?,
    @Json(name = "transport_type")
    val transport_type: String?,
    @Json(name = "station_type_name")
    val station_type_name: String?,
    @Json(name = "type")
    val type: String?
)


