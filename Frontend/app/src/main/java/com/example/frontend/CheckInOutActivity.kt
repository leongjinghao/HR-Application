package com.example.frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityCheckinoutBinding
import com.example.frontend.tabfragments.CheckInOutFragment
import com.example.frontend.tabfragments.HistoryFragment
import com.example.frontend.tabfragments.tabadapter.ViewPagerAdapter

class CheckInOutActivity: AppCompatActivity() {

    lateinit var binding: ActivityCheckinoutBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCheckinoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadTabs()
    }

    private fun loadTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CheckInOutFragment(),"Check In/Out")
        adapter.addFragment(HistoryFragment(), "History")
        binding.viewPagerCheckInOut.adapter=adapter
        binding.tabsCheckInOut.setupWithViewPager(binding.viewPagerCheckInOut)
        binding.tabsCheckInOut.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_login_24)
        binding.tabsCheckInOut.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_history_24)
    }

}