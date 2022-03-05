package com.example.bccintern3.invisiblefunction

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText

interface BackHandler {
    fun loadFragment(parentFlManager: FragmentManager,id:Int,receiver:Fragment){
        LoadFragment().transfer(parentFlManager,id,receiver)
    }
    fun exit(context:AppCompatActivity){
        context.finish()
    }
}