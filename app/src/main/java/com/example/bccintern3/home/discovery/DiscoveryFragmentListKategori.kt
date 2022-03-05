package com.example.bccintern3.home.discovery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.home.discovery.adapterlistcategory.RvAdapterListKategori
import com.google.android.material.bottomnavigation.BottomNavigationView

class DiscoveryFragmentListKategori(private val flManager: FragmentManager,
                                    private val thisContext: Context,
                                    private val navbar: BottomNavigationView
):Fragment(R.layout.home_discoveryfragment_listkategori) {
    private lateinit var listRv:RecyclerView
    private lateinit var rvAdapter:RvAdapterListKategori

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setPreview(view)
    }
    fun init(view: View){
        listRv = view.findViewById(R.id.discoveryfragment_listkategori_rv)
    }
    fun setPreview(view: View){
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                rvAdapter = RvAdapterListKategori(view,flManager)
                val linearLayoutManager = LinearLayoutManager(thisContext,LinearLayoutManager.VERTICAL,false)
                listRv.layoutManager = linearLayoutManager
                listRv.adapter = rvAdapter
            }
        })
    }
}