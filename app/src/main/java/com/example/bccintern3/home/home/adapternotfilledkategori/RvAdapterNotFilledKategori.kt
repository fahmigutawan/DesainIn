package com.example.bccintern3.home.home.adapternotfilledkategori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class RvAdapterNotFilledKategori(private var parentView:View,
                                 private var mainFlManager:FragmentManager)
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
            mainFlManager)
    }
    override fun onBindViewHolder(holder: RvAdapterNotFilledKategoriViewHolder, position: Int) {
        holder.bind(indexRandomized.get(position))
    }
    override fun getItemCount(): Int {
        return 3
    }
}