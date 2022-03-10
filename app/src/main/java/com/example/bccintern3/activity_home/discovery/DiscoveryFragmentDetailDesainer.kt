package com.example.bccintern3.activity_home.discovery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.example.bccintern3.activity_chat.ChatActivity
import com.example.bccintern3.activity_home.discovery.vp_adapterdetaildesainer.VpAdapterDetailDesainer
import com.example.bccintern3.activity_login.LoginActivity
import com.example.bccintern3.dummyactivity_payment.DummyPaymentActivity
import com.example.bccintern3.nonactivity_invisiblefunction.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso

class DiscoveryFragmentDetailDesainer(flManager:FragmentManager,
                                      thisContext: Context,
                                      navbar: BottomNavigationView,
                                      activity:AppCompatActivity,
                                      uidSelected:String,
                                      idKategoriSelected:String,
                                      appContext: Context
):Fragment(R.layout.home_discoveryfragment_desainer_detail) {
    private lateinit var bannerVp:ViewPager2
    private lateinit var nameTv:TextView
    private lateinit var kategoriTv:TextView
    private lateinit var deskripsiTv:TextView
    private lateinit var lokasiTv:TextView
    private lateinit var hargaTv:TextView
    private lateinit var chatBtn:Button
    private lateinit var orderBtn:Button
    private lateinit var picPicture:ImageView
    private lateinit var widthParam:LinearLayout
    private lateinit var loadFrag:LoadFragment
    private lateinit var loadAct:LoadActivity
    private lateinit var dbRef:DbReference
    private lateinit var vpAdapter:VpAdapterDetailDesainer
    private lateinit var fbAuth:FirebaseAuth

    private val flManager:FragmentManager
    private val thisContext: Context
    private val navbar: BottomNavigationView
    private val activity:AppCompatActivity
    private val uidSelected:String
    private val idKategoriSelected:String
    private val appContext: Context

    init {
        this.flManager = flManager
        this.thisContext = thisContext
        this.navbar = navbar
        this.activity = activity
        this.uidSelected = uidSelected
        this.idKategoriSelected = idKategoriSelected
        this.appContext = appContext
    }

    fun init(view: View){
        bannerVp = view.findViewById(R.id.discoveryfragment_desainerdetail_banner_vp)
        nameTv = view.findViewById(R.id.discoveryfragment_desainerdetail_nama_tv)
        kategoriTv = view.findViewById(R.id.discoveryfragment_desainerdetail_kategori_tv)
        deskripsiTv = view.findViewById(R.id.discoveryfragment_desainerdetail_deskripsi_tv)
        lokasiTv = view.findViewById(R.id.discoveryfragment_desainerdetail_lokasi_tv)
        hargaTv = view.findViewById(R.id.discoveryfragment_desainerdetail_harga_tv)
        chatBtn = view.findViewById(R.id.discoveryfragment_desainerdetail_chat_btn)
        orderBtn = view.findViewById(R.id.discoveryfragment_desainerdetail_order_btn)
        picPicture = view.findViewById(R.id.discoveryfragment_desainerdetail_profil_iv)
        widthParam = view.findViewById(R.id.discoveryfragment_desainerdetail_widthparam_layout)
        loadFrag = LoadFragment()
        loadAct = LoadActivity()
        dbRef = DbReference()
        fbAuth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        init(view)
        setVpBanner(view)
        setDataPreview()
        runClickListener()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        runBackHandler(context)
    }
    private fun runBackHandler(context: Context){
        val back = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val handler = context as BackHandler

                handler.loadFragment(flManager,R.id.homeactivity_flmanager, DiscoveryFragmentDefault(flManager,thisContext, navbar, activity,appContext))
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,back)
    }
    fun setVpBanner(view:View){
        val ref = dbRef.refDesignerNode().child(idKategoriSelected).child(uidSelected)

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ref.removeEventListener(this)
                        val banner = ArrayList<String>()

                        banner.add(snapshot.child("banner1").getValue().toString())
                        banner.add(snapshot.child("banner2").getValue().toString())
                        banner.add(snapshot.child("banner3").getValue().toString())

                        vpAdapter = VpAdapterDetailDesainer(banner,view)
                        bannerVp.adapter = vpAdapter
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

        })
    }
    fun setDataPreview(){
        val ref = dbRef.refDesignerNode().child(idKategoriSelected).child(uidSelected)
        val ref2 = dbRef.refUidNode(uidSelected).child("profile")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ref.removeEventListener(this)

                kategoriTv.setText(snapshot.child("kategori").getValue().toString())
                deskripsiTv.setText(snapshot.child("deskripsi").getValue().toString())
                lokasiTv.setText(snapshot.child("lokasi").getValue().toString())
                hargaTv.setText(snapshot.child("start harga").getValue().toString())

                ref2.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ref2.removeEventListener(this)

                        /**set nama**/
                        nameTv.setText(snapshot.child("name").getValue().toString())

                        /**set gambar**/
                        Picasso
                            .get()
                            .load(snapshot.child("pictureUrl").getValue().toString())
                            .transform(CircleTransform())
                            .into(picPicture)
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
    fun runClickListener(){
        chatBtn.setOnClickListener {
            if(fbAuth.currentUser?.uid!=null){
                val ref = dbRef.refChatRoomNode()

                val tmp1 = fbAuth.currentUser?.uid.toString() + uidSelected
                val tmp2 = uidSelected + fbAuth.currentUser?.uid.toString()

                val refUser1 = dbRef.refUidNode(fbAuth.currentUser?.uid.toString()).child("roomChatId")
                val refUser2 = dbRef.refUidNode(uidSelected).child("roomChatId")

                if(fbAuth.currentUser?.uid.toString() == uidSelected){
                    Toast.makeText(thisContext,"Tidak bisa chat dengan diri sendiri",Toast.LENGTH_SHORT).show()
                }
                else{
                    refUser1.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            refUser1.removeEventListener(this)
                            if(!snapshot.hasChild(tmp1) && !snapshot.hasChild(tmp2)){
                                /**hanya dicek pada user1. Karena jika user1 tidak punya, pasti user2 tidak punya**/
                                refUser1.child(tmp1).child("roomId").setValue(tmp1)
                                refUser1.child(tmp1).child("user1").setValue(uidSelected)
                                refUser1.child(tmp1).child("user2").setValue(fbAuth.currentUser?.uid.toString())
                                refUser1.child(tmp1).child("count").setValue("0")

                                refUser2.child(tmp1).child("roomId").setValue(tmp1)
                                refUser2.child(tmp1).child("user1").setValue(uidSelected)
                                refUser2.child(tmp1).child("user2").setValue(fbAuth.currentUser?.uid.toString())
                                refUser2.child(tmp1).child("count").setValue("0")

                                ref.child(tmp1).child("identity").child("count").setValue("0")
                                ref.child(tmp1).child("identity").child("chatId").setValue(tmp1)
                                ref.child(tmp1).child("identity").child("user1").setValue(fbAuth.currentUser?.uid.toString())
                                ref.child(tmp1).child("identity").child("user2").setValue(uidSelected)


                                loadAct.loadActivityCompleteWithExtras(
                                    thisContext,
                                    ChatActivity::class.java,
                                    activity,
                                    true,
                                    1000,
                                    "uid",
                                    tmp1)
                            }
                            else if(snapshot.hasChild(tmp1)){
                                loadAct.loadActivityCompleteWithExtras(
                                    thisContext,
                                    ChatActivity::class.java,
                                    activity,
                                    true,
                                    1000,
                                    "uid",
                                    tmp1)
                            }
                            else if(snapshot.hasChild(tmp2)){
                                loadAct.loadActivityCompleteWithExtras(
                                    thisContext,
                                    ChatActivity::class.java,
                                    activity,
                                    true,
                                    1000,
                                    "uid",
                                    tmp2)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }
            else{
                Toast.makeText(thisContext,"Harap login untuk mengirim pesan",Toast.LENGTH_SHORT).show()
                loadAct.loadActivityDelayable(thisContext,LoginActivity::class.java,1000)
            }
        }
        orderBtn.setOnClickListener {
            if(fbAuth.currentUser?.uid != null){
                if(fbAuth.currentUser?.uid.toString() != uidSelected){
                    var bundle = Bundle()
                    bundle.putString("uid",uidSelected)
                    loadAct.loadActivityCompleteWithBundle(thisContext,DummyPaymentActivity::class.java,activity,false,1000,bundle)
                }
                else{
                    Toast.makeText(appContext,"Tidak bisa order dengan diri sendiri",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(thisContext,"Harap login untuk order jasa desainer",Toast.LENGTH_SHORT).show()
                loadAct.loadActivityDelayable(thisContext,LoginActivity::class.java,1000)
            }

        }
    }
}