package com.example.bccintern3.activity_chat.adapterchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class RvAdapterChat(
                    private var thisContext: Context,
                    private var activiy: AppCompatActivity,
                    private var message:ArrayList<String>,
                    private var sender:ArrayList<String>
                    )
    :RecyclerView.Adapter<RvAdapterChatViewHolder>()
{
    private var fbAuth = FirebaseAuth.getInstance()
    private var SENDED_BY_KITA=1
    private var SENDED_BY_LAWAN=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterChatViewHolder {
        if(viewType == SENDED_BY_KITA){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.chatroom_activity_user_border,parent,false)
            return RvAdapterChatViewHolder(view,thisContext, activiy,SENDED_BY_KITA)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.chatroom_activity_lawan_border,parent,false)
            return RvAdapterChatViewHolder(view,thisContext, activiy,SENDED_BY_LAWAN)
        }
    }

    override fun onBindViewHolder(holder: RvAdapterChatViewHolder, position: Int) {
        holder.bind(message.get(position),sender.get(position))
    }

    override fun getItemCount(): Int {
        return message.size
    }

    override fun getItemViewType(position: Int): Int {
        if(sender.get(position) == fbAuth.currentUser?.uid.toString()){
            return SENDED_BY_KITA
        }
        else{
            return SENDED_BY_LAWAN
        }
    }
}