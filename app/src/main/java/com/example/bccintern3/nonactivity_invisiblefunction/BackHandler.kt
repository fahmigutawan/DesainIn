package com.example.bccintern3.nonactivity_invisiblefunction

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface BackHandler {
    fun loadFragment(parentFlManager: FragmentManager,id:Int,receiver:Fragment){
        LoadFragment().transfer(parentFlManager,id,receiver)
    }
    fun exit(context:AppCompatActivity){
        context.finish()
    }
}