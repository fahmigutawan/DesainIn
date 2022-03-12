package com.example.bccintern3.activity_splashscreen

import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bccintern2.firsttime.OnboardActivity
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso

class SplashScreen: AppCompatActivity() {
    private lateinit var load:LoadActivity
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var dbRef:DbReference
    private lateinit var imageView:ImageView
    private lateinit var splashScreenConstraint:ConstraintLayout
    private lateinit var bg:ImageView


    fun init() {
        splashScreenConstraint = findViewById(R.id.splashscreen_foregroundconstraint)
        load = LoadActivity()
        fbAuth = FirebaseAuth.getInstance()
        dbRef = DbReference()
        imageView = findViewById(R.id.splashscreen_imageview)
        bg = findViewById(R.id.splashscreen_bg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)
        init()
        setLogo()
        cekLogin()
        sendToken()
        setBackground()
    }
    fun setBackground(){
        bg.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                bg.viewTreeObserver.removeOnGlobalLayoutListener(this)

                Picasso
                    .get()
                    .load(R.drawable.background_splashscreen)
                    .resize(bg.width,bg.height)
                    .centerCrop()
                    .into(bg)
            }
        })
    }
    fun setLogo(){
        Picasso
            .get()
            .load(R.drawable.logo)
            .resize(256,256)
            .transform(RoundCornerRect(30f,0f,0f,0f,0f))
            .into(imageView)

    }
    fun cekLogin(){
        if(fbAuth.currentUser==null){
            load.loadActivityComplete(this, HomeActivity::class.java,this,true,3000)
        }
        else{
            val ref = dbRef.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ref.removeEventListener(this)
                    if(snapshot.child("isFirstTime").getValue().toString()=="true"){
                        load.loadActivityComplete(this@SplashScreen,OnboardActivity::class.java,this@SplashScreen,true,2500)
                    }else{
                        load.loadActivityComplete(this@SplashScreen,HomeActivity::class.java,this@SplashScreen,true,3000)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
    fun sendToken(){
        if(fbAuth.currentUser!=null){
            val ref = dbRef.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")

            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                ref.child("fcmToken").setValue(it.result.toString())
            }
        }
    }
}