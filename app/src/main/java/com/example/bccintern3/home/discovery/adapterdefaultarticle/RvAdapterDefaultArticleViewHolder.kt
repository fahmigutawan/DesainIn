package com.example.bccintern3.home.discovery.adapterdefaultarticle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.DbReference
import com.squareup.picasso.Picasso

class RvAdapterDefaultArticleViewHolder(inflater: LayoutInflater
                                        , parent:ViewGroup
                                        , parentView: View
                                        , mainFlManager:FragmentManager)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_default_artikelborder,parent,false))
{
    private var imageView: ImageView
    private var titleTv:TextView
    private var penulisTv:TextView
    private var tanggalTv:TextView
    private var parentView = parentView
    private var mainFlManager = mainFlManager

    init {
        imageView = itemView.findViewById(R.id.discoveryfragment_default_artikelborder_iv)
        titleTv = itemView.findViewById(R.id.discoveryfragment_default_artikelborder_titletv)
        penulisTv = itemView.findViewById(R.id.discoveryfragment_default_artikelborder_penulistv)
        tanggalTv = itemView.findViewById(R.id.discoveryfragment_default_artikelborder_tanggaltv)
    }
    fun bind(url:String,judul:String,penulis:String,tanggal:String){
        val width = parentView.width
        val height = width/3

        /**set gambar**/
        Picasso
            .get()
            .load(url)
            .transform(RoundCornerRect(30f,0f,0f,0f,0f))
            .resize(width,height)
            .centerCrop()
            .into(imageView)

        /**set judul**/
        titleTv.setText(judul)

        /**set penulis**/
        penulisTv.setText(penulis)

        /**set tanggal**/
        tanggalTv.setText(tanggal)
    }
}