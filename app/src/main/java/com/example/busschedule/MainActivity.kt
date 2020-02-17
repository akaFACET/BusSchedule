package com.example.busschedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.busschedule.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private val adapter by lazy { ViewPagerAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = adapter
        createLayoutMediator()
    }

    private fun createLayoutMediator(){
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
