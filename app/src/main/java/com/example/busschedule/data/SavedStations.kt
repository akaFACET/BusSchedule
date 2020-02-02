package com.example.busschedule.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedStations(
    @PrimaryKey val nameCode: String,
    val from: String,
    val to: String
)