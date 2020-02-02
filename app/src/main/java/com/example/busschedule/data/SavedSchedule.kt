package com.example.busschedule.data


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [ForeignKey(
        entity = SavedStations::class,
        parentColumns = ["nameCode"],
        childColumns = ["fromToCode"],
        onDelete = CASCADE
    )]
)

data class SavedSchedule(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val fromToCode: String,
    val title: String,
    val days: String,
    val number: String,
    val time: String
)