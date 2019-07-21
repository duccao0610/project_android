package com.example.dogs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.dogs.fragment.FragmentOne
import com.example.dogs.fragment.FragmentThree
import com.example.dogs.fragment.FragmentTwo

class MyAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    val listTab = arrayListOf<String>("Fragment1","Fragment2","Fragment3")
    val fragmentOne: FragmentOne = FragmentOne()
    val fragmentTwo: FragmentTwo = FragmentTwo()
    val fragmentThree: FragmentThree = FragmentThree()
    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> return fragmentOne
            1 -> return fragmentTwo
            2 -> return fragmentThree
        }
        return null
    }
    override fun getCount(): Int {
        return listTab.size
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return listTab[position]
    }
}