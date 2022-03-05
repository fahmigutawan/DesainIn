package com.example.bccintern3.home.home.adapternotfilledartikel

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

class RvAdapterNotFilledArtikel(private var parentView: View,
                                private var mainFlManager: FragmentManager)
    :RecyclerView.Adapter<RvAdapterNotFilledArtikelViewHolder>()
{
    private lateinit var imgUrl:ArrayList<String>
    private lateinit var judul:ArrayList<String>
    private lateinit var dbRef: DbReference
    private var maxCount=2

    fun init(){
        imgUrl = ArrayList()
        judul = ArrayList()
        dbRef = DbReference()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterNotFilledArtikelViewHolder {
        init()
        return RvAdapterNotFilledArtikelViewHolder(LayoutInflater.from(parent.context)
            ,parent
            ,parentView
            ,mainFlManager)
    }
    override fun onBindViewHolder(holder: RvAdapterNotFilledArtikelViewHolder, position: Int) {
        val ref = dbRef.refArticle()
        imgUrl.clear()
        judul.clear()
        val position = position

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /**iterasi tahun**/
                for (i: DataSnapshot in snapshot.children) {
                    /**iterasi bulan**/
                    for (j: DataSnapshot in i.children) {
                        /**iterasi tanggal**/
                        for (k: DataSnapshot in j.children) {
                            /**baru disini iterasi child**/
                            for (disini: DataSnapshot in k.children) {
                                imgUrl.add(disini.child("image").getValue().toString())
                                judul.add(disini.child("judul").getValue().toString())

                                if (imgUrl.size == maxCount) break
                            }
                            if (imgUrl.size == maxCount) break
                        }
                        if (imgUrl.size == maxCount) break
                    }
                    if (imgUrl.size == maxCount) break
                }
                holder.bind(
                    imgUrl.get(position),
                    judul.get(position))
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