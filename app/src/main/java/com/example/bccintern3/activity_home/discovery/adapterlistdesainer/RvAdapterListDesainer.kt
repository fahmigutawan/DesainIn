package com.example.bccintern3.activity_home.discovery.adapterlistdesainer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RvAdapterListDesainer(
    private var parentView: View,
    private var flManager: FragmentManager,
    private var thisContext:Context,
    private var navbar:BottomNavigationView,
    private var uid:ArrayList<String>,
    private var idKategori:ArrayList<String>,
    private var activity:AppCompatActivity,
    private var appContext: Context
) : RecyclerView.Adapter<RvAdapterListDesainerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterListDesainerViewHolder {
        return RvAdapterListDesainerViewHolder(
            LayoutInflater.from(parent.context),
            parent,
            parentView,
            flManager,
            thisContext,
            navbar,
            activity,
            appContext
            )
    }

    override fun onBindViewHolder(holder: RvAdapterListDesainerViewHolder, position: Int) {
        holder.bind(
            uid.get(position),
            idKategori.get(position))
    }

    override fun getItemCount(): Int {
        return uid.size
    }
}