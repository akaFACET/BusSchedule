package com.example.busschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.busschedule.adapters.ViewPagerAdapter
import com.example.busschedule.data.Station
import com.example.busschedule.databases.StationsDB
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private val adapter by lazy { ViewPagerAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = adapter

        createLayoutMediator()


    }
    private fun createLayoutMediator(){
        tabLayout
        TabLayoutMediator(tabLayout,viewPager,
            TabLayoutMediator.TabConfigurationStrategy{tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Расписание"

                        tab.setIcon(R.drawable.ic_list_black_24dp)
                    }
                    1 -> {
                        tab.text = "Маршруты"
                        tab.setIcon(R.drawable.ic_directions_bus_black_24dp)
                    }
                    2 -> {
                        tab.text = "Избранное"
                        tab.setIcon(R.drawable.ic_favorite_black_24dp)
                    }
                }
            }).attach()
    }

}
