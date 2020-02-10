package com.example.busschedule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.R
import com.example.busschedule.Repository.ScheduleRepository
import com.example.busschedule.ViewModels.FavoritesViewModel
import com.example.busschedule.adapters.FavoritesAdapter
import com.example.busschedule.data.SavedScheduleByStation
import com.example.busschedule.experiments.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel

    private lateinit var schedule: List<SavedScheduleByStation>

    private lateinit var adapterRv: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        adapterRv = FavoritesAdapter(
            emptyList(),
            context!!,
            activity!!
        )

        favorites_rv.adapter = adapterRv


        val swipeHelper = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                favoritesViewModel.delete(
                    schedule.get(viewHolder.adapterPosition).stations,
                    schedule.get(viewHolder.adapterPosition).schedule
                )
                adapterRv.notifyItemRemoved(viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(favorites_rv)

        ScheduleRepository.db.getAllSavedSchedule().observe(viewLifecycleOwner, Observer {
            adapterRv.values = it
            schedule = it
            adapterRv.notifyDataSetChanged()
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)

    }
}

