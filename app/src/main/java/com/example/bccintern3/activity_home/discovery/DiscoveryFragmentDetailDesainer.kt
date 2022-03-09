package com.example.bccintern3.activity_home.discovery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.discovery.vp_adapterdetaildesainer.VpAdapterDetailDesainer
import com.example.bccintern3.nonactivity_invisiblefunction.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso

class DiscoveryFragmentDetailDesainer(private val flManager:FragmentManager,
                                      private val thisContext: Context,
                                      private val navbar: BottomNavigationView,
                                      private val activity:AppCompatActivity,
                                      private val uidSelected:String,
                                      private val idKategoriSelected:String,
                                      private val appContext: Context
):Fragment(R.layout.home_discoveryfragment_desainer_detail) {
    private lateinit var archiveCb:CheckBox
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
        archiveCb = view.findViewById(R.id.discoveryfragment_desainerdetail_bookmark_cb)
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
        setDataPreview()
        runClickListener()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler

                handler.loadFragment(flManager,R.id.homeactivity_flmanager, DiscoveryFragmentDefault(flManager,thisContext, navbar, activity,appContext))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
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
    fun setDataPreview(){
        val ref = dbRef.refDesignerNode().child(idKategoriSelected).child(uidSelected)
        val ref2 = dbRef.refUidNode(uidSelected).child("profile")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)

                kategoriTv.setText(snapshot.child("kategori").getValue().toString())
                deskripsiTv.setText(snapshot.child("deskripsi").getValue().toString())
                lokasiTv.setText(snapshot.child("lokasi").getValue().toString())
                hargaTv.setText(snapshot.child("start harga").getValue().toString())

                ref2.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ref2.removeEventListener(this)

                        /**set nama**/
                        nameTv.setText(snapshot.child("name").getValue().toString())

                        /**set gambar**/
                        Picasso
                            .get()
                            .load(snapshot.child("pictureUrl").getValue().toString())
                            .transform(CircleTransform())
                            .into(picPicture)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun runClickListener(){


        chatBtn.setOnClickListener {

        }
    }
}