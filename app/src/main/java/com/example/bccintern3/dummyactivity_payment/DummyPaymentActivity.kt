package com.example.bccintern3.dummyactivity_payment

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern3.R
import com.example.bccintern3.dummyactivity_paymentkonfirmasi.DummyPaymentKonfirmasiActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.google.android.material.textfield.TextInputEditText

class DummyPaymentActivity:AppCompatActivity(R.layout.payment_activity) {
    private lateinit var nominalEt:TextInputEditText
    private lateinit var kembaliBtn:Button
    private lateinit var konfirmasiBtn:Button
    private lateinit var loadAct:LoadActivity
    private lateinit var uidSelected:String

    fun init(){
        nominalEt = findViewById(R.id.paymentactivity_inputharga)
        kembaliBtn = findViewById(R.id.paymentactivity_kembaliBtn)
        konfirmasiBtn = findViewById(R.id.paymentactivity_konfirmasibtn)
        loadAct = LoadActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        runClickListener()
        loadUidSelectedFromExtras()
    }
    fun runClickListener(){
        kembaliBtn.setOnClickListener {
            super.onBackPressed()
        }
        konfirmasiBtn.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("uid",uidSelected)

            loadAct.loadActivityCompleteWithBundle(
                this,
                DummyPaymentKonfirmasiActivity::class.java,
                this,
                false,
                1000,
                bundle)
        }
    }
    fun loadUidSelectedFromExtras(){
        uidSelected = intent.extras?.getString("uid").toString()
    }

}