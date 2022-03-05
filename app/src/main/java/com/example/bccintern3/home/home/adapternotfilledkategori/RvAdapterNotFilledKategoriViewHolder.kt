package com.example.bccintern3.home.home.adapternotfilledkategori

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
import com.squareup.picasso.Picasso

class RvAdapterNotFilledKategoriViewHolder(inflater: LayoutInflater,
                                           parent:ViewGroup,
                                           parentView:View,
                                           mainFlManager:FragmentManager)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.home_homefragment_notfill_kategori_border,parent,false))

{
        private var parentView:View=parentView
        private var mainFlManager:FragmentManager=mainFlManager
        private var imageView:ImageView=itemView.findViewById(R.id.homefragment_notfill_kategoriborder_iv)
        private var dbRef:DbReference= DbReference()

        fun bind(index:Int){
            val tmp=parentView.width/3.7
            val width=tmp.toInt()
            val height=tmp.toInt()
            val ref = dbRef.refCategoryPicture()
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Picasso
                        .get()
                        .load(snapshot.child(index.toString()).child("icon").getValue().toString())
                        .transform(RoundCornerRect(30f,0f,0f,0f,0f))
                        .resize(width,height)
                        .into(imageView)

                    ref.removeEventListener(this)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
}