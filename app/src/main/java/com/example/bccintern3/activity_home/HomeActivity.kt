package com.example.bccintern3.activity_home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.bccintern2.home.ProfileFragment
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.chat.ChatFragmentListChat
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentDefault
import com.example.bccintern3.activity_home.home.HomeFragment
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.example.bccintern3.activity_login.LoginActivity
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
    private lateinit var chatFragment:ChatFragmentListChat
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var intentData:String
    var lastId=0

    fun init() {
        fragLayout = R.id.homeactivity_flmanager
        botNavbar = findViewById(R.id.homeactivity_navbar)
        parentFlManager = supportFragmentManager
        loadFrag = LoadFragment()
        loadAct = LoadActivity()
        homeFragment = HomeFragment(parentFlManager,this,this,botNavbar,applicationContext)

        loadIntentExtras()
        loadFirstFragment()

        discoveryFragment = DiscoveryFragmentDefault(parentFlManager,this,botNavbar,this,applicationContext)
        chatFragment = ChatFragmentListChat(thisContext,this,botNavbar)

        fbAuth = FirebaseAuth.getInstance()
        val tmp = intent.extras?.getString("DARI SINI")
        Log.e("ASDASDASD",tmp.toString())

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
                lastId=0

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
                        Handler().postDelayed({
                            loadFrag.transfer(parentFlManager,fragLayout,chatFragment)
                        },500)
                    }
                    R.id.navProfile->{
                        Handler().postDelayed({
                            if(fbAuth.currentUser==null){
                                loadAct.loadActivityDisposable(thisContext,LoginActivity::class.java,thisContext,false)
                                Handler().postDelayed({
                                    botNavbar.menu.getItem(3).setChecked(false)
                                    botNavbar.menu.getItem(0).setChecked(true)
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
    private fun loadIntentExtras(){
        intentData = intent.extras?.getString(getString(R.string.last_frag)).toString()
    }
    private fun loadFirstFragment(){
        if(intentData==getString(R.string.home_fragment)){
            Handler().postDelayed({
                loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
            },500)
        }
        else if(intentData==getString(R.string.discovery_fragment)){
            botNavbar.menu.getItem(0).setChecked(false)
            botNavbar.menu.getItem(1).setChecked(true)
            Handler().postDelayed({
                loadFrag.transfer(parentFlManager,fragLayout,discoveryFragment)
            },500)
        }
        else if(intentData==getString(R.string.chat_fragment)){
            botNavbar.menu.getItem(0).setChecked(false)
            botNavbar.menu.getItem(2).setChecked(true)
            Handler().postDelayed({
                loadFrag.transfer(parentFlManager,fragLayout,chatFragment)
            },500)
        }
        else if(intentData==getString(R.string.user_fragment)){
            Handler().postDelayed({
                if(fbAuth.currentUser==null){
                    loadAct.loadActivityDisposable(thisContext,LoginActivity::class.java,thisContext,false)
                    Handler().postDelayed({
                        botNavbar.menu.getItem(3).setChecked(false)
                        botNavbar.menu.getItem(0).setChecked(true)
                    },500)
                }
                else{
                    loadFrag.transfer(parentFlManager,R.id.homeactivity_flmanager,ProfileFragment(thisContext,thisContext))
                }
            },500)
        }
        else{
            lastId=0
            Handler().postDelayed({
                loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
            },500)
        }
    }
}