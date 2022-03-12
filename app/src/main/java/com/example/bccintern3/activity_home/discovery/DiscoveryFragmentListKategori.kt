package com.example.bccintern3.activity_home.discovery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.activity_home.discovery.adapterlistcategory.RvAdapterListKategori
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.google.android.material.bottomnavigation.BottomNavigationView

class DiscoveryFragmentListKategori(private val flManager: FragmentManager,
                                    private val thisContext: Context,
                                    private val navbar: BottomNavigationView,
                                    private val activity: AppCompatActivity,
                                    private val appContext: Context,
                                    private var parentHome: HomeActivity
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
                rvAdapter = RvAdapterListKategori(view,flManager,thisContext, navbar,activity,appContext,parentHome)
                val linearLayoutManager = LinearLayoutManager(thisContext,LinearLayoutManager.VERTICAL,false)
                listRv.layoutManager = linearLayoutManager
                listRv.adapter = rvAdapter
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