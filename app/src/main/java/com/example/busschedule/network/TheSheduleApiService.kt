package com.example.busschedule.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface TheSheduleApiService {
    @GET("search/")
    fun getScheudle(
        @Query("apikey") apikey: String, @Query("from") from: String,
        @Query("to") to: String, @Query("limit") limit: Int
    )
            : Deferred<ScheduleResponse>

    @GET("schedule/")
    fun getBuses(
        @Query("apikey") apikey: String, @Query("station") station: String,
        @Query("limit") limit: Int
    )
            : Deferred<BusesResponse>
}

