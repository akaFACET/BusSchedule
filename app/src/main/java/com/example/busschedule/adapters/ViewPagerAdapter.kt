package com.example.busschedule.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.busschedule.fragments.FavoritesFragment
import com.example.busschedule.fragments.ScheduleFragment
import com.example.busschedule.fragments.TheBusesFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ScheduleFragment()
            }
            1 -> {
                TheBusesFragment()
            }
            2 -> {
                FavoritesFragment()
            }
            else -> ScheduleFragment()
        }
    }


}