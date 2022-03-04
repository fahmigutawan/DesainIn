package com.example.bccintern3.home.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.DiscoveryFragment
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomeActivity:
    AppCompatActivity(R.layout.home_activity){

    private var fragLayout:Int=0
    private lateinit var botNavbar:BottomNavigationView
    private lateinit var parentFlManager:FragmentManager
    private lateinit var loadFrag:LoadFragment
    private lateinit var homeFragment:HomeFragment
    private lateinit var discoveryFragment:DiscoveryFragment
    //private lateinit var chatFragment:Fragment
    //private lateinit var profile:Fragment


    fun init() {
        fragLayout = R.id.homeactivity_flmanager
        botNavbar = findViewById(R.id.homeactivity_navbar)
        parentFlManager = supportFragmentManager
        loadFrag = LoadFragment()
        homeFragment = HomeFragment(parentFlManager)
        discoveryFragment = DiscoveryFragment(parentFlManager)
        loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        runNavbarListener()
    }
    private fun runSpecialBackHandler(){

    }
    private fun runNavbarListener(){
        botNavbar.setOnItemSelectedListener(object:NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id = item.itemId

                when(id){
                    R.id.navHome->{
                        loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
                    }
                    R.id.navDiscovery->{
                        loadFrag.transfer(parentFlManager,fragLayout,discoveryFragment)
                    }
                    R.id.navChat->{

                    }
                    R.id.navProfile->{

                    }
                }

                return true
            }

        })
    }
    private fun searchData(){

    }
}