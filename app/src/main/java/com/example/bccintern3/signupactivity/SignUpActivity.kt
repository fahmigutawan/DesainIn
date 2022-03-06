package com.example.bccintern3.signupactivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.loginactivity.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class SignUpActivity:AppCompatActivity(R.layout.signup_activity) {
    private lateinit var nama:TextInputEditText
    private lateinit var jenisKlmn: Spinner
    private lateinit var email:TextInputEditText
    private lateinit var telepon:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var passwordConfirm: TextInputEditText
    private lateinit var signUpBtn: Button
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var klmnAdapter: ArrayAdapter<String>
    private lateinit var dbReference: DbReference
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

        init()
        setImage()

        signUpBtn.setOnClickListener {
            register()
        }
    }

    fun init(){
        //deklarasi
        jenisKlmn = findViewById(R.id.signupactivity_klmn_spinner)
        nama = findViewById(R.id.signupactivity_nama_edittext)
        email = findViewById(R.id.signupactivity_emil_edittext)
        telepon = findViewById(R.id.signupactivity_telpon_edittext)
        password = findViewById(R.id.signupactivity_password_edittext)
        passwordConfirm = findViewById(R.id.signupactivity_passwordconfirm_edittext)
        signUpBtn = findViewById(R.id.signupactivity_signup_btn)
        fbAuth = FirebaseAuth.getInstance()
        dbReference = DbReference()
        imageView = findViewById(R.id.signupactivity_imageiv)

        //jenis kelamin ke adapter array
        klmnAdapter = ArrayAdapter(this,R.layout.signup_activity_spinner_style,jenisKelamin())
        jenisKlmn.setAdapter(klmnAdapter)
    }
    fun setImage(){
        Picasso
            .get()
            .load(R.drawable.logo)
            .resize(420,420)
            .transform(RoundCornerRect(60f,0f,0f,0f,0f))
            .into(imageView)
    }
    private fun jenisKelamin():ArrayList<String>{
        var jKel = ArrayList<String>()
        jKel.add("Laki-laki")
        jKel.add("Perempuan")

        return jKel
    }
    private fun register(){
        if(getNama()!=""
            || getJenisKelamin()!=""
            || getEmail()!=""
            || getTelepon()!=""
            || getPassword()!=""
            || getPasswordConfirm()!="")
        {
            if(getPassword()==getPasswordConfirm()){
                fbAuth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext,"Registrasi berhasil.\n\nAnda akan otomatis menuju halaman login",Toast.LENGTH_LONG).show()

                        storeData() //memasukkan data profil pengguna

                        Handler().postDelayed({
                            //menuju ke halaman login
                            loadActivity(LoginActivity::class.java,true)
                        },2000)
                    }
                    .addOnFailureListener {
                        Toast.makeText(applicationContext,"Registrasi gagal, coba lagi nanti",Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                Toast.makeText(applicationContext,"Pastikan password dan konfirmasi sama",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(applicationContext,"Pastikan semua data terisi dengan benar",Toast.LENGTH_SHORT).show()
        }

    }
    private fun storeData(){
        /** Untuk store data profil pengguna baru */
        val userRef = dbReference.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")

        userRef.child("pictureUrl").setValue("null")
        userRef.child("uid").setValue(fbAuth.currentUser?.uid.toString())
        userRef.child("name").setValue(getNama())
        userRef.child("email").setValue(getEmail())
        userRef.child("gender").setValue(getJenisKelamin())
        userRef.child("phone").setValue(getTelepon())

        //untuk mengondisikan bahwa user belum selesai activity FirstTimeLogin
        userRef.child("isFirstTime").setValue("true")
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

    private fun getNama():String{
        return nama.text.toString().trim()
    }
    private fun getEmail():String{
        return email.text.toString().trim()
    }
    private fun getJenisKelamin():String{
        var jnsKlmn=""
        jenisKlmn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                jnsKlmn = jenisKlmn.getItemAtPosition(position).toString().trim()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        return jnsKlmn
    }
    private fun getTelepon():String{
        return telepon.text.toString().trim()
    }
    private fun getPassword():String{
        return password.text.toString().trim()
    }
    private fun getPasswordConfirm():String{
        return passwordConfirm.text.toString().trim()
    }
}