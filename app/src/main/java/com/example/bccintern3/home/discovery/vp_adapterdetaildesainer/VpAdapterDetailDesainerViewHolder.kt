package com.example.bccintern3.home.discovery.vp_adapterdetaildesainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.squareup.picasso.Picasso

class VpAdapterDetailDesainerViewHolder(inflater: LayoutInflater,
                                        parent: ViewGroup,
                                        private var parentView: View
):RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_desainer_detail_vpborder,parent,false))
{
    private var imageView:ImageView

    init {
        imageView = itemView.findViewById(R.id.discoveryfragment_desainerdetail_border_iv)
    }

    fun bind(url:String){
        val width = parentView.width
        val height = width*9/16

        Picasso
            .get()
            .load(url)
            .transform(RoundCornerRect(30f,0f,0f,0f,0f))
            .resize(width,height)
            .centerCrop()
            .into(imageView)
    }
}