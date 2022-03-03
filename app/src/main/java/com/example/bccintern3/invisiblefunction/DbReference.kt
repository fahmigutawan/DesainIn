package com.example.bccintern3.invisiblefunction

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DbReference {
    private val database= FirebaseDatabase.getInstance()
    fun refCategoryPicture():DatabaseReference{
        return FirebaseDatabase.getInstance().getReference("category_picture")
    }
    fun refUidNode(uid:String): DatabaseReference {
        return database.getReference("users").child(uid)
    }
    fun refUserNode():DatabaseReference{
        return database.getReference("users")
    }
    fun refHomeBanner():DatabaseReference{
        return database.getReference("banner")
    }
    fun refDesignerNode():DatabaseReference{
        return database.getReference("designer")
    }
}