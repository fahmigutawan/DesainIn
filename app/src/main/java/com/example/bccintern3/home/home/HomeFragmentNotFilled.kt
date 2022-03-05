package com.example.bccintern3.home.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.DiscoveryFragmentListKategori
import com.example.bccintern3.home.home.adapternotfilledartikel.RvAdapterNotFilledArtikel
import com.example.bccintern3.home.home.adapternotfilledkategori.RvAdapterNotFilledKategori
import com.example.bccintern3.invisiblefunction.BackHandler
import com.example.bccintern3.invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragmentNotFilled(private var flManager: FragmentManager,
                            private var activity:AppCompatActivity,
                            private var thisContext: Context,
                            private var navbar:BottomNavigationView):Fragment(R.layout.home_homefragment_notfilled) {

    private lateinit var artikelAdapter:RvAdapterNotFilledArtikel
    private lateinit var kategoriAdapter:RvAdapterNotFilledKategori
    private lateinit var kategoriRv:RecyclerView
    private lateinit var artikelRv:RecyclerView
    private lateinit var desainerRv:RecyclerView
    private lateinit var bannerVp:ViewPager2
    private lateinit var desainerLainTv:TextView
    private lateinit var artikelLainTv:TextView
    private lateinit var kategoriLainTv:TextView
    private lateinit var loadFrag:LoadFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setKategoriView(view)
        setArtikelView(view)
        runTouchListener()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }
    fun init(view: View){
        kategoriRv = view.findViewById(R.id.homefragment_notfill_kategori_rv)
        artikelRv = view.findViewById(R.id.homefragment_notfill_artikel_rv)
        desainerRv = view.findViewById(R.id.homefragment_notfill_desainer_rv)
        bannerVp = view.findViewById(R.id.homefragment_notfill_banner_vp)
        desainerLainTv = view.findViewById(R.id.homefragment_notfill_rekomenlain_tv)
        artikelLainTv = view.findViewById(R.id.homefragment_notfill_artikellain_tv)
        kategoriLainTv = view.findViewById(R.id.homefragment_notfill_kategorilain_tv)
        loadFrag = LoadFragment()
    }
    fun runTouchListener(){
        kategoriLainTv.setOnClickListener {
            navbar.menu.getItem(0).setChecked(false)
            navbar.menu.getItem(1).setChecked(true)
            loadFrag.transfer(flManager,R.id.homeactivity_flmanager,DiscoveryFragmentListKategori(flManager,thisContext,navbar))
        }
    }
    fun setKategoriView(view: View){
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                kategoriAdapter = RvAdapterNotFilledKategori(view,flManager)
                val gridLayoutManager = GridLayoutManager(thisContext,3)
                kategoriRv.layoutManager = gridLayoutManager
                kategoriRv.adapter = kategoriAdapter
            }

        })
    }
    fun setArtikelView(view: View){
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                artikelAdapter = RvAdapterNotFilledArtikel(view,flManager)
                val linearLayoutManager = LinearLayoutManager(thisContext,LinearLayoutManager.VERTICAL,false)
                val gridLayoutManager = GridLayoutManager(thisContext,2,GridLayoutManager.VERTICAL,false)
                artikelRv.layoutManager = gridLayoutManager
                artikelRv.adapter = artikelAdapter
            }
        })
    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler
                handler.exit(activity)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}