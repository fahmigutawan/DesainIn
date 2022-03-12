package com.example.bccintern3.dummyactivity_riwayatorder.adapterriwayatorder

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.dummyactivity_paymentkonfirmasi.DummyPaymentKonfirmasiActivity
import com.example.bccintern3.dummyactivity_riwayatorder.DummyRiwayatOrderDetailActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity

class RvAdapterRiwayatOrderViewHolder(view:View,
                                      private var thisContext:Context
                                      ):RecyclerView.ViewHolder(view) {
    private var nama:TextView
    private var harga:TextView
    private var clickArea:LinearLayout
    private var time:Long = 0
    private var loadAct:LoadActivity

    init{
        nama = itemView.findViewById(R.id.riwayatorder_list_border_nama)
        harga = itemView.findViewById(R.id.riwayatorder_list_border_harga)
        clickArea = itemView.findViewById(R.id.riwayatorder_list_border_clickarea)
        loadAct = LoadActivity()

        runCLickListener()
    }

    fun bind(nama:String,harga:String){
        this.nama.setText(nama)
        this.harga.setText(harga)
    }
    fun runCLickListener(){
        clickArea.setOnClickListener {
            if(time+1500>System.currentTimeMillis()){

            }else{
                loadAct.loadActivityDelayable(thisContext,DummyRiwayatOrderDetailActivity::class.java,500)
            }
            time = System.currentTimeMillis()
        }
    }
}