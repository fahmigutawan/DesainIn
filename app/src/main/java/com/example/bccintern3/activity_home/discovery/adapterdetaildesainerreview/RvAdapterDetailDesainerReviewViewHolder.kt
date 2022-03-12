package com.example.bccintern3.activity_home.discovery.adapterdetaildesainerreview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R

class RvAdapterDetailDesainerReviewViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private var namaTv:TextView
    private var reviewTv:TextView

    init {
        namaTv = itemView.findViewById(R.id.discoveryfragment_desainerdetail_reviewborder_nama)
        reviewTv = itemView.findViewById(R.id.discoveryfragment_desainerdetail_reviewborder_review)
    }

    fun bind(nama:String,review:String){
        namaTv.setText(nama)
        reviewTv.setText(review)
    }
}