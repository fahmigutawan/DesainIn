package com.example.bccintern3.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.DiscoveryFragmentDefault
import com.example.bccintern3.home.home.HomeFragment
import com.example.bccintern3.invisiblefunction.BackHandler
import com.example.bccintern3.invisiblefunction.LoadActivity
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.example.bccintern3.loginactivity.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity:
    AppCompatActivity(),
    BackHandler{

    private var fragLayout:Int=0
    private var thisContext=this
    private lateinit var botNavbar:BottomNavigationView
    private lateinit var parentFlManager:FragmentManager
    private lateinit var loadFrag:LoadFragment
    private lateinit var loadAct:LoadActivity
    private lateinit var homeFragment: HomeFragment
    private lateinit var discoveryFragment:DiscoveryFragmentDefault
    //private lateinit var chatFragment:Fragment
    //private lateinit var profile:Fragment
    private lateinit var fbAuth:FirebaseAuth

    fun init() {
        fragLayout = R.id.homeactivity_flmanager
        botNavbar = findViewById(R.id.homeactivity_navbar)
        parentFlManager = supportFragmentManager
        loadFrag = LoadFragment()
        loadAct = LoadActivity()
        homeFragment = HomeFragment(parentFlManager,this,this,botNavbar)
        discoveryFragment = DiscoveryFragmentDefault(parentFlManager,this,botNavbar,this)
        loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
        fbAuth = FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
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
                        if(fbAuth.currentUser==null){
                            loadAct.loadActivityDisposable(thisContext,LoginActivity::class.java,thisContext,true)
                        }
                        else{

                        }
                    }
                }

                return true
            }

        })
    }
    private fun searchData(){

    }
}