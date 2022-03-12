package com.example.bccintern3.dummyactivity_payment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern3.R
import com.example.bccintern3.dummyactivity_payment.adapter.SpinnerAdapter
import com.example.bccintern3.dummyactivity_paymentkonfirmasi.DummyPaymentKonfirmasiActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate

class DummyPaymentActivity:AppCompatActivity(R.layout.payment_activity) {
    private lateinit var nominalEt:TextInputEditText
    private lateinit var kembaliBtn:Button
    private lateinit var konfirmasiBtn:Button
    private lateinit var loadAct:LoadActivity
    private lateinit var uidSelected:String
    private lateinit var spinner:Spinner
    private lateinit var adapterBank:SpinnerAdapter
    private lateinit var metodeSelected:String
    private var time:Long=0
    private lateinit var icon:ArrayList<Int>
    private lateinit var nama:ArrayList<String>

    fun init(){
        nominalEt = findViewById(R.id.paymentactivity_inputharga)
        kembaliBtn = findViewById(R.id.paymentactivity_kembaliBtn)
        konfirmasiBtn = findViewById(R.id.paymentactivity_konfirmasibtn)
        spinner = findViewById(R.id.paymentactivity_spinner)
        loadAct = LoadActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        loadUidSelectedFromExtras()
        loadSpinner()
        runSpinnerListener()
        runClickListener()
    }
    fun runClickListener(){
        kembaliBtn.setOnClickListener {
            super.onBackPressed()
        }
        konfirmasiBtn.setOnClickListener {
            if(time+2000>System.currentTimeMillis()){

            }else{
                if(nominalEt.text.toString()==""){
                    Toast.makeText(applicationContext,"Harap masukkan nominal terlebih dahulu",Toast.LENGTH_SHORT).show()
                }
                else{
                    if(metodeSelected=="Pilih salah satu"){
                        Toast.makeText(this,"Harap pilih metode pembayaran",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        var bundle = Bundle()
                        bundle.putString("uid",uidSelected)
                        bundle.putString("nominal",nominalEt.text.toString())
                        bundle.putString("metode",metodeSelected)

                        loadAct.loadActivityCompleteWithBundle(
                            this,
                            DummyPaymentKonfirmasiActivity::class.java,
                            this,
                            false,
                            1000,
                            bundle)
                    }
                }
            }
            time=System.currentTimeMillis()
        }
    }
    fun loadUidSelectedFromExtras(){
        uidSelected = intent.extras?.getString("uid").toString()
    }
    fun loadSpinner(){
        icon = ArrayList()
        nama = ArrayList()

        icon.add(R.drawable.bank_transfer)
        icon.add(R.drawable.gopay)
        icon.add(R.drawable.ovo)
        icon.add(R.drawable.shopee)
        icon.add(R.drawable.alfa)
        icon.add(R.drawable.indomaret)

        nama.add("Pilih salah satu")
        nama.add("Transfer Bank")
        nama.add("Go Pay")
        nama.add("OVO")
        nama.add("Shopee")
        nama.add("Alfamart")
        nama.add("Indomaret")

        setAdapter(icon,nama)
        spinner.adapter=adapterBank
    }
    fun runSpinnerListener(){
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                metodeSelected = nama.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
    fun setAdapter(icon:ArrayList<Int>,nama:ArrayList<String>){
        adapterBank = SpinnerAdapter(nama,icon)
    }
}