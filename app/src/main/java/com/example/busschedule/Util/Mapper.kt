package com.example.busschedule.Util

import com.example.busschedule.data.SavedSchedule
import com.example.busschedule.data.SavedStations
import com.example.busschedule.network.Segments

class Mapper {
    companion object{

        fun mapSegmentsToSavedSchedule(segments:List<Segments>): List<SavedSchedule>{
            val result:MutableList<SavedSchedule> = arrayListOf()
            segments.forEach { result.add(
                mapSegment(
                    it
                )
            ) }
            return result
        }
        private fun mapSegment(segments: Segments): SavedSchedule {
            return SavedSchedule(0,segments.from!!.code+segments.to!!.code,
                segments.thread!!.title!!,
                segments.days!!,
                segments.thread.number!!,
                segments.departure!!
            )
        }

        fun mapToSavedStation(segments: List<Segments>): SavedStations{
            return SavedStations(segments.get(1).from!!.code+segments.get(1).to!!.code,
                segments.get(1).from!!.title!!,segments.get(1).to!!.title!!)
        }
    }

}