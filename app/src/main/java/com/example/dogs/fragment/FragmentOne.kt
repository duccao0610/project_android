package com.example.dogs.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogs.R

class FragmentOne: Fragment(){
    var mRootView: View?= null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_one,container,false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


}