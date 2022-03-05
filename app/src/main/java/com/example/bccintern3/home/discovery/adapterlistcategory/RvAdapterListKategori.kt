package com.example.bccintern3.home.discovery.adapterlistcategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class RvAdapterListKategori(private var parentView: View,
                            private var mainFlManager: FragmentManager)
    : RecyclerView.Adapter<RvAdapterListKategoriViewHolder>()
{
    private var maxCount=11


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterListKategoriViewHolder {
        return RvAdapterListKategoriViewHolder(LayoutInflater.from(parent.context)
            ,parent
            ,parentView
            ,mainFlManager)
    }
    override fun onBindViewHolder(holder: RvAdapterListKategoriViewHolder, position: Int) {
        holder.bind((position+1).toString())
    }
    override fun getItemCount(): Int {
        return maxCount
    }
}