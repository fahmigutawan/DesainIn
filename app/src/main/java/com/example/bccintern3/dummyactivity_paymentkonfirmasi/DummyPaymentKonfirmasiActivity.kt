package com.example.bccintern3.dummyactivity_paymentkonfirmasi

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern3.R
import com.example.bccintern3.activity_chat.ChatActivity
import com.example.bccintern3.activity_login.LoginActivity
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DummyPaymentKonfirmasiActivity:AppCompatActivity(R.layout.paymentkonfirmasi_activity) {
    private lateinit var chatBtn:Button
    private lateinit var loadFrag: LoadFragment
    private lateinit var loadAct: LoadActivity
    private lateinit var dbRef: DbReference
    private lateinit var fbAuth: FirebaseAuth
    private lateinit var uidSelected:String

    private lateinit var harga:String
    private lateinit var metode:String

    private lateinit var namaTv:TextView
    private lateinit var hargaTv:TextView
    private lateinit var metodeTv:TextView
    private lateinit var uploadBuktiBtn:Button
    private lateinit var uploadedTv:TextView

    fun init(){
        chatBtn = findViewById(R.id.paymentkonfirmasiactivity_chatbtn)
        loadFrag = LoadFragment()
        loadAct = LoadActivity()
        dbRef = DbReference()
        fbAuth = FirebaseAuth.getInstance()
        namaTv = findViewById(R.id.paymentkonfirmasiactivity_desainertv)
        hargaTv = findViewById(R.id.paymentkonfirmasiactivity_hargatv)
        metodeTv = findViewById(R.id.paymentkonfirmasiactivity_metodetv)
        uploadBuktiBtn = findViewById(R.id.paymentkonfirmasiactivity_unggahBtn)
        uploadedTv = findViewById(R.id.paymentkonfirmasiactivity_uploadedtv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        loadUidSelectedFromExras()
        loadNominalFromExtras()
        loadMetodeFromExtras()
        loadDisplayText()
        runClickListener()

        chatBtn.setBackgroundResource(R.drawable.universal_gray_button)
        chatBtn.setClickable(false)
    }
    fun displayBackConfirmation(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.paymentkorfirmasi_activity_backkonfirmasi)
        dialog.setCancelable(true)

        val batalBtn = dialog.findViewById<Button>(R.id.paymentactivity_backkonfirmasi_batalbtn)
        val hapusBtn = dialog.findViewById<Button>(R.id.paymentactivity_backkonfirmasi_hapusbtn)

        batalBtn.setOnClickListener {
            dialog.dismiss()
        }
        hapusBtn.setOnClickListener {
            super.onBackPressed()
        }
        dialog.show()
    }
    fun loadDisplayText(){
        val ref = dbRef.refUidNode(uidSelected).child("profile")

        ref.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)

                namaTv.setText(snapshot.child("name").getValue().toString())
                hargaTv.setText(harga)
                metodeTv.setText(metode)
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    override fun onBackPressed() {
        displayBackConfirmation()
    }
    fun runClickListener(){
        chatBtn.setOnClickListener {
            if(fbAuth.currentUser?.uid!=null){
                val ref = dbRef.refChatRoomNode()

                val tmp1 = fbAuth.currentUser?.uid.toString() + uidSelected
                val tmp2 = uidSelected + fbAuth.currentUser?.uid.toString()

                val refUser1 = dbRef.refUidNode(fbAuth.currentUser?.uid.toString()).child("roomChatId")
                val refUser2 = dbRef.refUidNode(uidSelected).child("roomChatId")

                if(fbAuth.currentUser?.uid.toString() == uidSelected){
                    Toast.makeText(this,"Tidak bisa chat dengan diri sendiri", Toast.LENGTH_SHORT).show()
                }
                else{
                    refUser1.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            refUser1.removeEventListener(this)
                            if(!snapshot.hasChild(tmp1) && !snapshot.hasChild(tmp2)){
                                /**hanya dicek pada user1. Karena jika user1 tidak punya, pasti user2 tidak punya**/
                                refUser1.child(tmp1).child("roomId").setValue(tmp1)
                                refUser1.child(tmp1).child("user1").setValue(uidSelected)
                                refUser1.child(tmp1).child("user2").setValue(fbAuth.currentUser?.uid.toString())
                                refUser1.child(tmp1).child("count").setValue("0")

                                refUser2.child(tmp1).child("roomId").setValue(tmp1)
                                refUser2.child(tmp1).child("user1").setValue(uidSelected)
                                refUser2.child(tmp1).child("user2").setValue(fbAuth.currentUser?.uid.toString())
                                refUser2.child(tmp1).child("count").setValue("0")

                                ref.child(tmp1).child("identity").child("count").setValue("0")
                                ref.child(tmp1).child("identity").child("chatId").setValue(tmp1)
                                ref.child(tmp1).child("identity").child("user1").setValue(fbAuth.currentUser?.uid.toString())
                                ref.child(tmp1).child("identity").child("user2").setValue(uidSelected)


                                loadAct.loadActivityCompleteWithExtras(
                                    this@DummyPaymentKonfirmasiActivity,
                                    ChatActivity::class.java,
                                    this@DummyPaymentKonfirmasiActivity,
                                    true,
                                    1000,
                                    "uid",
                                    tmp1)
                            }
                            else if(snapshot.hasChild(tmp1)){
                                loadAct.loadActivityCompleteWithExtras(
                                    this@DummyPaymentKonfirmasiActivity,
                                    ChatActivity::class.java,
                                    this@DummyPaymentKonfirmasiActivity,
                                    true,
                                    1000,
                                    "uid",
                                    tmp1)
                            }
                            else if(snapshot.hasChild(tmp2)){
                                loadAct.loadActivityCompleteWithExtras(
                                    this@DummyPaymentKonfirmasiActivity,
                                    ChatActivity::class.java,
                                    this@DummyPaymentKonfirmasiActivity,
                                    true,
                                    1000,
                                    "uid",
                                    tmp2)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }
            else{
                Toast.makeText(this,"Harap login untuk mengirim pesan", Toast.LENGTH_SHORT).show()
                loadAct.loadActivityDelayable(this, LoginActivity::class.java,1000)
            }
        }
        uploadBuktiBtn.setOnClickListener {
            selectImage()
        }
    }
    fun loadUidSelectedFromExras(){
        uidSelected = intent.extras?.getString("uid").toString()
    }
    fun loadNominalFromExtras(){
        harga = intent.extras?.getString("nominal").toString()
    }
    fun loadMetodeFromExtras(){
        metode = intent.extras?.getString("metode").toString()
    }
    private fun selectImage(){
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(intent,100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && data!=null &&data.getData()!=null && resultCode == RESULT_OK){
            uploadedTv.setText("Gambar terpilih")
            chatBtn.setBackgroundResource(R.drawable.universal_blue_button)
            chatBtn.setClickable(true)
        }
    }
}