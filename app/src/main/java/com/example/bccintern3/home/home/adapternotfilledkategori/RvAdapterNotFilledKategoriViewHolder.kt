package com.example.bccintern3.home.home.adapternotfilledkategori

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.DiscoveryFragmentDetailKategori
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*

class RvAdapterNotFilledKategoriViewHolder(inflater: LayoutInflater,
                                           parent:ViewGroup,
                                           parentView:View,
                                           mainFlManager:FragmentManager,
                                           private var thisContext:Context,
                                           private var navbar:BottomNavigationView,
                                           private var activity: AppCompatActivity
                                           )
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.home_homefragment_notfill_kategori_border,parent,false))

{
    private var parentView:View=parentView
    private var mainFlManager:FragmentManager=mainFlManager
    private var imageView:ImageView=itemView.findViewById(R.id.homefragment_notfill_kategoriborder_iv)
    private var dbRef:DbReference= DbReference()
    private var loadFrag:LoadFragment

    init {
        loadFrag= LoadFragment()
    }

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
                    runClickListener(index)

                    ref.removeEventListener(this)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    fun runClickListener(index: Int){
        imageView.setOnClickListener {
            Handler().postDelayed({
                navbar.menu.getItem(0).setChecked(false)
                navbar.menu.getItem(1).setChecked(true)
                loadFrag.transfer(mainFlManager,R.id.homeactivity_flmanager,DiscoveryFragmentDetailKategori(mainFlManager,thisContext,navbar,index.toString(),activity))
            },1000)
        }
    }
}