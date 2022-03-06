package com.example.bccintern3.home.discovery

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.vp_adapterdetailkategori.VpAdapterDetailKategori
import com.example.bccintern3.invisiblefunction.BackHandler
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DiscoveryFragmentDetailKategori(private val flManager:FragmentManager,
                                      private val thisContext: Context,
                                      private val navbar: BottomNavigationView,
                                      private val id:String,
                                      private val activity: AppCompatActivity
):Fragment(R.layout.home_discoveryfragment_kategori_detail) {
    private lateinit var judulTv:TextView
    private lateinit var gambarVp:ViewPager2
    private lateinit var deskripsiTv:TextView
    private lateinit var tampilDesainerBtn:Button
    private lateinit var loadFrag:LoadFragment
    private lateinit var dbRef:DbReference
    private lateinit var storageRef:StorageReference
    private lateinit var vpAdapter:VpAdapterDetailKategori

    fun init(view:View){
        judulTv = view.findViewById(R.id.discoveryfragment_kategoridetail_namakategori_tv)
        gambarVp = view.findViewById(R.id.discoveryfragment_kategoridetail_banner_vp)
        deskripsiTv = view.findViewById(R.id.discoveryfragment_kategoridetail_deskripsi_tv)
        tampilDesainerBtn = view.findViewById(R.id.discoveryfragment_kategoridetail_tampilkan_btn)
        loadFrag = LoadFragment()
        dbRef = DbReference()
        storageRef = FirebaseStorage.getInstance().getReference()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        loadViewPager(view)
        tampilDesainerBtn.isVisible=false
        judulTv.setText(". . .")
        Handler().postDelayed({
            tampilDesainerBtn.isVisible=true
            loadDataRequired()
        },2000)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler
                handler.loadFragment(flManager,R.id.homeactivity_flmanager,
                    DiscoveryFragmentDefault(flManager,context, navbar, activity)
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
    fun loadViewPager(view:View){
        val ref = storageRef.child("category_banner").child(id)

        ref.listAll().addOnSuccessListener {
            val url = ArrayList<String>()
            var param=0
            for(i:StorageReference in it.items){
                val size = it.items.size
                i.downloadUrl.addOnSuccessListener {
                    url.add(it.toString())
                    param++
                    if(param==size){
                        vpAdapter = VpAdapterDetailKategori(url,view,flManager,navbar,activity)
                        gambarVp.adapter = vpAdapter
                    }
                }
            }
        }
    }
    fun loadDataRequired(){
        val ref = dbRef.refCategoryPicture().child(id)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                judulTv.setText(snapshot.child("title").getValue().toString())
                deskripsiTv.setText(snapshot.child("deskripsi").getValue().toString())

                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}