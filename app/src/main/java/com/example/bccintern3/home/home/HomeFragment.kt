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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.BackHandler
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class HomeFragment(private var flManager: FragmentManager,
                   private var activity:AppCompatActivity,
                   private var thisContext: Context,
                   private var navbar:BottomNavigationView) :
    Fragment(R.layout.home_homefragment),
    BackHandler {


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
                    loadFrag.transfer(flManager,fragLayout,notFilled)
                }else{
                    loadFrag.transfer(flManager,fragLayout,filled)
                }
            }
            override fun afterTextChanged(s: Editable?) {
                if(searchEt.text.toString()==""){
                    loadFrag.transfer(flManager,fragLayout,notFilled)
                }else{
                    loadFrag.transfer(flManager,fragLayout,filled)
                }
            }
        })
    }
    fun runFirstFragment(){
        loadFrag.transfer(flManager,fragLayout,notFilled)
    }
    fun init(view:View){
        filled = HomeFragmentFilled(flManager,activity,view.findViewById(R.id.home_homefragment_search_et),thisContext,navbar)
        notFilled = HomeFragmentNotFilled(flManager,activity,thisContext,navbar)
        searchEt = view.findViewById(R.id.home_homefragment_search_et)
        fragLayout = R.id.home_homefragment_fl
        loadFrag = LoadFragment()
    }
}