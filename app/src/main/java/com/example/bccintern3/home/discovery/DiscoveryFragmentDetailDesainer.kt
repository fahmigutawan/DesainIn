package com.example.bccintern3.home.discovery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.vp_adapterdetaildesainer.VpAdapterDetailDesainer
import com.example.bccintern3.invisiblefunction.DbReference
import com.example.bccintern3.invisiblefunction.LoadActivity
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DiscoveryFragmentDetailDesainer(private val flManager:FragmentManager,
                                      private val thisContext: Context,
                                      private val navbar: BottomNavigationView,
                                      private val activity:AppCompatActivity,
                                      private val uidSelected:String,
                                      private val idKategoriSelected:String
):Fragment(R.layout.home_discoveryfargment_desainer_detail) {
    private lateinit var archiveRb:RadioButton
    private lateinit var bannerVp:ViewPager2
    private lateinit var nameTv:TextView
    private lateinit var kategoriTv:TextView
    private lateinit var deskripsiTv:TextView
    private lateinit var lokasiTv:TextView
    private lateinit var hargaTv:TextView
    private lateinit var chatBtn:Button
    private lateinit var orderBtn:Button
    private lateinit var picPicture:ImageView
    private lateinit var widthParam:LinearLayout
    private lateinit var loadFrag:LoadFragment
    private lateinit var loadAct:LoadActivity
    private lateinit var dbRef:DbReference
    private lateinit var vpAdapter:VpAdapterDetailDesainer

    fun init(view: View){
        archiveRb = view.findViewById(R.id.discoveryfragment_desainerdetail_bookmark_rb)
        bannerVp = view.findViewById(R.id.discoveryfragment_desainerdetail_banner_vp)
        nameTv = view.findViewById(R.id.discoveryfragment_desainerdetail_nama_tv)
        kategoriTv = view.findViewById(R.id.discoveryfragment_desainerdetail_kategori_tv)
        deskripsiTv = view.findViewById(R.id.discoveryfragment_desainerdetail_deskripsi_tv)
        lokasiTv = view.findViewById(R.id.discoveryfragment_desainerdetail_lokasi_tv)
        hargaTv = view.findViewById(R.id.discoveryfragment_desainerdetail_harga_tv)
        chatBtn = view.findViewById(R.id.discoveryfragment_desainerdetail_chat_btn)
        orderBtn = view.findViewById(R.id.discoveryfragment_desainerdetail_order_btn)
        picPicture = view.findViewById(R.id.discoveryfragment_desainerdetail_profil_iv)
        widthParam = view.findViewById(R.id.discoveryfragment_desainerdetail_widthparam_layout)
        loadFrag = LoadFragment()
        loadAct = LoadActivity()
        dbRef = DbReference()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setVpBanner(view)
    }
    fun setVpBanner(view:View){
        val ref = dbRef.refDesignerNode().child(idKategoriSelected).child(uidSelected)

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ref.removeEventListener(this)
                        val banner = ArrayList<String>()

                        banner.add(snapshot.child("banner1").getValue().toString())
                        banner.add(snapshot.child("banner2").getValue().toString())
                        banner.add(snapshot.child("banner3").getValue().toString())

                        vpAdapter = VpAdapterDetailDesainer(banner,view)
                        bannerVp.adapter = vpAdapter
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

        })
    }
}