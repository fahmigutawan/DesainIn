package com.example.bccintern3.home.discovery.adapterlistartikel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class RvAdapterListArtikel(private var parentView: View,
                           private var mainFlManager:FragmentManager,
                           artikelAtribut:ArrayList<ArrayList<String>>): RecyclerView.Adapter<RvAdapterListArtikelViewHolder>()
{
    private var param = artikelAtribut.get(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterListArtikelViewHolder {
        return RvAdapterListArtikelViewHolder(LayoutInflater.from(parent.context)
            ,parent
            ,parentView
            ,mainFlManager)
    }

    override fun onBindViewHolder(holder: RvAdapterListArtikelViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return param.size
    }
}