package com.example.bccintern3.activity_home.discovery.adapterlistdesainer

import android.content.Context
import android.util.Log
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
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentDetailDesainer
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class RvAdapterListDesainerViewHolder(
    private var inflater:LayoutInflater,
    private var parent:ViewGroup,
    private var parentView:View,
    private var flManager: FragmentManager,
    private var thisContext: Context,
    private var navbar: BottomNavigationView,
    private var activity: AppCompatActivity,
    private var appContext: Context,
    private var parentHome: HomeActivity
)
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_listdesainer_borderitem,parent,false))
{
    private var clickArea:LinearLayout
    private var img:ImageView
    private var nama:TextView
    private var kategori:TextView
    private var harga:TextView
    private var dbRef:DbReference
    private var loadAct:LoadActivity
    private var loadFrag:LoadFragment

    init {
        clickArea = itemView.findViewById(R.id.discoveryfragment_listdesainer_border_clickarea)
        img = itemView.findViewById(R.id.discoveryfragment_listdesainer_border_imageiv)
        nama = itemView.findViewById(R.id.discoveryfragment_listdesainer_border_namatv)
        kategori = itemView.findViewById(R.id.discoveryfragment_listdesainer_border_kategoritv)
        harga = itemView.findViewById(R.id.discoveryfragment_listdesainer_border_hargatv)
        dbRef = DbReference()
        loadAct = LoadActivity()
        loadFrag = LoadFragment()
    }
    fun bind(uid:String,idKategori:String){
        runClickListener(uid,idKategori)

        Log.e("UID",uid)
        Log.e("ID KATEGORI",idKategori)
        val size = parentView.width/4

        val ref = dbRef.refUidNode(uid).child("profile")
        val ref2 = dbRef.refDesignerNode().child(idKategori).child(uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /**set data**/
                nama.setText(snapshot.child("name").getValue().toString())

                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Picasso
                    .get()
                    .load(snapshot.child("icon").getValue().toString())
                    .transform(RoundCornerRect(20f,0f,0f,0f,0f))
                    .resize(size,size)
                    .into(img)

                kategori.setText(snapshot.child("kategori").getValue().toString())
                harga.setText("Mulai dari Rp. "+snapshot.child("start harga").getValue().toString())
                ref2.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun runClickListener(uid:String,idKategori: String){
        clickArea.setOnClickListener {
            loadFrag.transfer(
                flManager,
                R.id.homeactivity_flmanager,
                DiscoveryFragmentDetailDesainer(flManager,thisContext,navbar,activity,uid,idKategori,appContext,parentHome)
                )
        }
    }
}