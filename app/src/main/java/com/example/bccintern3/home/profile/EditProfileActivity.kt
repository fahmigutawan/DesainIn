package com.example.bccintern2.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.example.bccintern3.invisiblefunction.DbReference
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditProfileActivity:AppCompatActivity() {
    private lateinit var changePictureIv:ImageView
    private lateinit var namaEditText:TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var teleponEditText:TextInputEditText
    private lateinit var savebtn:Button
    private lateinit var dbReference: DbReference
    private lateinit var fbAuth:FirebaseAuth
    private lateinit var imageUri:Uri
    private lateinit var storageRef:StorageReference
    private lateinit var credential:AuthCredential

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile_activity)

        /** DEKLARASI **/
        changePictureIv = findViewById(R.id.editprofile_picture_imageview)
        namaEditText = findViewById(R.id.editprofile_nama_textedit)
        emailEditText = findViewById(R.id.editprofile_emil_textedit)
        teleponEditText = findViewById(R.id.editprofile_telepon_textedit)
        savebtn = findViewById(R.id.editprofile_save_btn)
        dbReference = DbReference()
        fbAuth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance().getReference()

        /** Operasi pertama **/
        setPreviewData()

        /** upload foto profil **/
        changePictureIv.setOnClickListener {
            selectImage()
        }

        /** upload semua data **/
        savebtn.setOnClickListener {
            storeData()
        }
    }

    private fun setPreviewData(){
        val uid = fbAuth.currentUser?.uid.toString()
        val dbRef = dbReference.refUidNode(uid).child("profile")

        /** set profile picture **/
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /**profile picture**/
                Picasso
                    .get()
                    .load(snapshot.child("pictureUrl").getValue().toString())
                    .transform(CircleTransform())
                    .into(changePictureIv)

                /**nama, email, no telpon**/
                namaEditText.setText(snapshot.child("name").getValue().toString())
                emailEditText.setText(snapshot.child("email").getValue().toString())

                if(snapshot.child("phone").getValue().toString()=="null"){
                    teleponEditText.setText("belum ada")
                }else{
                    teleponEditText.setText(snapshot.child("phone").getValue().toString())
                }

                dbRef.removeEventListener(this)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun storeData(){
        val ref = dbReference.refUidNode(fbAuth.currentUser?.uid.toString()).child("profile")
        ref.child("name").setValue(namaEditText.text.toString().trim())
        ref.child("phone").setValue(teleponEditText.text.toString().trim())
    }
    private fun selectImage(){
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(intent,100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && data!=null &&data.getData()!=null && resultCode == RESULT_OK){
            imageUri = data.getData()!!
            uploadProfilePicture()
        }
    }
    private fun uploadProfilePicture() {
        val uid = fbAuth.currentUser?.uid.toString()

        val mountainImagesRef = storageRef.child("user_profile_picture/"+uid)

        mountainImagesRef.putFile(imageUri).addOnSuccessListener {
            updatePictureUrlInDb()
        }
    }
    private fun updatePictureUrlInDb(){
        val uid = fbAuth.currentUser?.uid.toString()
        val ref = storageRef.child("user_profile_picture/"+uid)

        ref.getDownloadUrl().addOnSuccessListener {
            /** proses memasukkan url foto terbaru **/
            val dbRef = dbReference.refUidNode(uid).child("profile").child("pictureUrl")
            dbRef.setValue(it.toString()).addOnSuccessListener {
                setPreviewData()
            }
        }


    }
}