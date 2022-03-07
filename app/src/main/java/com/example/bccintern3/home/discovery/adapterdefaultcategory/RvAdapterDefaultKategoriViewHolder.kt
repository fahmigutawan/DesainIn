package com.example.bccintern3.home.discovery.adapterdefaultcategory

import android.content.Context
import android.os.Handler
import android.util.Log
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
import com.example.bccintern3.home.discovery.DiscoveryFragmentListKategori
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.squareup.picasso.Picasso
import kotlin.random.Random

class RvAdapterDefaultKategoriViewHolder(inflater:LayoutInflater,
                                         parent:ViewGroup,
                                         parentView: View,
                                         mainFlManager:FragmentManager,
                                         private var thisContext: Context,
                                         private var navbar:BottomNavigationView,
                                         private val activity: AppCompatActivity) :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_default_kategoriborder,parent,false)) {

    private var dbRef: DbReference
    private var imageView: ImageView
    private var parentView:View
    private var mainFlManager:FragmentManager
    private var loadFrag:LoadFragment

    init {
        dbRef = DbReference()
        imageView = itemView.findViewById(R.id.discoveryfragment_default_kategoriborder_iv)
        loadFrag = LoadFragment()
        this.parentView = parentView
        this.mainFlManager = mainFlManager
    }
    fun bind(index:Int){
        val w = parentView.width
        val size = (w/4.2).toInt()
        val ref = dbRef.refCategoryPicture()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var url = snapshot.child(index.toString()).child("icon").getValue().toString()
                Picasso
                    .get()
                    .load(url)
                    .transform(RoundCornerRect(20f,0f,0f,0f,0f))
                    .resize(size,size)
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
            if(adapterPosition==5){
                Handler().postDelayed({
                    loadFrag.transfer(mainFlManager,R.id.homeactivity_flmanager,DiscoveryFragmentListKategori(mainFlManager,thisContext,navbar,activity))
                },1000)
            }else{
                Handler().postDelayed({
                    loadFrag.transfer(mainFlManager,R.id.homeactivity_flmanager,DiscoveryFragmentDetailKategori(mainFlManager,thisContext,navbar,index.toString(),activity))
                },1000)
            }
        }
    }
}