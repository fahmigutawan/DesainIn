package com.example.bccintern3.activity_chat.adapterchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class RvAdapterChatViewHolder(private var view:View,
                              private var thisContext: Context,
                              private var activiy:AppCompatActivity,
                              private var viewType:Int
                              )
    :
    RecyclerView.ViewHolder(view)
{
    private var SENDED_BY_KITA=1
    private var SENDED_BY_LAWAN=0
    private lateinit var dbRef:DbReference
    private lateinit var fotoKita:ImageView
    private lateinit var messageKita:TextView
    private lateinit var fotoLawan:ImageView
    private lateinit var messageLawan:TextView

    init {
        if(viewType==SENDED_BY_KITA){
            fotoKita = itemView.findViewById(R.id.chatroom_activity_border_user_foto)
            messageKita = itemView.findViewById(R.id.chatroom_activity_border_user_message)
        }
        else{
            fotoLawan = itemView.findViewById(R.id.chatroom_activity_border_lawan_foto)
            messageLawan = itemView.findViewById(R.id.chatroom_activity_border_lawan_message)
        }
        dbRef = DbReference()
    }

    fun bind(message:String,senderUid:String){
        if(viewType == SENDED_BY_KITA){
            /**set gambar**/
            val ref = dbRef.refUidNode(senderUid).child("profile")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ref.removeEventListener(this)

                    Picasso
                        .get()
                        .load(snapshot.child("pictureUrl").getValue().toString())
                        .transform(CircleTransform())
                        .into(fotoKita)

                    messageKita.setText(message)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        else{
            /**set gambar**/
            val ref = dbRef.refUidNode(senderUid).child("profile")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ref.removeEventListener(this)

                    Picasso
                        .get()
                        .load(snapshot.child("pictureUrl").getValue().toString())
                        .transform(CircleTransform())
                        .into(fotoLawan)

                    messageLawan.setText(message)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}