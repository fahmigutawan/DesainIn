package com.example.bccintern3.dummyactivity_riwayatorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.dummyactivity_riwayatorder.adapterriwayatorder.RvAdapterRiwayatOrder

class DummyRiwayatOrderActivity:AppCompatActivity(R.layout.riwayatorder_activity) {
    private lateinit var riwayatorderRv:RecyclerView
    private lateinit var riwayatRvAdapter:RvAdapterRiwayatOrder

    fun init(){
        riwayatorderRv = findViewById(R.id.riwayatorder_activity_chatrv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        loadRiwayatOrderRv()
    }
    fun loadRiwayatOrderRv(){
        var nama = ArrayList<String>()
        var harga = java.util.ArrayList<String>()

        nama.add("Fahmi Noordin")
        nama.add("Kala Huda")
        nama.add("Joko Santoso")
        nama.add("Ahmad Nur")

        harga.add("Rp.150000")
        harga.add("Rp.250000")
        harga.add("Rp.75000")
        harga.add("Rp.350000")

        riwayatRvAdapter = RvAdapterRiwayatOrder(nama,harga,this)

        riwayatorderRv.layoutManager = LinearLayoutManager(this)
        riwayatorderRv.adapter = riwayatRvAdapter
    }
}