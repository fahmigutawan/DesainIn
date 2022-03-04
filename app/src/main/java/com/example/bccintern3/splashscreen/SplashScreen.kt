package com.example.bccintern3.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern3.R
import com.example.bccintern3.home.home.HomeActivity
import com.example.bccintern3.invisiblefunction.LoadActivity

class SplashScreen: AppCompatActivity() {
    private lateinit var load:LoadActivity


    init {
        load = LoadActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)

        load.loadActivityComplete(this, HomeActivity::class.java,this,true,2500)
    }
}