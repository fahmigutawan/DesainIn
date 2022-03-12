package com.example.bccintern3.activity_home.discovery.adapterlistcategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.activity_home.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class RvAdapterListKategori(private var parentView: View,
                            private var mainFlManager: FragmentManager,
                            private var thisContext: Context,
                            private var navbar:BottomNavigationView,
                            private var activity: AppCompatActivity,
                            private var appContext: Context,
                            private var parentHome: HomeActivity
                            )
    : RecyclerView.Adapter<RvAdapterListKategoriViewHolder>()
{
    private var maxCount=10


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterListKategoriViewHolder {
        return RvAdapterListKategoriViewHolder(LayoutInflater.from(parent.context)
            ,parent
            ,parentView
            ,mainFlManager
            ,thisContext
            ,navbar
            ,activity,appContext,parentHome)
    }
    override fun onBindViewHolder(holder: RvAdapterListKategoriViewHolder, position: Int) {
        holder.bind((position+1).toString())
    }
    override fun getItemCount(): Int {
        return maxCount
    }
}