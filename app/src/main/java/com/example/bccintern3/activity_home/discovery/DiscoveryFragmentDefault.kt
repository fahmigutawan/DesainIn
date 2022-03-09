package com.example.bccintern3.activity_home.discovery

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.discovery.adapterdefaultarticle.RvAdapterDefaultArticle
import com.example.bccintern3.activity_home.discovery.adapterdefaultcategory.RvAdapterDefaultKategori
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DiscoveryFragmentDefault(private val flManager:FragmentManager,
                               private val thisContext: Context,
                               private val navbar:BottomNavigationView,
                               private var activity: AppCompatActivity,
                               private var appContext: Context
                               ):Fragment(R.layout.home_discoveryfragment_default) {

    private lateinit var kategoriAdapter:RvAdapterDefaultKategori
    private lateinit var artikelAdapter:RvAdapterDefaultArticle
    private lateinit var kategoriRv:RecyclerView
    private lateinit var artikelRv:RecyclerView
    private lateinit var artikelLainTv:TextView
    private lateinit var loadFrag:LoadFragment

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
    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }

    fun runClickListener(){
        artikelLainTv.setOnClickListener {
            Handler().postDelayed({
                loadFrag.transfer(
                    flManager,
                    R.id.homeactivity_flmanager,
                    DiscoveryFragmentListArtikel(flManager,thisContext,navbar,activity,appContext)
                )},1000)
        }
    }
    fun setKategori(view: View){
        kategoriAdapter = RvAdapterDefaultKategori(view,flManager,thisContext,navbar,activity,appContext)
        val gridLayoutManager = GridLayoutManager(thisContext,3)
        kategoriRv.layoutManager = gridLayoutManager
        kategoriRv.adapter = kategoriAdapter
    }
    fun setArtikel(view: View){
        artikelAdapter = RvAdapterDefaultArticle(view,flManager,activity,navbar,appContext)
        val linearLayoutManager = LinearLayoutManager(thisContext)
        artikelRv.layoutManager = linearLayoutManager
        artikelRv.adapter = artikelAdapter
    }
    fun init(view:View){
        kategoriRv = view.findViewById(R.id.discoveryfragment_default_kategori_rv)
        artikelRv = view.findViewById(R.id.discoveryfragment_default_artikel_rv)
        artikelLainTv = view.findViewById(R.id.discoveryfragment_default_artikellain_tv)
        loadFrag = LoadFragment()
        runClickListener()
    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler
                val time = System.currentTimeMillis()
                val toast = Toast.makeText(thisContext,"Tekan kembali lagi untuk keluar", Toast.LENGTH_SHORT)

                if(time+1500>System.currentTimeMillis()){
                    handler.exit(activity)
                    toast.cancel()
                }
                else{
                    toast.show()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}