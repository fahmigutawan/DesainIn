package com.example.bccintern3.home.home.adapternotfilleddesainer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RvAdapterNotFilledDesainer(private var parentView: View,
                                 private var mainFlManager: FragmentManager,
                                 private var navbar: BottomNavigationView,
                                 private var activity: AppCompatActivity,
                                 private var designerList:ArrayList<String>,
                                 private var idDesignerKategori:ArrayList<String>,
                                 private var thisContext:Context
                                 )
    :RecyclerView.Adapter<RvADapterNotFilledDesainerViewHolder>() {

    private var indexRandomized:ArrayList<Int>

    init {
        indexRandomized = ArrayList()
        runRandomizer()
    }
    fun runRandomizer(){
        val size = designerList.size-1
        indexRandomized.add((0..size).random())
        while(true){
            val tmp = (0..size).random()
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvADapterNotFilledDesainerViewHolder {
        return RvADapterNotFilledDesainerViewHolder(
            LayoutInflater.from(parent.context),
            parent,
            parentView,
            mainFlManager,
            navbar,
            activity,
            designerList,
            idDesignerKategori,
            thisContext
            )
    }
    override fun onBindViewHolder(holder: RvADapterNotFilledDesainerViewHolder, position: Int) {
        holder.bind(indexRandomized.get(position))
    }
    override fun getItemCount(): Int {
        return 3
    }
}