package com.example.bccintern3.activity_home.home.adapterfilledsearch

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
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentDetailDesainer
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlin.concurrent.fixedRateTimer

class RvAdapterFilledSearchViewHolder(private var inflater: LayoutInflater,
                                      private var parent:ViewGroup,
                                      private var parentView: View,
                                      private var mainFlManager: FragmentManager,
                                      private var navbar: BottomNavigationView,
                                      private var activity: AppCompatActivity,
                                      private var thisContext:Context,
                                      private var appContext: Context
):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.home_filled_searched_border,parent,false)) {

    private var clickArea:LinearLayout
    private var img:ImageView
    private var nama:TextView
    private var kategori:TextView
    private var harga:TextView
    private var dbRef:DbReference
    private var loadFrag:LoadFragment
    private var loadAct:LoadActivity

    init {
        clickArea = itemView.findViewById(R.id.homefragment_filled_search_border_clickarea)
        img = itemView.findViewById(R.id.homefragment_filled_search_border_imageiv)
        nama = itemView.findViewById(R.id.homefragment_filled_search_border_namatv)
        kategori = itemView.findViewById(R.id.homefragment_filled_search_border_kategoritv)
        harga = itemView.findViewById(R.id.homefragment_filled_search_border_hargatv)
        dbRef = DbReference()
        loadFrag = LoadFragment()
        loadAct = LoadActivity()
    }

    fun runNextFragment(uid:String,idKategori: String){
        clickArea.setOnClickListener {
            Handler().postDelayed({
                navbar.menu.getItem(0).setChecked(false)
                navbar.menu.getItem(1).setChecked(true)
                loadFrag.transfer(
                    mainFlManager,
                    R.id.homeactivity_flmanager,
                    DiscoveryFragmentDetailDesainer(mainFlManager,thisContext,navbar,activity,uid,idKategori,appContext)
                )
            },1000)
        }
    }
    fun bind(uid:String,idKategori:String){
        runNextFragment(uid, idKategori)
        /**set nama**/
        val ref1 = dbRef.refUidNode(uid).child("profile")
        ref1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref1.removeEventListener(this)
                nama.setText(snapshot.child("name").getValue().toString())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        /**set data lainnya**/
        val ref2 = dbRef.refDesignerNode().child(idKategori).child(uid)
        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref2.removeEventListener(this)

                /**set gambar**/
                Picasso
                    .get()
                    .load(snapshot.child("icon").getValue().toString())
                    .transform(CircleTransform())
                    .into(img)

                /**set data lain**/
                kategori.setText(snapshot.child("kategori").getValue().toString())
                harga.setText("Harga mulai dari Rp. "+snapshot.child("start harga").getValue().toString())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}