package com.example.bccintern3.home.discovery.vp_adapterdetailkategori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class VpAdapterDetailKategoriViewHolder(inflater: LayoutInflater,
                                        parent: ViewGroup,
                                        private var parentView: View,
                                        private var mainFlManager: FragmentManager,
                                        private var navbar: BottomNavigationView,
                                        private var activity: AppCompatActivity
                                        )
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_kategori_detail_border,parent,false))
{
    private var imageView:ImageView
    init {
        imageView = itemView.findViewById(R.id.discoveryfragment_kategoridetail_border_image)
    }

    fun bind(url:String){
        val width =  parentView.width
        val height = (width/4*3)

        Picasso
            .get()
            .load(url)
            .transform(RoundCornerRect(30f,0f,0f,0f,0f))
            .resize(width,height)
            .centerCrop()
            .into(imageView)
    }
}