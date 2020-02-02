package com.example.busschedule.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.busschedule.R
import com.example.busschedule.ViewModels.FavoritesViewModel
import com.example.busschedule.adapters.DetailsAdapter
import kotlinx.android.synthetic.main.details_fragment.*


private const val DATA = "DATA"

class DetailsFragment : Fragment() {

    private lateinit var adapterRv: DetailsAdapter
    private lateinit var viewModel: FavoritesViewModel

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
        val position =
            arguments?.getInt(DATA) ?: throw IllegalArgumentException("Missing data")

        viewModel.savedScheduleByStation.observe(this, Observer { arg ->
            adapterRv.values = arg.get(position).schedule
            adapterRv.notifyDataSetChanged()
        })
        details_rv.adapter = adapterRv

    }




}

