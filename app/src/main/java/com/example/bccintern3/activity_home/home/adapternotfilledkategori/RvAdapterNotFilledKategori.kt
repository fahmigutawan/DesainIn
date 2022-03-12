package com.example.bccintern3.activity_home.home.adapternotfilledkategori

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.activity_home.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class RvAdapterNotFilledKategori(private var parentView:View,
                                 private var mainFlManager:FragmentManager,
                                 private var thisCOntext:Context,
                                 private var navbar:BottomNavigationView,
                                 private var activity: AppCompatActivity,
                                 private var appContext: Context,
                                 private var parentHome: HomeActivity
                                 )
    : RecyclerView.Adapter<RvAdapterNotFilledKategoriViewHolder>()

{
    private var indexRandomized:ArrayList<Int>

    init {
        indexRandomized = ArrayList()
        runRandomizer()
    }
    fun runRandomizer(){
        indexRandomized.add((1..10).random())
        var i=0
        while(true){
            val tmp = (1..10).random()
            var j = 0
            while (j<indexRandomized.size){
                if(tmp==indexRandomized.get(j)){
                    break
                }else{
                    j++
                }
                if(j==indexRandomized.size){
                    indexRandomized.add(tmp)
                }
            }
            if(indexRandomized.size==3) break
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterNotFilledKategoriViewHolder {
        return RvAdapterNotFilledKategoriViewHolder(
            LayoutInflater.from(parent.context),
            parent,
            parentView,
            mainFlManager,
            thisCOntext,
            navbar,
            activity
            ,appContext,parentHome)
    }
    override fun onBindViewHolder(holder: RvAdapterNotFilledKategoriViewHolder, position: Int) {
        holder.bind(indexRandomized.get(position))
    }
    override fun getItemCount(): Int {
        return 3
    }
}