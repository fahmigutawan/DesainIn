package com.example.bccintern3.home

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.FragmentManager
import com.example.bccintern2.home.ProfileFragment
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
import com.squareup.picasso.Picasso

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

        botNavbar.itemIconTintList=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        init()
        runNavbarListener()
    }
    private fun runNavbarListener(){

        botNavbar.setOnItemSelectedListener(object:NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id = item.itemId
                var lastId=0

                when(id){
                    R.id.navHome->{
                        lastId=0
                        Handler().postDelayed({
                            loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
                                              },500)
                    }
                    R.id.navDiscovery->{
                        lastId=1
                        Handler().postDelayed({
                            loadFrag.transfer(parentFlManager,fragLayout,discoveryFragment)
                                              },500)
                    }
                    R.id.navChat->{
                        lastId=2

                    }
                    R.id.navProfile->{
                        Handler().postDelayed({
                            if(fbAuth.currentUser==null){
                                loadAct.loadActivityDisposable(thisContext,LoginActivity::class.java,thisContext,false)
                                Handler().postDelayed({
                                    botNavbar.menu.getItem(3).setChecked(false)
                                    botNavbar.menu.getItem(lastId).setChecked(true)
                                },500)
                            }
                            else{
                                loadFrag.transfer(parentFlManager,R.id.homeactivity_flmanager,ProfileFragment(thisContext,thisContext))
                            }
                                              },500)
                    }
                }
                return true
            }

        })
    }
}