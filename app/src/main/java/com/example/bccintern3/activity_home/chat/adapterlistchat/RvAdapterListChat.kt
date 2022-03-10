package com.example.bccintern3.activity_home.chat.adapterlistchat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern3.R

class RvAdapterListChat(private var roomId:ArrayList<String>,
                        private var uidLawan:ArrayList<String>,
                        private var activity:AppCompatActivity,
                        private var thisContext:Context,
                        ):RecyclerView.Adapter<RvAdapterListChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterListChatViewHolder {
        var view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.home_chatfragment_listchat_border,parent,false)

        return RvAdapterListChatViewHolder(activity, thisContext, view)
    }

    override fun onBindViewHolder(holder: RvAdapterListChatViewHolder, position: Int) {
        holder.bind(
            roomId.get(position),
            uidLawan.get(position)
        )
    }

    override fun getItemCount(): Int {
        return roomId.size
    }
}