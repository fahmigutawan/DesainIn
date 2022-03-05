package com.example.bccintern3.home.home.adapternotfilledartikel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.squareup.picasso.Picasso

class RvAdapterNotFilledArtikelViewHolder(inflater: LayoutInflater,
                                          parent: ViewGroup,
                                          parentView: View,
                                          mainFlManager: FragmentManager)
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_homefragment_notfill_artikel_border,parent,false))
{
    private var parentView = parentView
    private var mainFlManager = mainFlManager
    private var imageView:ImageView
    private var titleTv:TextView

    init {
        imageView = itemView.findViewById(R.id.homefragment_notfill_artikelborder_iv)
        titleTv = itemView.findViewById(R.id.homefragment_notfill_artikeltitleborder_tv)
    }
    fun bind(url:String,title:String){
        val width = (parentView.width/2.5).toInt()
        val height = width/2
        /**set gambar**/
        Picasso
            .get()
            .load(url)
            .transform(RoundCornerRect(30f,0f,0f,0f,0f))
            .resize(width,height)
            .centerCrop()
            .into(imageView)

        /**set title**/
        titleTv.setText(title)
    }
}