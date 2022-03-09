package com.example.bccintern3.activity_home.home.adapternotfilledartikel

import android.content.Context
import android.os.Handler
import android.util.Log
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
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentDetailArtikel
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class RvAdapterNotFilledArtikelViewHolder(inflater: LayoutInflater,
                                          parent: ViewGroup,
                                          parentView: View,
                                          mainFlManager: FragmentManager,
                                          private var navbar: BottomNavigationView,
                                          private var activity: AppCompatActivity,
                                          private var appContext: Context
)
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_homefragment_notfill_artikel_border,parent,false))
{
    private var parentView = parentView
    private var mainFlManager = mainFlManager
    private var clickArea:LinearLayout
    private var imageView:ImageView
    private var titleTv:TextView
    private var loadFrag:LoadFragment
    private var hari:String=""
    private var bulan:String=""
    private var tahun:String=""
    private var id:String=""

    init {
        clickArea = itemView.findViewById(R.id.homefragment_notfill_artikelborder_clickarea)
        imageView = itemView.findViewById(R.id.homefragment_notfill_artikelborder_iv)
        titleTv = itemView.findViewById(R.id.homefragment_notfill_artikeltitleborder_tv)
        loadFrag = LoadFragment()
        runClickListener()
    }
    fun bind(
        url:String,
        title:String,
        hari:String,
        bulan:String,
        tahun:String,
        id:String)
    {
        this.hari=hari
        this.bulan=bulan
        this.tahun=tahun
        this.id=id

        val width = (parentView.width/2.5).toInt()
        val height = width/2
        /**set gambar**/
        Picasso
            .get()
            .load(url)
            .transform(RoundCornerRect(30f,0f,0f,0f,0f))
            .resize(width,height)
            .centerCrop()
            .into(imageView)

        /**set title**/
        titleTv.setText(title)
    }
    fun runClickListener(){
        clickArea.setOnClickListener {
            Log.e("HARI",hari)
            Log.e("BULAN",bulan)
            Log.e("ID",id)
            if(hari!="" && bulan!="" && tahun!="" && id!=""){
                Handler().postDelayed({
                    navbar.menu.getItem(0).setChecked(false)
                    navbar.menu.getItem(1).setChecked(true)
                    loadFrag.transfer(
                        mainFlManager,
                        R.id.homeactivity_flmanager,
                        DiscoveryFragmentDetailArtikel(hari,bulan, tahun, id,mainFlManager,navbar,activity,appContext)
                    )
                },1000)
            }
        }
    }
}