package com.example.bccintern2.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.activity_hubungikami.HubungiKamiActivity
import com.example.bccintern3.dummyactivity_riwayatorder.DummyRiwayatOrderActivity
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ProfileFragment(
    private var thisContext: Context,
    private var activity:AppCompatActivity
    ):Fragment(R.layout.home_profilefragment) {

    private lateinit var profileIV:ImageView
    private lateinit var nameTV:TextView
    private lateinit var emailTV:TextView
    private lateinit var editProfilBtn:Button
    private lateinit var hubungiKamiBtn:Button
    private lateinit var riwayatOrderBtn:Button
    private lateinit var logoutBtn:Button
    private lateinit var dbReference: DbReference
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var loadAct:LoadActivity
    private lateinit var loadFrag:LoadFragment
    private var lastBack:Long =0
    private var time:Long=0

    fun init(view:View) {
        /** DEKLARASI **/
        profileIV = view.findViewById(R.id.profilfragment_picture_imageview)
        nameTV = view.findViewById(R.id.profilefragment_name_textview)
        emailTV = view.findViewById(R.id.profilefragment_email_textview)
        editProfilBtn = view.findViewById(R.id.profilfragment_edit_btn)
        hubungiKamiBtn = view.findViewById(R.id.profilfragment_hubungikami_btn)
        riwayatOrderBtn = view.findViewById(R.id.profilfragment_riwayatorder_btn)
        logoutBtn = view.findViewById(R.id.profilfragment_logout_btn)
        dbReference = DbReference()
        fbAuth = FirebaseAuth.getInstance()
        loadAct = LoadActivity()
        loadFrag = LoadFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(view)

        /** Menyetel nama, PP, dan email **/
        setDataPreview()
        runCLickListener()

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }

    private fun runCLickListener(){
        /** Operasi **/
        editProfilBtn.setOnClickListener {
            if(lastBack+2000 > System.currentTimeMillis()){

            }else{
                loadAct.loadActivityDelayable(thisContext,EditProfileActivity::class.java,1000)
            }
            lastBack = System.currentTimeMillis()
        }
        riwayatOrderBtn.setOnClickListener {
            if(lastBack+2000>System.currentTimeMillis()){

            }else{
                loadAct.loadActivityDelayable(thisContext,DummyRiwayatOrderActivity::class.java,500)
            }
        }
        logoutBtn.setOnClickListener {
            if(time+1500>System.currentTimeMillis()){

            }else{
                signOut(thisContext)
            }
            time = System.currentTimeMillis()
        }
        hubungiKamiBtn.setOnClickListener {
            if(lastBack+2000 > System.currentTimeMillis()){

            }else{
                loadAct.loadActivityDelayable(thisContext,HubungiKamiActivity::class.java,1000)
            }
            lastBack = System.currentTimeMillis()
        }
    }
    private fun setDataPreview(){
        val ref = dbReference.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")
        Log.e("MSG",fbAuth.currentUser?.uid.toString())

        /** mengambil URL image profil, email, dan nama **/
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /** Set foto profil dengan Picasso **/
                Picasso
                    .get()
                    .load(snapshot.child("pictureUrl").getValue().toString())
                    .transform(CircleTransform())
                    .into(profileIV)

                /** Mengambil data email dan nama **/
                nameTV.setText(snapshot.child("name").getValue().toString())
                emailTV.setText(snapshot.child("email").getValue().toString())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun signOut(context:Context){
        val ref = dbReference.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")

        ref.child("fcmToken").removeValue().addOnSuccessListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(context,gso)
            fbAuth.signOut()
            mGoogleSignInClient.signOut().addOnSuccessListener {
                mGoogleSignInClient.revokeAccess().addOnSuccessListener {
                    activity.finish()
                    loadAct.loadActivityDisposable(thisContext,HomeActivity::class.java,activity,true)
                }
            }
            loadAct.loadActivityComplete(thisContext,HomeActivity::class.java,activity,true,1500)
        }.addOnFailureListener {
            Toast.makeText(thisContext,"Signout gagal, coba lagi nanti",Toast.LENGTH_SHORT).show()
        }

    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler
                val toast = Toast.makeText(thisContext,"Tekan kembali lagi untuk keluar",Toast.LENGTH_SHORT)

                if(time+1500>System.currentTimeMillis()){
                    toast.cancel()
                    handler.exit(activity)
                }
                else{
                    toast.show()
                }
                time = System.currentTimeMillis()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}