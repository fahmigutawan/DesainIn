package com.example.bccintern3.activity_home.home.adapterfilledsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RvAdapterFilledSearch(private var parentView: View,
                            private var mainFlManager: FragmentManager,
                            private var navbar: BottomNavigationView,
                            private var activity: AppCompatActivity,
                            private var searchedUidList:ArrayList<String>,
                            private var searchIdKategori:ArrayList<String>,
                            private var thisContext:Context,
                            private var appContext: Context
):RecyclerView.Adapter<RvAdapterFilledSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterFilledSearchViewHolder {
        return RvAdapterFilledSearchViewHolder(
            LayoutInflater.from(parent.context),
            parent,
            parentView,
            mainFlManager,
            navbar,
            activity,
            thisContext,appContext
        )
    }

    override fun onBindViewHolder(holder: RvAdapterFilledSearchViewHolder, position: Int) {
        holder.bind(searchedUidList.get(position),searchIdKategori.get(position))
    }

    override fun getItemCount(): Int {
        return searchedUidList.size
    }
}