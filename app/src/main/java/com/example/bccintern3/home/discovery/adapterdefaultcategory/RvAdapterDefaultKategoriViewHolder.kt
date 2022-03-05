package com.example.bccintern3.home.discovery.adapterdefaultcategory

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.DbReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.squareup.picasso.Picasso
import kotlin.random.Random

class RvAdapterDefaultKategoriViewHolder(inflater:LayoutInflater,
                                         parent:ViewGroup,
                                         parentView: View,
                                         mainFlManager:FragmentManager) :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_default_kategoriborder,parent,false)) {

    private var dbRef: DbReference
    private var imageView: ImageView
    private var parentView:View
    private var mainFlManager:FragmentManager

    init {
        dbRef = DbReference()
        imageView = itemView.findViewById(R.id.discoveryfragment_default_kategoriborder_iv)
        this.parentView = parentView
        this.mainFlManager = mainFlManager
    }
    fun bind(index:Int){
        val w = parentView.width
        val size = w/4
        val ref = dbRef.refCategoryPicture()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var url = snapshot.child(index.toString()).child("icon").getValue().toString()
                Picasso
                    .get()
                    .load(url)
                    .transform(RoundCornerRect(30f,0f,0f,0f,0f))
                    .resize(size,size)
                    .into(imageView)
                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}