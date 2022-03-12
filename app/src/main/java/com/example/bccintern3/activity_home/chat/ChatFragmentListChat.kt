package com.example.bccintern3.activity_home.chat

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.chat.adapterlistchat.RvAdapterListChat
import com.example.bccintern3.nonactivity_invisiblefunction.BackHandler
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ChatFragmentListChat(private var thisContext:Context,
                           private var activity:AppCompatActivity,
                           private var navbar:BottomNavigationView
                           )
    :Fragment(R.layout.home_chatfragment_listchat)
{
    private lateinit var listChatRv:RecyclerView
    private lateinit var listChatAdapter:RvAdapterListChat
    private lateinit var dbRef:DbReference
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var roomId:ArrayList<String>
    private lateinit var uidLawan:ArrayList<String>
    private var time:Long=0

    fun init(view:View){
        listChatRv = view.findViewById(R.id.chatfragment_listchat_rv)
        listChatRv.layoutManager = LinearLayoutManager(thisContext)

        dbRef = DbReference()
        fbAuth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        loadData()
        runDataUpdater()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }
    fun loadData(){
        val uid = fbAuth.currentUser?.uid.toString()
        val ref = dbRef.refUidNode(uid).child("roomChatId")
        roomId = ArrayList()
        uidLawan = ArrayList()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)

                for(item:DataSnapshot in snapshot.children){
                    if(item.child("count").getValue().toString() != "0"){
                        roomId.add(item.child("roomId").getValue().toString())

                        if(item.child("user1").getValue().toString() == uid){
                            uidLawan.add(item.child("user2").getValue().toString())
                        }
                        else{
                            uidLawan.add(item.child("user1").getValue().toString())
                        }
                    }
                }

                listChatAdapter = RvAdapterListChat(roomId, uidLawan, activity, thisContext)
                listChatRv.adapter = listChatAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun runDataUpdater(){
        val ref = dbRef.refUidNode(fbAuth.currentUser?.uid.toString()).child("roomChatId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if (
                    snapshot.child("roomId").getValue().toString()!="null" &&
                    snapshot.child("user1").getValue().toString()!="null" &&
                    snapshot.child("user2").getValue().toString()!="null"
                )
                {
                    if(snapshot.child("count").getValue().toString()!="0"){
                        roomId.add(snapshot.child("roomId").getValue().toString())

                        if(snapshot.child("user1").getValue().toString() == fbAuth.currentUser?.uid.toString()){
                            uidLawan.add(snapshot.child("user2").getValue().toString())
                        }
                        else{
                            uidLawan.add(snapshot.child("user1").getValue().toString())
                        }
                        listChatAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler
                val toast = Toast.makeText(thisContext,"Tekan kembali lagi untuk keluar", Toast.LENGTH_SHORT)

                if(time+1500>System.currentTimeMillis()){
                    toast.cancel()
                    handler.exit(activity)
                }
                else{
                    toast.show()
                }
                time = System.currentTimeMillis()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
}