package com.example.bccintern3.activity_home.discovery.adapterlistartikel

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
import com.example.bccintern3.activity_home.HomeActivity
import com.example.bccintern3.activity_home.discovery.DiscoveryFragmentDetailArtikel
import com.example.bccintern3.nonactivity_invisiblefunction.LoadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class RvAdapterListArtikelViewHolder(inflater: LayoutInflater,
                                     parent: ViewGroup,
                                     parentView: View,
                                     mainFlManager: FragmentManager,
                                     private var navbar:BottomNavigationView,
                                     private var activity:AppCompatActivity,
                                     private val appContext: Context,
                                     private var parentHome: HomeActivity
                                     )
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.home_discoveryfragment_listartikel_borderitem,parent,false))
{
    private var parentView = parentView
    private var mainFlManager = mainFlManager
    private var clickArea:LinearLayout
    private var imageView: ImageView
    private var title:TextView
    private var tanggal:TextView
    private lateinit var hari:String
    private lateinit var bulan:String
    private lateinit var tahun: String
    private lateinit var id: String
    private var loadFrag:LoadFragment

    init {
        clickArea = itemView.findViewById(R.id.discoveryfragment_listartikel_border_clickarea)
        imageView = itemView.findViewById(R.id.discoveryfragment_listartikel_border_imageiv)
        title = itemView.findViewById(R.id.discoveryfragment_listartikel_border_titletv)
        tanggal = itemView.findViewById(R.id.discoveryfragment_listartikel_border_tanggaltv)
        loadFrag = LoadFragment()
        runClickListener()
    }

    fun bind(url:String,
             judul:String,
             tanggal:String,
             hari:String,
             bulan:String,
             tahun:String,
             id:String)
    {
        this.hari=hari
        this.bulan=bulan
        this.tahun=tahun
        this.id=id

        val width=parentView.width
        val height=width/3

        /**set gambar**/
        Picasso
            .get()
            .load(url)
            .transform(RoundCornerRect(30f,0f,0f,0f,0f))
            .resize(width,height)
            .centerCrop()
            .into(imageView)

        /**set judul**/
        title.setText(judul)

        /**set tanggal**/
        this.tanggal.setText(tanggal)
    }
    fun runClickListener(){
        clickArea.setOnClickListener {
            Handler().postDelayed({
                loadFrag.transfer(
                    mainFlManager,
                    R.id.homeactivity_flmanager,
                    DiscoveryFragmentDetailArtikel(hari,bulan, tahun, id,mainFlManager,navbar,activity,appContext,parentHome)
                )
            },1000)
        }
    }
}