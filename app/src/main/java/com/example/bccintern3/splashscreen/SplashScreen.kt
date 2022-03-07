package com.example.bccintern3.splashscreen

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern2.firsttime.OnboardActivity
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.home.HomeActivity
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.invisiblefunction.LoadActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class SplashScreen: AppCompatActivity() {
    private lateinit var load:LoadActivity
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var dbRef:DbReference
    private lateinit var imageView:ImageView


    fun init() {
        load = LoadActivity()
        fbAuth = FirebaseAuth.getInstance()
        dbRef = DbReference()
        imageView = findViewById(R.id.splashscreen_imageview)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)
        init()
        setLogo()
        cekLogin()
    }
    fun setLogo(){
        Picasso
            .get()
            .load(R.drawable.logo)
            .resize(256,256)
            .transform(RoundCornerRect(60f,0f,0f,0f,0f))
            .into(imageView)

    }
    fun cekLogin(){
        if(fbAuth.currentUser==null){
            load.loadActivityComplete(this, HomeActivity::class.java,this,true,2500)
        }
        else{
            val ref = dbRef.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ref.removeEventListener(this)
                    if(snapshot.child("isFirstTime").getValue().toString()=="true"){
                        load.loadActivityComplete(this@SplashScreen,OnboardActivity::class.java,this@SplashScreen,true,2500)
                    }else{
                        load.loadActivityComplete(this@SplashScreen,HomeActivity::class.java,this@SplashScreen,true,2500)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}