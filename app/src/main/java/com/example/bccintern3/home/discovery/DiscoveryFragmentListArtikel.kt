package com.example.bccintern3.home.discovery

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
import com.example.bccintern3.home.discovery.adapterlistartikel.RvAdapterListArtikel
import com.example.bccintern3.home.home.HomeFragmentNotFilled
import com.example.bccintern3.invisiblefunction.BackHandler
import com.example.bccintern3.invisiblefunction.DbReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DiscoveryFragmentListArtikel(private val flManager: FragmentManager,
                                   private val thisContext: Context,
                                   private val navbar: BottomNavigationView,
                                   private val activity:AppCompatActivity
):Fragment(R.layout.home_discoveryfragment_listartikel) {

    private lateinit var artikelRv:RecyclerView
    private lateinit var rvAdapter:RvAdapterListArtikel
    private lateinit var dbRef:DbReference
    private lateinit var arrAtribut:ArrayList<ArrayList<String>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setArtikelRv(view)
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
                    DiscoveryFragmentDefault(flManager,context, navbar, activity)
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
    fun init(view:View){
        artikelRv = view.findViewById(R.id.discoveryfragment_listartikel_rv)
        dbRef = DbReference()
        arrAtribut = ArrayList()
        arrAtribut.add(ArrayList())
        arrAtribut.add(ArrayList())
        arrAtribut.add(ArrayList())
        arrAtribut.add(ArrayList())
        arrAtribut.add(ArrayList())
        arrAtribut.add(ArrayList())
        arrAtribut.add(ArrayList())
    }
    fun setArtikelRv(view:View){
        view.viewTreeObserver.addOnGlobalLayoutListener(object:ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val ref = dbRef.refArticle()
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
                                        arrAtribut.get(2).add(disini.child("tanggal").getValue().toString())
                                        arrAtribut.get(3).add(disini.child("hari").getValue().toString())
                                        arrAtribut.get(4).add(disini.child("bulan").getValue().toString())
                                        arrAtribut.get(5).add(disini.child("tahun").getValue().toString())
                                        arrAtribut.get(6).add(disini.child("id").getValue().toString())
                                    }
                                }
                            }
                        }
                        rvAdapter = RvAdapterListArtikel(view,flManager,arrAtribut,navbar,activity)
                        val linearLayoutManager = LinearLayoutManager(thisContext,LinearLayoutManager.VERTICAL,false)
                        artikelRv.layoutManager = linearLayoutManager
                        artikelRv.adapter=rvAdapter
                        ref.removeEventListener(this)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        })
    }

}