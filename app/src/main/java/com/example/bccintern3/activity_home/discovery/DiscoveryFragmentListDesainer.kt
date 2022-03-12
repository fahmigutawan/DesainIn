package com.example.bccintern3.activity_home.discovery

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.activity_home.discovery.adapterlistdesainer.RvAdapterListDesainer
import com.example.bccintern3.activity_home.home.HomeFragment
import com.example.bccintern3.activity_home.home.HomeFragmentNotFilled
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DiscoveryFragmentListDesainer(private val flManager: FragmentManager,
                                    private val thisContext: Context,
                                    private val navbar: BottomNavigationView,
                                    private val idKategori:String?,
                                    private val activity:AppCompatActivity,
                                    private val appContext: Context,
                                    private var parent:HomeActivity
):Fragment(R.layout.home_discoveryfragment_listdesainer) {
    private lateinit var desainerRv:RecyclerView
    private lateinit var kategoriTv:TextView
    private lateinit var dbRef:DbReference
    private lateinit var rvAdapter:RvAdapterListDesainer

    fun init(view: View){
        desainerRv = view.findViewById(R.id.discoveryfragment_listdesainer_rv)
        kategoriTv = view.findViewById(R.id.discoveryfragment_listdesainer_kategoritv)
        dbRef = DbReference()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setRvAdapter(view)
    }
    fun setRvAdapter(view:View){
        view.viewTreeObserver.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                if(idKategori == null){
                    val ref = dbRef.refDesignerNode()
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            ref.removeEventListener(this)

                            val uid=ArrayList<String>()
                            val idKategori=ArrayList<String>()

                            /**iterator kategori**/
                            for(i:DataSnapshot in snapshot.children){

                                /**iterator desainer**/
                                for(j:DataSnapshot in i.children){
                                    uid.add(j.child("uid").getValue().toString())
                                    idKategori.add(j.child("id_kategori").getValue().toString())
                                }
                            }

                            rvAdapter = RvAdapterListDesainer(
                                view,
                                flManager,
                                thisContext,
                                navbar,
                                uid,
                                idKategori,
                                activity,appContext,parent)

                            val linearLayoutManager = LinearLayoutManager(thisContext,LinearLayoutManager.VERTICAL,false)
                            desainerRv.layoutManager = linearLayoutManager
                            desainerRv.adapter = rvAdapter
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
                else{
                    val ref = dbRef.refDesignerNode().child(idKategori)
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            ref.removeEventListener(this)

                            val uid=ArrayList<String>()
                            val idKategori=ArrayList<String>()

                            /**iterator desainer**/
                            for(j:DataSnapshot in snapshot.children){
                                uid.add(j.child("uid").getValue().toString())
                                idKategori.add(j.child("id_kategori").getValue().toString())
                            }

                            rvAdapter = RvAdapterListDesainer(
                                view,
                                flManager,
                                thisContext,
                                navbar,
                                uid,
                                idKategori,
                                activity,appContext,parent)

                            val linearLayoutManager = LinearLayoutManager(thisContext,LinearLayoutManager.VERTICAL,false)
                            desainerRv.layoutManager = linearLayoutManager
                            desainerRv.adapter = rvAdapter
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
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

                Handler().postDelayed({
                    navbar.menu.getItem(1).setChecked(false)
                    navbar.menu.getItem(0).setChecked(true)
                    handler.loadFragment(flManager,R.id.homeactivity_flmanager,
                        HomeFragment(flManager, activity, thisContext, navbar,appContext,parent)
                    )
                },1000)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}