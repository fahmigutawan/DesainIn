package com.example.bccintern3.home.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.LoadFragment

class HomeFragmentNotFilled(flManager: FragmentManager):Fragment(R.layout.home_homefragment_notfilled) {
    private val flManager=flManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }

    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}