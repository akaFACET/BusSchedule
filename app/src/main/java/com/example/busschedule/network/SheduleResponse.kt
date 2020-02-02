package com.example.busschedule.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ScheduleResponse(

    @Json(name = "interval_segments")
    val interval_segments: List<String>?,
    @Json(name = "pagination")
    val pagination: Pagination?,
    @Json(name = "segments")
    val segments: List<Segments>?,
    @Json(name = "search")
    val search: Search?
)

@JsonClass(generateAdapter = true)
data class To(

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


@JsonClass(generateAdapter = true)
data class Segments(

    @Json(name = "except_days")
    val except_days: String?,
    @Json(name = "arrival")
    val arrival: String?,
    @Json(name = "from")
    val from: From?,
    @Json(name = "thread")
    val thread: Thread?,
    @Json(name = "departure_platform")
    val departure_platform: String?,
    @Json(name = "departure")
    val departure: String?,
    @Json(name = "stops")
    val stops: String?,
    @Json(name = "days")
    val days: String?,
    @Json(name = "to")
    val to: To?,
    @Json(name = "duration")
    val duration: Int?,
    @Json(name = "departure_terminal")
    val departure_terminal: String?,
    @Json(name = "arrival_terminal")
    val arrival_terminal: String?,
    @Json(name = "start_date")
    val start_date: String?,
    @Json(name = "arrival_platform")
    val arrival_platform: String?
)

@JsonClass(generateAdapter = true)
data class Search(

    @Json(name = "date")
    val date: String?,
    @Json(name = "to")
    val to: To?,
    @Json(name = "from")
    val from: From?
)


@JsonClass(generateAdapter = true)
data class Pagination(

    @Json(name = "total")
    val total: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "offset")
    val offset: Int?
)


@JsonClass(generateAdapter = true)
data class From(
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


@JsonClass(generateAdapter = true)
data class Transport_subtype(

    @Json(name = "color")
    val color: String?,
    @Json(name = "code")
    val code: String?,
    @Json(name = "title")
    val title: String?
)


@JsonClass(generateAdapter = true)
data class Thread(

    @Json(name = "uid")
    val uid: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "number")
    val number: String?,
    @Json(name = "short_title")
    val short_title: String?,
    @Json(name = "thread_method_link")
    val thread_method_link: String?,
    @Json(name = "carrier")
    val carrier: String?,
    @Json(name = "transport_type")
    val transport_type: String?,
    @Json(name = "vehicle")
    val vehicle: String?,
    @Json(name = "transport_subtype")
    val transport_subtype: Transport_subtype?,
    @Json(name = "express_type")
    val express_type: String?
)