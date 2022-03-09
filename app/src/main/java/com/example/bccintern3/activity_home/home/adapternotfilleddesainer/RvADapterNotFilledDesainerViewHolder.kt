package com.example.bccintern3.activity_home.home.adapternotfilleddesainer

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bccintern2.picasso.RoundCornerRect
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentDetailDesainer
import com.example.bccintern3.nonactivity_invisiblefunction.DbReference
import com.example.bccintern3.nonactivity_invisiblefunction.LoadActivity
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class RvADapterNotFilledDesainerViewHolder(inflater: LayoutInflater,
                                           parent: ViewGroup,
                                           private var parentView: View,
                                           private var mainFlManager: FragmentManager,
                                           private var navbar: BottomNavigationView,
                                           private var activity: AppCompatActivity,
                                           private var designerList:ArrayList<String>,
                                           private var idDesignerKategori:ArrayList<String>,
                                           private var thisContext:Context,
                                           private var appContext: Context
                                           )
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.home_homefragment_notfill_desainer_border,parent,false))
{
    private var loadAct:LoadActivity
    private var loadFrag:LoadFragment
    private var dbRef:DbReference
    private var imageView:ImageView
    private var name:TextView
    private var clickableArea:LinearLayout

    init{
        loadAct = LoadActivity()
        loadFrag = LoadFragment()
        dbRef = DbReference()
        imageView = itemView.findViewById(R.id.homefragment_notfill_desainerborder_iv)
        name = itemView.findViewById(R.id.homefragment_notfill_desainerborder_tv)
        clickableArea = itemView.findViewById(R.id.homefragment_notfill_desainerborder_layout)
    }
    fun bind(id:Int){
        val imgSize = (parentView.width/4.2).toInt()
        val uid = designerList.get(id)
        val idKategori = idDesignerKategori.get(id)
        val ref = dbRef.refDesignerNode().child(idKategori).child(uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)

                /**set gambar**/
                Picasso
                    .get()
                    .load(snapshot.child("icon").getValue().toString())
                    .transform(RoundCornerRect(20f,0f,0f,0f,0f))
                    .resize(imgSize,imgSize)
                    .centerCrop()
                    .into(imageView)

                val ref2 = dbRef.refUidNode(uid).child("profile")
                ref2.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ref2.removeEventListener(this)

                        name.setText(snapshot.child("name").getValue().toString())
                        name.setWidth(imgSize)
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

        clickableArea.setOnClickListener {
            Handler().postDelayed({
                navbar.menu.getItem(0).setChecked(false)
                navbar.menu.getItem(1).setChecked(true)
                loadFrag.transfer(
                    mainFlManager,
                    R.id.homeactivity_flmanager,
                    DiscoveryFragmentDetailDesainer(
                        mainFlManager,
                        thisContext,
                        navbar,
                        activity,
                        uid,
                        idKategori,appContext))
            },1500)        }
    }
}