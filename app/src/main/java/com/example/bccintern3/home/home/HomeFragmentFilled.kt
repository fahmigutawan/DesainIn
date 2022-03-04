package com.example.bccintern3.home.home

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.LoadFragment

class HomeFragmentFilled(flManager: FragmentManager):Fragment(R.layout.home_homefragment_filled) {
    val flManager = flManager
    lateinit var backHandler:BackHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }

    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                backHandler = context as BackHandler
                backHandler.handler(flManager)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
    interface BackHandler{
        fun handler(flManager: FragmentManager){
            LoadFragment().transfer(flManager,R.id.home_homefragment_fl,HomeFragmentNotFilled(flManager))
        }
    }
}