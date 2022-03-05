package com.example.bccintern3.home.discovery.adapterdefaultarticle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.invisiblefunction.DbReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RvAdapterDefaultArticle(private var parentView: View,
                              private var mainFlManager: FragmentManager
                              ):RecyclerView.Adapter<RvAdapterDefaultArticleViewHolder>() {
    private lateinit var arrAtribut:ArrayList<ArrayList<String>>
    private lateinit var dbRef:DbReference
    private var maxCount=3

    fun init(){
        arrAtribut = ArrayList()
        dbRef = DbReference()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterDefaultArticleViewHolder {
        init()
        return RvAdapterDefaultArticleViewHolder(
            LayoutInflater.from(parent.context),
            parent,
            parentView,
            mainFlManager
        )
    }
    override fun onBindViewHolder(holder: RvAdapterDefaultArticleViewHolder, position: Int) {
        val ref = dbRef.refArticle()
        val position = position

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /**iterasi tahun**/
                for(i:DataSnapshot in snapshot.children){
                    /**iterasi bulan**/
                    for(j:DataSnapshot in i.children){
                        /**iterasi tanggal**/
                        for(k:DataSnapshot in j.children){
                            /**baru disini iterasi child**/
                            for(disini:DataSnapshot in k.children){
                                arrAtribut.get(0).add(disini.child("image").getValue().toString())
                                arrAtribut.get(1).add(disini.child("judul").getValue().toString())
                                arrAtribut.get(2).add(disini.child("penulis").getValue().toString())
                                arrAtribut.get(3).add(disini.child("tanggal").getValue().toString())
                                arrAtribut.get(4).add(disini.child("hari").getValue().toString())
                                arrAtribut.get(5).add(disini.child("bulan").getValue().toString())
                                arrAtribut.get(6).add(disini.child("tahun").getValue().toString())

                                if(arrAtribut.get(0).size==maxCount) break
                            }
                            if(arrAtribut.get(0).size==maxCount) break
                        }
                        if(arrAtribut.get(0).size==maxCount) break
                    }
                    if(arrAtribut.get(0).size==maxCount) break
                }
                holder.bind()
                ref.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
    override fun getItemCount(): Int {
        return maxCount
    }
}