package com.example.bccintern3.home.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.textfield.TextInputEditText

class HomeFragment(flManager: FragmentManager)
    : Fragment(R.layout.home_homefragment){
    private val flManager=flManager
    private lateinit var searchEt:TextInputEditText
    private var fragLayout:Int=0
    private lateinit var loadFrag:LoadFragment
    private lateinit var notFilled: HomeFragmentNotFilled
    private lateinit var filled: HomeFragmentFilled

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        runFirstFragment()
        runSearchListener()
    }
    fun runSearchListener(){
        searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(searchEt.text.toString()==""){
                    loadFrag.transfer(childFragmentManager,fragLayout,notFilled)
                }else{
                    loadFrag.transfer(childFragmentManager,fragLayout,filled)
                }
            }
            override fun afterTextChanged(s: Editable?) {
                if(searchEt.text.toString()==""){
                    loadFrag.transfer(childFragmentManager,fragLayout,notFilled)
                }else{
                    loadFrag.transfer(childFragmentManager,fragLayout,filled)
                }
            }
        })
    }
    fun runFirstFragment(){
        loadFrag.transfer(childFragmentManager,fragLayout,notFilled)
    }
    fun init(view:View) {
        filled = HomeFragmentFilled(childFragmentManager)
        notFilled = HomeFragmentNotFilled(childFragmentManager)
        searchEt = view.findViewById(R.id.home_homefragment_search_et)
        fragLayout = R.id.home_homefragment_fl
        loadFrag = LoadFragment()
    }
}