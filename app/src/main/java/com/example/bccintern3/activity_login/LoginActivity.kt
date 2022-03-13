package com.example.bccintern3.activity_login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern2.firsttime.OnboardActivity
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.activity_signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso

class LoginActivity:AppCompatActivity() {
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var daftarBtn: TextView
    private lateinit var loginBtn: Button
    private lateinit var googleLoginBtn: FloatingActionButton
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var dbReference: DbReference
    private lateinit var loadAct: LoadActivity
    private lateinit var imageView: ImageView
    private var lastBack:Long = 0

    fun init() {
        emailInput = findViewById(R.id.loginactivity_emil_et)
        passwordInput = findViewById(R.id.loginactivity_password_et)
        daftarBtn = findViewById(R.id.loginactivity_toregister_textview)
        loginBtn = findViewById(R.id.loginactivity_login_btn)
        googleLoginBtn = findViewById(R.id.loginactivity_google_fab)
        fbAuth = FirebaseAuth.getInstance()
        dbReference = DbReference()
        loadAct = LoadActivity()
        imageView = findViewById(R.id.loginactivity_imageiv)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }
    fun setImage(){
        imageView.viewTreeObserver.addOnGlobalLayoutListener(object:ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val size = imageView.width/3
                Picasso
                    .get()
                    .load(R.drawable.logo)
                    .transform(RoundCornerRect(30f,0f,0f,0f,0f))
                    .resize(size,size)
                    .into(imageView)
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        init()
        setImage()
        runClickListener()
    }

    fun runClickListener(){
        daftarBtn.setOnClickListener {
            if(lastBack+2000>System.currentTimeMillis()){

            }else{
                loadAct.loadActivity(this, SignUpActivity::class.java)
            }
            lastBack = System.currentTimeMillis()
        }
        googleLoginBtn.setOnClickListener {
            signInGoogle()
        }
        loginBtn.setOnClickListener {
            if(emailInput.text.toString()!=""
                && passwordInput.text.toString()!=""){
                signInEmail(
                    emailInput.text.toString()
                    ,passwordInput.text.toString()
                )
            }
            else{
                Toast.makeText(applicationContext,"Harap isi semua form yang tersedia",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun checkFirstTimeLogin(){
        val ref = dbReference.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child("isFirstTime").getValue().toString()=="true"){
                    loadAct.loadActivityDisposable(this@LoginActivity,OnboardActivity::class.java,this@LoginActivity,true)
                }
                else{
                    loadAct.loadActivityDisposable(this@LoginActivity,HomeActivity::class.java,this@LoginActivity,true)
                }
                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun storeData(){
        /** Untuk store data profil pengguna baru */
        val userRef = dbReference.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")

        dbReference.refUserNode().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(fbAuth.currentUser?.uid.toString())){
                    checkFirstTimeLogin()
                }
                else{
                    userRef.child("pictureUrl").setValue(fbAuth.currentUser?.photoUrl.toString())
                    userRef.child("uid").setValue(fbAuth.currentUser?.uid.toString())
                    userRef.child("name").setValue(fbAuth.currentUser?.displayName.toString())
                    userRef.child("email").setValue(fbAuth.currentUser?.email.toString())
                    userRef.child("gender").setValue("null")
                    userRef.child("phone").setValue(fbAuth.currentUser?.phoneNumber.toString())

                    //untuk mengondisikan bahwa user belum selesai activity FirstTimeLogin
                    userRef.child("isFirstTime").setValue("true")
                    checkFirstTimeLogin()
                }

                dbReference.refUserNode().removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(applicationContext,"Login dengan Google gagal, coba lagi nanti",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        fbAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    sendToken()
                    Toast.makeText(applicationContext,"Login berhasil.\n\nOtomatis akan ke halaman beranda",Toast.LENGTH_LONG).show()
                    Handler().postDelayed({
                        storeData()
                    },1200)
                } else {
                    Toast.makeText(applicationContext,"Terjadi kesalahan tak terduga, coba lagi nanti", Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    fun signInEmail(email:String,pass:String){
        fbAuth.signInWithEmailAndPassword(email,pass).addOnFailureListener {
            Toast.makeText(applicationContext,"Login gagal, cek kembali data yang anda masukkan",Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            checkFirstTimeLogin()
            Toast.makeText(applicationContext,"Login berhasil.\n\nOtomatis akan ke halaman beranda",Toast.LENGTH_LONG).show()
            sendToken()
        }
    }
    fun sendToken(){
        if(fbAuth.currentUser!=null){
            val ref = dbReference.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")

            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                ref.child("fcmToken").setValue(it.result.toString())
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 120
    }
}