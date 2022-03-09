package com.example.bccintern3.activity_home.home.vp_adapternotfilledbanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class VpAdapterNotFilledBanner(
                              private var imgUrl:ArrayList<String>,
                              private var parentView: View,
                              private var flManager: FragmentManager,
                              private var navbar:BottomNavigationView,
                              private var activity:AppCompatActivity
                              )
    :RecyclerView.Adapter<VpAdapterNotFilledBannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpAdapterNotFilledBannerViewHolder {
        return VpAdapterNotFilledBannerViewHolder(
            LayoutInflater.from(parent.context),
            parent,
            parentView,
            flManager,
            navbar,
            activity
            )
    }

    override fun onBindViewHolder(holder: VpAdapterNotFilledBannerViewHolder, position: Int) {
        holder.bind(imgUrl.get(position))
    }

    override fun getItemCount(): Int {
        return imgUrl.size
    }
}