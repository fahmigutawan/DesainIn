package com.example.bccintern3.home.discovery.vp_adapterdetaildesainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class VpAdapterDetailDesainer(
    private var bannerUrl:ArrayList<String>,
    private var parentView: View
):RecyclerView.Adapter<VpAdapterDetailDesainerViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpAdapterDetailDesainerViewHolder {
        return VpAdapterDetailDesainerViewHolder(
            LayoutInflater.from(parent.context),
            parent,
            parentView
        )
    }
    override fun onBindViewHolder(holder: VpAdapterDetailDesainerViewHolder, position: Int) {
        holder.bind(bannerUrl.get(position))
    }
    override fun getItemCount(): Int {
        return bannerUrl.size
    }
}