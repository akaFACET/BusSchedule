package com.example.busschedule.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "54054e61-e383-4239-bd5c-c64c17d80132"

interface TheSheduleApiService {
    @GET("search/")
    fun getScheudle(
        @Query("apikey") apikey: String = API_KEY, @Query("from") from: String,
        @Query("to") to: String, @Query("limit") limit: Int
    )
            : Deferred<ScheduleResponse>

    @GET("schedule/")
    fun getBuses(
        @Query("apikey") apikey: String = API_KEY, @Query("station") station: String,
        @Query("limit") limit: Int
    )
            : Deferred<BusesResponse>
}

