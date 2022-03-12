package com.example.bccintern3.dummyactivity_riwayatorder.adapterriwayatorder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R

class RvAdapterRiwayatOrder(private var nama:ArrayList<String>,
                            private var harga:ArrayList<String>,
                            private var thisContext:Context
                            ):RecyclerView.Adapter<RvAdapterRiwayatOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterRiwayatOrderViewHolder {
        return RvAdapterRiwayatOrderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.riwayatorder_activity_list_border,parent,false),
            thisContext
        )
    }

    override fun onBindViewHolder(holder: RvAdapterRiwayatOrderViewHolder, position: Int) {
        holder.bind(nama.get(position),harga.get(position))
    }

    override fun getItemCount(): Int {
        return nama.size
    }
}