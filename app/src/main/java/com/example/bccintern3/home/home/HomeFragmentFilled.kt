package com.example.bccintern3.home.home

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.BackHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class HomeFragmentFilled(private var flManager: FragmentManager,
                         private var activity:AppCompatActivity,
                         private var search:TextInputEditText,
                         private var thisContext: Context,
                         private var navbar:BottomNavigationView):Fragment(R.layout.home_homefragment_filled) {
    private lateinit var handler:BackHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }

    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                handler = context as BackHandler
                search.setText("")
                handler.loadFragment(flManager,R.id.home_homefragment_fl,HomeFragmentNotFilled(flManager,activity,thisContext,navbar))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}