package com.example.frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.adapters.ViewPagerAdapter
import com.example.frontend.databinding.ActivityCheckinoutBinding
import com.example.frontend.tabfragments.CheckInOutFragment
import com.example.frontend.tabfragments.HistoryFragment

class CheckInOutActivity: AppCompatActivity() {

    lateinit var binding: ActivityCheckinoutBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCheckinoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadTabs()
    }

    //Function to initialize the tabs
    private fun loadTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        //Add the Check In/Out fragment to the adapter
        adapter.addFragment(CheckInOutFragment(),"Check In/Out")
        //Add the History fragment to the adapter
        adapter.addFragment(HistoryFragment(), "History")
        binding.viewPagerCheckInOut.adapter=adapter
        //Display fragments using ViewPager
        binding.tabsCheckInOut.setupWithViewPager(binding.viewPagerCheckInOut)
        //Add icons to the tabs
        binding.tabsCheckInOut.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_login_24)
        binding.tabsCheckInOut.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_history_24)
    }

}