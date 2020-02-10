package com.example.busschedule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.busschedule.R
import com.example.busschedule.ViewModels.FavoritesViewModel
import com.example.busschedule.adapters.DetailsAdapter
import com.example.busschedule.data.Exceptions
import com.example.busschedule.data.SavedScheduleByStation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.details_fragment.*


private const val DATA = "DATA"

class DetailsFragment : Fragment() {

    private lateinit var adapterRv: DetailsAdapter
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var station: SavedScheduleByStation
    private var isLoading: Boolean = false
    private var exceptions = Exceptions.noException
    private var position = 0

    companion object {
        fun newInstance(
            position: Int
        ): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = Bundle(1).apply {
                putInt(DATA, position)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        details_toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }

        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        adapterRv = DetailsAdapter(emptyList(), context!!)
        position =
            arguments?.getInt(DATA) ?: throw IllegalArgumentException("Missing data")

        viewModel.savedScheduleByStation.observe(viewLifecycleOwner, Observer { arg ->
            adapterRv.values = arg.get(position).schedule
            station = arg.get(position)
            adapterRv.notifyDataSetChanged()
        })

        details_rv.adapter = adapterRv

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { arg ->
            isLoading = arg

            if (!isLoading) {
                swipe_to_refresh.isRefreshing = false
            }
        })

        viewModel.exceptions.observe(viewLifecycleOwner, Observer { arg ->
            exceptions = arg
            when (exceptions) {
                Exceptions.noInternet -> {
                    Snackbar
                        .make(
                            details_fragment,
                            getString(R.string.noInternetConnection),
                            Snackbar.LENGTH_LONG
                        ).show()

                }
                else -> {
                    Snackbar
                        .make(
                            details_fragment,
                            getString(R.string.error),
                            Snackbar.LENGTH_LONG
                        ).show()
                }
            }
        })

        swipe_to_refresh.setOnRefreshListener {
            viewModel.refreshData(position)

        }

    }

}

