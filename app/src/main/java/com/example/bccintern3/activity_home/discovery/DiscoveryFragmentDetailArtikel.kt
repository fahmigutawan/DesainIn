package com.example.bccintern3.activity_home.discovery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class DiscoveryFragmentDetailArtikel(private var hari:String,
                                     private var bulan:String,
                                     private var tahun:String,
                                     private var id:String,
                                     private val flManager:FragmentManager,
                                     private val navbar:BottomNavigationView,
                                     private val activity:AppCompatActivity,
                                     private val appContext: Context,
                                     private var parentHome: HomeActivity
                                     ):Fragment(R.layout.home_discoveryfragment_artikel_detail) {
    private lateinit var articleImage:ImageView
    private lateinit var judulTv:TextView
    private lateinit var penulisTv:TextView
    private lateinit var tanggalTv:TextView
    private lateinit var isiTv:TextView
    private lateinit var dbRef:DbReference

    fun init(view: View){
        judulTv=view.findViewById(R.id.discoveryfragment_artikeldetail_judultv)
        articleImage=view.findViewById(R.id.discoveryfragment_artikeldetail_imageiv)
        penulisTv=view.findViewById(R.id.discoveryfragment_artikeldetail_penulistv)
        tanggalTv=view.findViewById(R.id.discoveryfragment_artikeldetail_tanggaltv)
        isiTv=view.findViewById(R.id.discoveryfragment_artikeldetail_isitv)
        dbRef= DbReference()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        runViewListener(view)
    }
    fun runViewListener(view: View){
        view.viewTreeObserver.addOnGlobalLayoutListener(object:ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                setDataPage(view)
            }
        })
    }
    fun setDataPage(view: View){
        val width = view.width
        val height = (width/1.8).toInt()

        val ref = dbRef.refArticle().child(tahun).child(bulan).child(hari).child(id)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val url=snapshot.child("image").getValue().toString()
                val judul=snapshot.child("judul").getValue().toString()
                val penulis=snapshot.child("penulis").getValue().toString()
                val tanggal=snapshot.child("tanggal").getValue().toString()
                val isi=snapshot.child("isi").getValue().toString()

                /**set gambar**/
                Picasso
                    .get()
                    .load(url)
                    .transform(RoundCornerRect(30f,0f,0f,0f,0f))
                    .resize(width,height)
                    .centerCrop()
                    .into(articleImage)

                /**set penulis**/
                penulisTv.setText(penulis)

                /**set tanggal**/
                tanggalTv.setText(tanggal)

                /**set judul**/
                judulTv.setText(judul)

                /**set isi**/
                isiTv.setText(isi)

                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
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
                    DiscoveryFragmentDefault(flManager,context, navbar, activity,appContext,parentHome)
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}