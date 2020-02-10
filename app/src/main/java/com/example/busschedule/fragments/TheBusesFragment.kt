package com.example.busschedule.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.busschedule.R
import com.example.busschedule.Util.hideKeyboard
import com.example.busschedule.ViewModels.TheBusesViewModel
import com.example.busschedule.adapters.BusesAdapter
import com.example.busschedule.data.BusStation
import com.example.busschedule.data.Exceptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_the_buses.*
import java.util.*


class TheBusesFragment : Fragment() {

    private lateinit var adapterRV: BusesAdapter
    private lateinit var theBusesViewModel: TheBusesViewModel
    private lateinit var station: BusStation
    private var RESULT: List<BusStation> = emptyList()
    private var busStationCode: String = ""
    private var isLoading = false
    private var exceptions = Exceptions.noException


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapters()
        createTheBusesViewModel()
        createObservers()
        createClickListeners()
    }


    private fun createTheBusesViewModel() {
        theBusesViewModel = ViewModelProviders.of(this).get(TheBusesViewModel::class.java)
    }

    private fun createObservers() {

        theBusesViewModel.isLoading.observe(viewLifecycleOwner, Observer { arg ->
            isLoading = arg
            if (isLoading) {
                buses_progressBar.visibility = View.VISIBLE
            } else {
                buses_progressBar.visibility = View.GONE
            }
        })

        theBusesViewModel.busStations.observe(viewLifecycleOwner, Observer { arg ->
            RESULT = arg
            val adapter: ArrayAdapter<BusStation> = ArrayAdapter(
                context!!,
                android.R.layout.simple_expandable_list_item_1,
                RESULT
            )

            station_actv.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
        theBusesViewModel.exceptions.observe(viewLifecycleOwner, Observer { arg ->
            exceptions = arg
        })
        theBusesViewModel.buses.observe(viewLifecycleOwner, Observer { buses ->
            adapterRV.values = buses

            when (exceptions) {
                Exceptions.noInternet -> {
                    Snackbar
                        .make(
                            buses_fragment,
                            getString(R.string.noInternetConnection),
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                }
                Exceptions.noSchedule -> {
                    Snackbar
                        .make(
                            buses_fragment,
                            getString(R.string.snackbar_NoSchedule),
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                }
                Exceptions.others -> {
                    Snackbar
                        .make(
                            buses_fragment,
                            getString(R.string.error),
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                }
            }
            adapterRV.notifyDataSetChanged()
        })

    }

    private fun createAdapters() {
        adapterRV =
            BusesAdapter(emptyList(), context!!)
        buses_rv.adapter = adapterRV
    }

    private fun createClickListeners() {

        station_actv.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position) as BusStation
            busStationCode = item.stations_codes_yandex_code ?: "null"
            station = item
        })

        stationLocal_btn.setOnClickListener {
            if (!station_actv.text.isEmpty()) {
                val uri = String.format(
                    Locale.getDefault(),
                    "geo:${station.stations_latitude},${station.stations_longitude}"
                )
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                context!!.startActivity(intent)
            }
        }

        searchBus_btn.setOnClickListener {
            if (!station_actv.text.isEmpty()) {
                theBusesViewModel.getBuses(busStationCode)
                hideKeyboard()
            } else {
                Snackbar
                    .make(
                        buses_fragment, getString(R.string.snackbar_ChoiseStation),
                        Snackbar.LENGTH_LONG
                    )
                    .show()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_the_buses, container, false)
    }

}
