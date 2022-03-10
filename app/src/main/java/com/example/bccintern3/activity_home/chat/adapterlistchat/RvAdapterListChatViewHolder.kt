package com.example.bccintern3.activity_home.chat.adapterlistchat

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.example.bccintern3.activity_chat.ChatActivity
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class RvAdapterListChatViewHolder(private var activity: AppCompatActivity,
                                  private var thisContext: Context,
                                  private var view:View
):RecyclerView.ViewHolder(view) {
    private var imageLawan:ImageView
    private var namaLawan:TextView
    private var clickArea:LinearLayout
    private var dbRef:DbReference
    private var loadAct:LoadActivity

    init {
        imageLawan = itemView.findViewById(R.id.chatfragment_listchat_border_image)
        namaLawan = itemView.findViewById(R.id.chatfragment_listchat_border_nama)
        clickArea = itemView.findViewById(R.id.chatfragment_listchat_border_clickarea)
        dbRef = DbReference()
        loadAct = LoadActivity()
    }
    fun bind(roomId:String,uidLawan:String){
        runClickListener(roomId)

        val ref = dbRef.refUidNode(uidLawan).child("profile")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)

                Picasso
                    .get()
                    .load(snapshot.child("pictureUrl").getValue().toString())
                    .transform(CircleTransform())
                    .into(imageLawan)

                namaLawan.setText(snapshot.child("name").getValue().toString())

                runClickListener(roomId)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun runClickListener(roomId: String){
        clickArea.setOnClickListener {
            loadAct.loadActivityCompleteWithExtras(
                thisContext,
                ChatActivity::class.java,
                activity,
                true,
                1000,
                "uid",
                roomId
            )
        }
    }
}