package com.example.bccintern3.home.discovery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.adapterdefaultarticle.RvAdapterDefaultArticle
import com.example.bccintern3.home.discovery.adapterdefaultcategory.RvAdapterDefaultKategori
import com.google.android.material.bottomnavigation.BottomNavigationView

class DiscoveryFragmentDefault(private val flManager:FragmentManager,
                               private val thisContext: Context,
                               private val navbar:BottomNavigationView,
                               private var activity: AppCompatActivity
                               ):Fragment(R.layout.home_discoveryfragment_default) {

    private lateinit var kategoriAdapter:RvAdapterDefaultKategori
    private lateinit var artikelAdapter:RvAdapterDefaultArticle
    private lateinit var kategoriRv:RecyclerView
    private lateinit var artikelRv:RecyclerView
    private lateinit var artikelLainTv:TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                /**KATEGORI**/
                setKategori(view)

                /****/
                setArtikel(view)
            }
        })
    }

    fun setKategori(view: View){
        kategoriAdapter = RvAdapterDefaultKategori(view,flManager)
        val gridLayoutManager = GridLayoutManager(thisContext,3)
        kategoriRv.layoutManager = gridLayoutManager
        kategoriRv.adapter = kategoriAdapter
    }
    fun setArtikel(view: View){
        artikelAdapter = RvAdapterDefaultArticle(view,flManager)
        val linearLayoutManager = LinearLayoutManager(thisContext)
        artikelRv.layoutManager = linearLayoutManager
        artikelRv.adapter = artikelAdapter
    }
    fun init(view:View){
        kategoriRv = view.findViewById(R.id.discoveryfragment_default_kategori_rv)
        artikelRv = view.findViewById(R.id.discoveryfragment_default_artikel_rv)
        artikelLainTv = view.findViewById(R.id.discoveryfragment_default_artikellain_tv)
    }
}