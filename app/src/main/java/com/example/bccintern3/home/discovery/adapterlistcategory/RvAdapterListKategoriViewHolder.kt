package com.example.bccintern3.home.discovery.adapterlistcategory

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
import com.example.bccintern3.home.discovery.DiscoveryFragmentDetailKategori
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.invisiblefunction.LoadActivity
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class RvAdapterListKategoriViewHolder(inflater: LayoutInflater,
                                      parent: ViewGroup,
                                      parentView: View,
                                      mainFlManager: FragmentManager,
                                      private var thisContext:Context,
                                      private var navbar:BottomNavigationView,
                                      private var activity: AppCompatActivity
                                      )
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_listkategori_borderitem,parent,false))
{
    private var parentView = parentView
    private var mainFlManager = mainFlManager
    private var clickableArea:LinearLayout
    private var imageView:ImageView
    private var title:TextView
    private var dbRef:DbReference
    private var loadAct:LoadActivity
    private var loadFrag:LoadFragment

    init {
        imageView = itemView.findViewById(R.id.discoveryfragment_listkategori_border_iv)
        title = itemView.findViewById(R.id.discoveryfragment_listkategori_border_title_tv)
        clickableArea = itemView.findViewById(R.id.discoveryfragment_listkategori_border_clickarea)
        dbRef = DbReference()
        loadAct = LoadActivity()
        loadFrag = LoadFragment()
    }
    fun bind(idKategori:String){
        val size=parentView.width/5

        val ref = dbRef.refCategoryPicture().child(idKategori)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /**set gambar**/
                Picasso
                    .get()
                    .load(snapshot.child("icon").getValue().toString())
                    .transform(RoundCornerRect(30f,0f,0f,0f,0f))
                    .resize(size,size)
                    .into(imageView)

                runClickListener(idKategori)
                /**set title**/
                title.setText(snapshot.child("title").getValue().toString())

                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun runClickListener(id:String){
        clickableArea.setOnClickListener {
            Handler().postDelayed({
                loadFrag.transfer(mainFlManager,R.id.homeactivity_flmanager,DiscoveryFragmentDetailKategori(mainFlManager,thisContext,navbar,id,activity))
            },1000)
        }
    }
}