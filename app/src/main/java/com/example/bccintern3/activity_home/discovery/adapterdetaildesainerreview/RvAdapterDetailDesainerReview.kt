package com.example.bccintern3.activity_home.discovery.adapterdetaildesainerreview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R

class RvAdapterDetailDesainerReview(private var nama:ArrayList<String>,
                                    private var review:ArrayList<String>):RecyclerView.Adapter<RvAdapterDetailDesainerReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterDetailDesainerReviewViewHolder {
        return RvAdapterDetailDesainerReviewViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.home_discoveryfragment_desainer_detail_reviewborder,parent,false
                )
        )
    }

    override fun onBindViewHolder(holder: RvAdapterDetailDesainerReviewViewHolder, position: Int) {
        holder.bind(nama.get(position),review.get(position))
    }

    override fun getItemCount(): Int {
        return 4
    }
}