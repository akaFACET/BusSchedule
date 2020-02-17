package com.example.busschedule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.R
import com.example.busschedule.data.SavedSchedule

class DetailsAdapter(
    var values: List<SavedSchedule>,
    val context: Context
) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values.get(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val departure: TextView = itemView.findViewById(R.id.tv_departure)
        private val days: TextView = itemView.findViewById(R.id.tv_days)
        private val number: TextView = itemView.findViewById(R.id.tv_number)
        private val title: TextView = itemView.findViewById(R.id.tv_title)

        fun bind(station: SavedSchedule) {
            departure.text = station.time.subSequence(0, 5)
            days.text = station.days
            number.text = station.number
            title.text = station.title
        }
    }
}