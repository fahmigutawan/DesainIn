package com.example.bccintern3.activity_home.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bccintern3.R
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeFragment(private var flManager: FragmentManager,
                   private var activity:AppCompatActivity,
                   private var thisContext: Context,
                   private var navbar:BottomNavigationView,
                   private var appContext: Context
                   ) :
    Fragment(R.layout.home_homefragment),
    BackHandler
{
    private lateinit var searchEt:TextInputEditText
    private var fragLayout:Int=0
    private lateinit var loadFrag:LoadFragment
    private lateinit var notFilled: HomeFragmentNotFilled
    private lateinit var dbRef:DbReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        runSearchListener()
        runFirstFragment()
    }

    fun runSearchListener(){
        searchEt.setText("")
        val ref1 = dbRef.refDesignerNode()
        val uid = ArrayList<String>()
        val nama = ArrayList<String>()
        val idKategori = ArrayList<String>()

        ref1.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ref1.removeEventListener(this)

                /**iterator kategori**/
                for(i:DataSnapshot in snapshot.children){

                    /**iterator desainer**/
                    for(j:DataSnapshot in i.children){
                        uid.add(j.child("uid").getValue().toString())
                        idKategori.add(j.child("id_kategori").getValue().toString())
                    }
                }

                val ref2 = dbRef.refUserNode()
                ref2.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ref2.removeEventListener(this)

                        var i=0
                        while(i<uid.size){
                            nama.add(
                                snapshot
                                    .child(uid.get(i))
                                    .child("profile")
                                    .child("name")
                                    .getValue().toString().lowercase()
                            )
                            i++
                        }

                        /**mulai mencari**/
                        searchEt.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                                if(searchEt.text.toString()==""){
//                                    loadFrag.transfer(flManager,fragLayout,notFilled)
//                                }
//                                else{
//                                    loadFrag.transfer(
//                                        flManager,
//                                        fragLayout,
//                                        HomeFragmentFilled(
//                                            flManager,
//                                            childFragmentManager,
//                                            activity,
//                                            searchEt,
//                                            thisContext,
//                                            navbar,
//                                            s.toString().lowercase(),
//                                            uid,
//                                            nama,
//                                            idKategori
//                                        )
//                                    )
//                                }
                            }
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                if(searchEt.text.toString()==""){
                                    loadFrag.transfer(flManager,fragLayout,notFilled)
                                }
                                else{
                                    loadFrag.transfer(
                                        flManager,
                                        fragLayout,
                                        HomeFragmentFilled(
                                            flManager,
                                            childFragmentManager,
                                            activity,
                                            searchEt,
                                            thisContext,
                                            navbar,
                                            s.toString().lowercase(),
                                            uid,
                                            nama,
                                            idKategori,
                                            appContext
                                        )
                                    )
                                }
                            }
                            override fun afterTextChanged(s: Editable?) {
                                if(searchEt.text.toString()==""){
                                    loadFrag.transfer(flManager,fragLayout,notFilled)
                                }
                                else{
                                    loadFrag.transfer(
                                        flManager,
                                        fragLayout,
                                        HomeFragmentFilled(
                                            flManager,
                                            childFragmentManager,
                                            activity,
                                            searchEt,
                                            thisContext,
                                            navbar,
                                            s.toString().lowercase(),
                                            uid,
                                            nama,
                                            idKategori,
                                            appContext
                                        )
                                    )
                                }
                            }
                        })
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
    fun runFirstFragment(){
        loadFrag.transfer(flManager,fragLayout,notFilled)
    }
    fun init(view:View){
        notFilled = HomeFragmentNotFilled(flManager,activity,thisContext,navbar,appContext)
        searchEt = view.findViewById(R.id.home_homefragment_search_et)
        fragLayout = R.id.home_homefragment_fl
        loadFrag = LoadFragment()
        dbRef = DbReference()
    }
}