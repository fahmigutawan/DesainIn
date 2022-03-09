package com.example.bccintern3.nonactivity_invisiblefunction

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class LoadFragment {
    public fun transfer(flManager: FragmentManager,id:Int,receiver:Fragment){
        flManager.beginTransaction().replace(id,receiver).commit()
    }
}