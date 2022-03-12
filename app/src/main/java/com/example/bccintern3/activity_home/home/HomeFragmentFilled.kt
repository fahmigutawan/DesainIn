package com.example.bccintern3.activity_home.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.activity_home.home.adapterfilledsearch.RvAdapterFilledSearch
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class HomeFragmentFilled(private var flManager: FragmentManager,
                         private var childFlManager: FragmentManager,
                         private var activity:AppCompatActivity,
                         private var search:TextInputEditText,
                         private var thisContext: Context,
                         private var navbar:BottomNavigationView,
                         private var keyword:String,
                         private var uid:ArrayList<String>,
                         private var nama:ArrayList<String>,
                         private var idKategori:ArrayList<String>,
                         private var appContext: Context,
                         private var parentHome: HomeActivity
                         )
    :Fragment(R.layout.home_homefragment_filled) {

    private lateinit var searchRv:RecyclerView
    private lateinit var loadFrag:LoadFragment
    private lateinit var loadAct:LoadActivity
    private lateinit var dbRef:DbReference
    private lateinit var resultUidSearch:ArrayList<String>
    private lateinit var resultIdKategori:ArrayList<String>

    fun init(view:View){
        searchRv = view.findViewById(R.id.homefragment_filled_search_rv)
        loadAct = LoadActivity()
        loadFrag = LoadFragment()
        dbRef = DbReference()
        resultUidSearch = ArrayList()
        resultIdKategori = ArrayList()
        analyze()
    }

    fun analyze(){
        var param=0
        for(item:String in nama){
            if(item.contains(keyword)){
                resultUidSearch.add(uid.get(param))
                resultIdKategori.add(idKategori.get(param))
            }
            param++
        }
    }
    fun createRecyclerView(view: View){
        val adapter = RvAdapterFilledSearch(view,flManager,navbar,activity,resultUidSearch,resultIdKategori,thisContext,appContext,parentHome)
        searchRv.layoutManager = LinearLayoutManager(thisContext,LinearLayoutManager.VERTICAL,false)
        searchRv.adapter = adapter
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        createRecyclerView(view)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler
                search.setText("")
                handler.loadFragment(flManager,R.id.home_homefragment_fl,HomeFragmentNotFilled(flManager,activity,thisContext,navbar,appContext,parentHome))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }

}
