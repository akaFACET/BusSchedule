package com.example.busschedule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.R
import com.example.busschedule.Util.extensions.onClick
import com.example.busschedule.data.SavedScheduleByStation
import com.example.busschedule.fragments.DetailsFragment

class FavoritesAdapter(
    var values: List<SavedScheduleByStation>,
    val context: Context,
    val activity: FragmentActivity
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.favorites_item_row, parent, false)
        return ViewHolder(itemView).onClick { position, type ->
            val fragment = DetailsFragment.newInstance(position)
            activity.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_container, fragment)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values.get(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val from: TextView = itemView.findViewById(R.id.tv_from)
        private val to: TextView = itemView.findViewById(R.id.tv_to)


        fun bind(station: SavedScheduleByStation) {
            from.text = station.stations.from
            to.text = station.stations.to
        }
    }
}