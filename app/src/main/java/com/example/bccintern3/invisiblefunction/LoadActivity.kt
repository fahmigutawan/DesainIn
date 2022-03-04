package com.example.bccintern3.invisiblefunction

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class LoadActivity {
    public fun loadActivity(thisContext: Context,cls:Class<*>){
        val intent = Intent(thisContext,cls)
        thisContext.startActivity(intent)
    }
    public fun loadActivityDisposable(thisContext: Context,cls: Class<*>,parentAppCompat:AppCompatActivity,isDispose:Boolean){
        val intent = Intent(thisContext, cls)
        thisContext.startActivity(intent)
        if(isDispose){
            parentAppCompat.finish()
        }
    }
    public fun loadActivityDelayable(thisContext: Context,cls: Class<*>,time:Long){
        val intent = Intent(thisContext,cls)
        Handler().postDelayed({
            thisContext.startActivity(intent)
        },time)
    }
    public fun loadActivityComplete(thisContext: Context,cls: Class<*>,parentAppCompat: AppCompatActivity,isDispose: Boolean,time: Long){
        val intent = Intent(thisContext,cls)
        Handler().postDelayed({
            thisContext.startActivity(intent)
            if(isDispose){
                parentAppCompat.finish()
            }
        },time)
    }
}