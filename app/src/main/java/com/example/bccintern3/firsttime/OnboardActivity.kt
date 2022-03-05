package com.example.bccintern2.firsttime

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern3.R
import com.example.bccintern3.home.HomeActivity
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.invisiblefunction.LoadActivity
import com.example.bccintern3.loginactivity.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class OnboardActivity:AppCompatActivity() {
    private var fragmentNumber=1
    private var manager = supportFragmentManager
    private lateinit var nxtBtn:Button
    private lateinit var passBtn:Button
    private lateinit var signoutBtn:Button
    private lateinit var loadActivity: LoadActivity
    private var point1 = PointOne()
    private var point2 = PointTwo()
    private var point3 = PointThree()
    private lateinit var mGoogleSignInClient:GoogleSignInClient
    private lateinit var dbRef: DbReference
    private lateinit var fbAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboard_activity)

        //google signin option untk logout
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //deklarasi
        nxtBtn = findViewById(R.id.firsttimelogin_next_btn)
        passBtn = findViewById(R.id.firsttimelogin_pass_btn)
        signoutBtn = findViewById(R.id.firsttimelogin_signout_btn)
        dbRef = DbReference()
        fbAuth = FirebaseAuth.getInstance()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        //fragment pertama
        manager.beginTransaction()
            .setCustomAnimations(R.anim.set_slidein_left_to_right,R.anim.set_slideout_left_to_right,R.anim.set_slidein_right_to_left,R.anim.set_slideout_right_to_left)
            .replace(R.id.firsttimelogin_fragment,point1)
            .addToBackStack(null)
            .commit()

        //fragment kedua dan ketiga
        passBtn.setOnClickListener {
            storeFirstTimePassed()
            loadActivity(HomeActivity::class.java,true)
        }
        nxtBtn.setOnClickListener {
            if(getFragmentNumber()<=3){
                //menghindari fragmentNumber terus bertambah saat terjadi bug
                setFragmentNumber(1)
            }

            when(getFragmentNumber()){
                2 -> {
                    manager.beginTransaction()
                        .setCustomAnimations(R.anim.set_slidein_left_to_right,R.anim.set_slideout_left_to_right,R.anim.set_slidein_right_to_left,R.anim.set_slideout_right_to_left)
                        .replace(R.id.firsttimelogin_fragment,point2)
                        .addToBackStack(null)
                        .commit()
                }

                3 -> {
                    manager.beginTransaction()
                        .setCustomAnimations(R.anim.set_slidein_right_to_left,R.anim.set_slideout_right_to_left,R.anim.set_slidein_left_to_right,R.anim.set_slideout_left_to_right)
                        .replace(R.id.firsttimelogin_fragment,point3)
                        .addToBackStack(null)
                        .commit()
                    nxtBtn.text="Mulai"
                }
                 
                4 -> {
                    storeFirstTimePassed()
                    loadActivity(HomeActivity::class.java,true)
                }
            }
        }
        signoutBtn.setOnClickListener {
            signOut()
            loadActivity(LoginActivity::class.java,true)
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        when(getFragmentNumber()){
            3 ->{
                setFragmentNumber(-1)
                manager.beginTransaction()
                    .setCustomAnimations(R.anim.set_slidein_left_to_right,R.anim.set_slideout_left_to_right,R.anim.set_slidein_right_to_left,R.anim.set_slideout_right_to_left)
                    .replace(R.id.firsttimelogin_fragment,point2)
                    .addToBackStack(null)
                    .commit()
                nxtBtn.text="Selanjutnya"
            }
            2 ->{
                setFragmentNumber(-1)
                manager.beginTransaction()
                    .setCustomAnimations(R.anim.set_slidein_right_to_left,R.anim.set_slideout_right_to_left,R.anim.set_slidein_left_to_right,R.anim.set_slideout_left_to_right)
                    .replace(R.id.firsttimelogin_fragment,point1)
                    .addToBackStack(null)
                    .commit()
            }
            1 ->{
                this.finish()
            }
        }
    }

    fun loadActivity(cls:Class<*>,dispose:Boolean){
        val intent= Intent(this,cls)
        Handler().postDelayed({
            startActivity(intent)
            if(dispose){
                this.finish()
            }
        },100)
    }
    fun storeFirstTimePassed(){
        dbRef.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile").child("isFirstTime").setValue("false")
    }
    fun setFragmentNumber(number:Int){
        fragmentNumber+=number
    }
    fun getFragmentNumber():Int{
        return fragmentNumber
    }
    private fun signOut() {
        fbAuth.signOut()
        mGoogleSignInClient.signOut()
        mGoogleSignInClient.revokeAccess()
    }
}