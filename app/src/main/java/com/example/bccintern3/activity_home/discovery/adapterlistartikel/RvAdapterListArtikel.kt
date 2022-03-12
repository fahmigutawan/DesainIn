package com.example.bccintern3.activity_home.discovery.adapterlistartikel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.activity_home.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class RvAdapterListArtikel(private var parentView: View,
                           private var mainFlManager:FragmentManager,
                           private var artikelAtribut:ArrayList<ArrayList<String>>,
                           private var navbar:BottomNavigationView,
                           private var activity: AppCompatActivity,
                           private var appContext: Context,
                           private var parentHome: HomeActivity
                           ): RecyclerView.Adapter<RvAdapterListArtikelViewHolder>()
{
    private var param = artikelAtribut.get(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterListArtikelViewHolder {
        return RvAdapterListArtikelViewHolder(LayoutInflater.from(parent.context)
            ,parent
            ,parentView
            ,mainFlManager
            ,navbar
            ,activity
            ,appContext,parentHome)
    }

    override fun onBindViewHolder(holder: RvAdapterListArtikelViewHolder, position: Int) {
        holder.bind(
            artikelAtribut.get(0).get(position),
            artikelAtribut.get(1).get(position),
            artikelAtribut.get(2).get(position),
            artikelAtribut.get(3).get(position),
            artikelAtribut.get(4).get(position),
            artikelAtribut.get(5).get(position),
            artikelAtribut.get(6).get(position))
    }

    override fun getItemCount(): Int {
        return param.size
    }
}