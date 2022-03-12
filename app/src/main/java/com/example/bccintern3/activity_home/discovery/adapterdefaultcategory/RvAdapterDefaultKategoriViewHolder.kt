package com.example.bccintern3.activity_home.discovery.adapterdefaultcategory

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentDetailKategori
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentListKategori
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class RvAdapterDefaultKategoriViewHolder(inflater:LayoutInflater,
                                         parent:ViewGroup,
                                         parentView: View,
                                         mainFlManager:FragmentManager,
                                         private var thisContext: Context,
                                         private var navbar:BottomNavigationView,
                                         private val activity: AppCompatActivity,
                                         private val appContext: Context,
                                         private var parentHome:HomeActivity
                                         ) :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_default_kategoriborder,parent,false)) {

    private var dbRef: DbReference
    private var imageView: ImageView
    private var parentView:View
    private var mainFlManager:FragmentManager
    private var loadFrag:LoadFragment
    private var clickArea:LinearLayout
    private var titleTv:TextView

    init {
        dbRef = DbReference()
        imageView = itemView.findViewById(R.id.discoveryfragment_default_kategoriborder_iv)
        loadFrag = LoadFragment()
        clickArea = itemView.findViewById(R.id.discoveryfragment_default_kategoriborder_clickarea)
        titleTv = itemView.findViewById(R.id.discoveryfragment_default_kategoriborder_title)
        this.parentView = parentView
        this.mainFlManager = mainFlManager
    }
    fun bind(index:Int){
        val w = parentView.width
        val size = (w/5).toInt()
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

                titleTv.setText(snapshot.child(index.toString()).child("title").getValue().toString())
                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun runClickListener(index: Int){
        clickArea.setOnClickListener {
            if(adapterPosition==5){
                Handler().postDelayed({
                    loadFrag.transfer(mainFlManager,R.id.homeactivity_flmanager,DiscoveryFragmentListKategori(mainFlManager,thisContext,navbar,activity,appContext,parentHome))
                },1000)
            }else{
                Handler().postDelayed({
                    loadFrag.transfer(mainFlManager,R.id.homeactivity_flmanager,DiscoveryFragmentDetailKategori(mainFlManager,thisContext,navbar,index.toString(),activity,appContext,parentHome))
                },1000)
            }
        }
    }
}