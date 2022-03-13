package com.example.bccintern3.activity_home

import android.content.Intent
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
    lateinit var botNavbar:BottomNavigationView
    private lateinit var parentFlManager:FragmentManager
    private lateinit var loadFrag:LoadFragment
    private lateinit var loadAct:LoadActivity
    private lateinit var homeFragment: HomeFragment
    private lateinit var discoveryFragment:DiscoveryFragmentDefault
    private lateinit var chatFragment:ChatFragmentListChat
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var intentData:String
    private var lastTime:Long = 0
    private var lastId:Int = 0
    private lateinit var thisHome:HomeActivity

    fun init() {
        fragLayout = R.id.homeactivity_flmanager
        botNavbar = findViewById(R.id.homeactivity_navbar)
        parentFlManager = supportFragmentManager
        loadFrag = LoadFragment()
        loadAct = LoadActivity()
        thisHome = HomeActivity()
        homeFragment = HomeFragment(parentFlManager,this,this,botNavbar,applicationContext,thisHome)

        loadIntentExtras()
        loadFirstFragment()

        discoveryFragment = DiscoveryFragmentDefault(parentFlManager,this,botNavbar,this,applicationContext,thisHome)
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

                when(id){
                    R.id.navHome->{
                        Handler().postDelayed({
                            loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
                                              },500)
                    }
                    R.id.navDiscovery->{
                        thisHome.setLastId(1)
                        Handler().postDelayed({
                            loadFrag.transfer(parentFlManager,fragLayout,discoveryFragment)
                                              },500)
                    }
                    R.id.navChat->{
                        if(lastTime+1000 > System.currentTimeMillis()){

                        }else{
                            Handler().postDelayed({
                                if(fbAuth.currentUser==null){
                                    loadAct.loadActivityDisposable(thisContext,LoginActivity::class.java,thisContext,false)
                                    Handler().postDelayed({
                                        botNavbar.menu.getItem(3).setChecked(false)
                                        botNavbar.menu.getItem(thisHome.getLastId()).setChecked(true)
                                    },500)
                                }
                                else{
                                    loadFrag.transfer(parentFlManager,fragLayout,chatFragment)
                                    thisHome.setLastId(2)
                                }
                            },500)
                        }
                        lastTime = System.currentTimeMillis()
                    }
                    R.id.navProfile->{
                        if(lastTime+1000 > System.currentTimeMillis()){

                        }else{
                            Handler().postDelayed({
                                if(fbAuth.currentUser==null){
                                    val intent = Intent(thisContext,LoginActivity::class.java)
                                    loadAct.loadActivityDisposable(thisContext,LoginActivity::class.java,thisContext,false)
                                    Handler().postDelayed({
                                        botNavbar.menu.getItem(3).setChecked(false)
                                        botNavbar.menu.getItem(thisHome.getLastId()).setChecked(true)
                                    },500)
                                }
                                else{
                                    loadFrag.transfer(parentFlManager,R.id.homeactivity_flmanager,ProfileFragment(thisContext,thisContext))
                                    thisHome.setLastId(3)
                                }
                            },500)
                        }
                        lastTime = System.currentTimeMillis()
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
                thisHome.setLastId(0)
                loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
            },500)
        }
        else if(intentData==getString(R.string.discovery_fragment)){
            thisHome.setLastId(1)
            botNavbar.menu.getItem(0).setChecked(false)
            botNavbar.menu.getItem(1).setChecked(true)
            Handler().postDelayed({
                loadFrag.transfer(parentFlManager,fragLayout,discoveryFragment)
            },500)
        }
        else if(intentData==getString(R.string.chat_fragment)){
            thisHome.setLastId(2)
            botNavbar.menu.getItem(0).setChecked(false)
            botNavbar.menu.getItem(2).setChecked(true)
            Handler().postDelayed({
                loadFrag.transfer(parentFlManager,fragLayout,chatFragment)
            },500)
        }
        else if(intentData==getString(R.string.user_fragment)){
            thisHome.setLastId(3)
            Handler().postDelayed({
                if(fbAuth.currentUser==null){
                    loadAct.loadActivityDisposable(thisContext,LoginActivity::class.java,thisContext,false)
                    Handler().postDelayed({
                        botNavbar.menu.getItem(3).setChecked(false)
                        botNavbar.menu.getItem(thisHome.getLastId()).setChecked(true)
                    },500)
                }
                else{
                    thisHome.setLastId(0)
                    loadFrag.transfer(parentFlManager,R.id.homeactivity_flmanager,ProfileFragment(thisContext,thisContext))
                }
            },500)
        }
        else{
            Handler().postDelayed({
                loadFrag.transfer(parentFlManager,fragLayout,homeFragment)
            },500)
        }
    }
    public fun setLastId(id:Int){
        lastId=id
    }
    public fun getLastId():Int{
        return lastId
    }
}