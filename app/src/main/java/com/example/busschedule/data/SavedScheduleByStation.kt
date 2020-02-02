package com.example.busschedule.data

import androidx.room.Embedded
import androidx.room.Relation

data class SavedScheduleByStation(
    @Embedded
    var stations: SavedStations = SavedStations("", "", ""),
    @Relation(parentColumn = "nameCode", entityColumn = "fromToCode", entity = SavedSchedule::class)
    var schedule: List<SavedSchedule> = emptyList()
)