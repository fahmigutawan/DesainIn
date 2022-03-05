package com.example.bccintern3.home.discovery.adapterlistartikel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R

class RvAdapterListArtikelViewHolder(inflater: LayoutInflater,
                                     parent: ViewGroup,
                                     parentView: View,
                                     mainFlManager: FragmentManager)
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_listartikel_borderitem,parent,false))
{
    private var parentView = parentView
    private var mainFlManager = mainFlManager

    fun bind(){

    }
}