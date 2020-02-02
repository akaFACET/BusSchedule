package com.example.busschedule.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.example.busschedule.R
import com.example.busschedule.Util.hideKeyboard
import com.example.busschedule.ViewModels.StationsViewModel
import com.example.busschedule.adapters.StationsAdapter
import com.example.busschedule.data.BusStation
import com.example.busschedule.data.Exceptions
import com.example.busschedule.network.Segments
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleFragment : Fragment() {

    private lateinit var stationsViewModel: StationsViewModel
    private var RESULT: List<BusStation> = emptyList()
    private lateinit var adapterRv: StationsAdapter
    private lateinit var schedule: List<Segments>
    private var exceptions = Exceptions.noException
    private var isLoading = false
    private var stationCodeFrom = ""
    private var stationCodeTo = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapters()
        createStationViewModel()
        createObservers()
        createClickListeners()

    }

    private fun createAdapters() {

        schedule = emptyList()

        adapterRv = StationsAdapter(
            emptyList(),
            context!!
        )
        schedule_rv.adapter = adapterRv
    }

    private fun createStationViewModel() {
        stationsViewModel = ViewModelProviders.of(this).get(StationsViewModel::class.java)
    }

    private fun createObservers() {

        stationsViewModel.exceptions.observe(this, Observer { arg ->
            exceptions = arg
        })


        stationsViewModel.busStations.observe(this, Observer { arg ->
            RESULT = arg
            val adapter: ArrayAdapter<BusStation> = ArrayAdapter(
                context!!,
                android.R.layout.simple_expandable_list_item_1,
                RESULT
            )

            from_actv.setAdapter(adapter)
            to_actv.setAdapter(adapter)
            adapter.notifyDataSetChanged()

        })

        stationsViewModel.isLoading.observe(this, Observer { arg ->
            isLoading = arg
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        stationsViewModel.schedule.observe(this, Observer { arg ->
            schedule = arg
        })




        stationsViewModel.schedule.observe(this, Observer { stations ->
            adapterRv.values = stations

            when (exceptions) {
                Exceptions.noSchedule -> {
                    Snackbar
                        .make(
                            schedule_fragment,
                            getString(R.string.snackbar_NoSchedule),
                            Snackbar.LENGTH_LONG
                        ).show()
                }
                Exceptions.noInternet -> {
                    Snackbar
                        .make(
                            schedule_fragment,
                            getString(R.string.noInternetConnection),
                            Snackbar.LENGTH_LONG
                        ).show()
                }
                Exceptions.others -> {
                    Snackbar
                        .make(
                            schedule_fragment,
                            getString(R.string.error),
                            Snackbar.LENGTH_LONG
                        ).show()

                }
            }

            adapterRv.notifyDataSetChanged()
        })
    }

    private fun createClickListeners() {
        save_btn.setOnClickListener {
            if (!schedule.isEmpty()) {
                stationsViewModel.saveData()
            }
        }

        search_btn.setOnClickListener {
            if (!from_actv.text.toString().isEmpty() && !to_actv.text.toString().isEmpty()) {
                stationsViewModel.getSchedule(stationCodeFrom, stationCodeTo)
                hideKeyboard()
            } else {
                Snackbar
                    .make(
                        schedule_fragment,
                        getString(R.string.snackbar_ChoiseStation),
                        Snackbar.LENGTH_LONG
                    )
                    .show()
            }
        }

        from_actv.setOnItemClickListener(AdapterView.OnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position) as BusStation
            stationCodeFrom = item.stations_codes_yandex_code ?: "null"
        })

        to_actv.setOnItemClickListener(AdapterView.OnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position) as BusStation
            stationCodeTo = item.stations_codes_yandex_code ?: "null"
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

}
