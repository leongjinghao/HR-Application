package com.example.frontend.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    //ArrayList of fragments for different tabs
    private val fragmentList = ArrayList<Fragment>()
    //ArrayList of string to label tabs
    private val fragmentTitleList = ArrayList<String>()

    //Get the number of fragments
    override fun getCount(): Int {
        return fragmentList.size
    }

    //For retrieving the fragment
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    //For retrieving the tab name
    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    //Function to add new fragments to tabs
    fun addFragment(fragment:Fragment, title:String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

}