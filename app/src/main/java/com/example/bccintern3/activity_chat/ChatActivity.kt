package com.example.bccintern3.activity_chat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.example.bccintern3.activity_chat.adapterchat.RvAdapterChat
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.FcmNotificationsSender
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ChatActivity:AppCompatActivity(R.layout.chatroom_activity) {
    private lateinit var imageProfile:ImageView
    private lateinit var nameTv:TextView
    private lateinit var chatRv:RecyclerView
    private lateinit var chatInput:TextView
    private lateinit var sendBtn:Button
    private lateinit var loadAct:LoadActivity
    private lateinit var dbRef:DbReference
    private lateinit var roomId:String
    private lateinit var uidSendiri:String
    private lateinit var uidLawan:String
    private lateinit var tokenLawan:String
    private lateinit var chatAdapter:RvAdapterChat
    private lateinit var linearLayoutManager: LinearLayoutManager
    var message = ArrayList<String>()
    var sender = ArrayList<String>()

    fun init(){
        imageProfile = findViewById(R.id.chatroom_activity_profile_image)
        nameTv = findViewById(R.id.chatroom_activity_nameTv)
        chatRv = findViewById(R.id.chatroom_activity_chatrv)
        chatInput = findViewById(R.id.chatroom_activity_chatinputEt)
        sendBtn = findViewById(R.id.chatroom_activity_sendBtn)
        loadAct = LoadActivity()
        dbRef = DbReference()

        chatRv.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        chatRv.layoutManager = linearLayoutManager
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        init()
        getDataFromIntent(intent)
        loadDataPreview()
        runClickListener()
        loadLastChat()
        childDbListener()
    }
    fun getDataFromIntent(intent: Intent){
        roomId = intent.extras?.getString("uid").toString()
    }
    fun loadDataPreview(){
        chatInput.isVisible = false
        sendBtn.isVisible = false

        uidSendiri=FirebaseAuth.getInstance().currentUser?.uid.toString()
        uidLawan=""

        val refIdentity = dbRef.refChatRoomNode().child(roomId).child("identity")
        refIdentity.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                refIdentity.removeEventListener(this)

                if(uidSendiri == snapshot.child("user1").getValue().toString()){
                    uidLawan = snapshot.child("user2").getValue().toString()
                }
                else if(uidSendiri == snapshot.child("user2").getValue().toString()){
                    uidLawan = snapshot.child("user1").getValue().toString()
                }
                Log.e("UID LAWAN","WOI"+uidLawan)

                val refPreview = dbRef.refUidNode(uidLawan).child("profile")
                refPreview.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        refPreview.removeEventListener(this)

                        /**set gambar**/
                        Picasso
                            .get()
                            .load(snapshot.child("pictureUrl").getValue().toString())
                            .transform(CircleTransform())
                            .resize(45,45)
                            .centerCrop()
                            .into(imageProfile)

                        /**load nama**/
                        nameTv.setText(snapshot.child("name").getValue().toString())

                        refPreview.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                /**memasukkan token lawan**/
                                tokenLawan = snapshot.child("fcmToken").getValue().toString()
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                        chatInput.isVisible = true
                        sendBtn.isVisible = true
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
    fun sendMessage(s:String){
        val ref = dbRef.refChatRoomNode().child(roomId).child("chat")
        val refCount = dbRef.refChatRoomNode().child(roomId).child("identity")
        val refSendiri = dbRef.refUidNode(uidSendiri).child("roomChatId")
        val refLawan = dbRef.refUidNode(uidLawan).child("roomChatId")
        val randKey = ref.push().key

        refCount.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                refCount.removeEventListener(this)

                var count:Int = Integer.parseInt(snapshot.child("count").getValue().toString())
                var fixedChatName = count.toString()+"_"+randKey

                ref.child(fixedChatName).child("sender").setValue(uidSendiri).addOnSuccessListener {
                    ref.child(fixedChatName).child("receiver").setValue(uidLawan).addOnSuccessListener {
                        ref.child(fixedChatName).child("message").setValue(s).addOnSuccessListener {

                            refCount.child("count").setValue((++count).toString())
                            refSendiri.child(roomId).child("count").setValue(count.toString())
                            refLawan.child(roomId).child("count").setValue(count.toString())

                            /**Notifikasi**/
                            FcmNotificationsSender(
                                tokenLawan,
                                "Pesan baru",
                                chatInput.text.toString(),
                                applicationContext,
                                this@ChatActivity,
                                roomId
                            ).SendNotifications()

                            chatInput.setText("")
                        }
                    }
                }


            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun runClickListener(){
        sendBtn.setOnClickListener {
            if(chatInput.text.isEmpty()){

            }
            else{
                sendMessage(chatInput.text.toString())
                //loadLastChat()
            }
        }
    }
    fun loadLastChat(){
        val ref = dbRef.refChatRoomNode().child(roomId).child("chat")

        /**chat loader**/
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)

                message.clear()
                sender.clear()

                for(item:DataSnapshot in snapshot.children){
                    message.add(item.child("message").getValue().toString())
                    sender.add(item.child("sender").getValue().toString())
                }

                chatAdapter = RvAdapterChat(
                    this@ChatActivity,
                    this@ChatActivity,
                    message,
                    sender
                    )

                chatRv.adapter = chatAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun childDbListener(){
        val ref = dbRef.refChatRoomNode().child(roomId).child("chat")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.child("message").getValue().toString() != "null"){
                    updateChat(
                        snapshot.child("message").getValue().toString(),
                        snapshot.child("sender").getValue().toString()
                    )
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
    fun updateChat(message:String,sender:String){
        this.message.add(message)
        this.sender.add(sender)

        chatAdapter.notifyDataSetChanged()
        chatRv.scrollToPosition(this.message.size-1)
    }
    override fun onBackPressed() {
        val intent = Intent(this,HomeActivity::class.java)
        val bundle = Bundle()
        bundle.putString(getString(R.string.last_frag),getString(R.string.chat_fragment))
        intent.putExtras(bundle)

        Handler().postDelayed({
            startActivity(intent)
            this.finish()
        },1000)
    }
}