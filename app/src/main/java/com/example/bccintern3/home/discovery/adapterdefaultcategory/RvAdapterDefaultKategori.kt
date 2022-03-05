package com.example.bccintern3.home.discovery.adapterdefaultcategory

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class RvAdapterDefaultKategori(private var parentView: View,
                               private var mainFlManager:FragmentManager) : RecyclerView.Adapter<RvAdapterDefaultKategoriViewHolder>() {

    private var indexRandomized:ArrayList<Int>
    init {
        indexRandomized = ArrayList()
        runRandomizer()
    }

    fun runRandomizer(){
        indexRandomized.add((1..10).random())
        var i=0
        while(true){
            val tmp = Random.nextInt(1,10)
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
            if(indexRandomized.size==5) break
        }
        indexRandomized.add(12)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterDefaultKategoriViewHolder {
        return RvAdapterDefaultKategoriViewHolder(
            LayoutInflater.from(parent.context)
            ,parent
            ,parentView
            ,mainFlManager)
    }

    override fun onBindViewHolder(holder: RvAdapterDefaultKategoriViewHolder, position: Int) {
        holder.bind(indexRandomized.get(position))
    }

    override fun getItemCount(): Int {
        return 6
    }
}