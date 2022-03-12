package com.example.bccintern3.nonactivity_invisiblefunction

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.bccintern3.R
import com.example.bccintern3.activity_chat.ChatActivity
import com.example.bccintern3.activity_home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FbMessagingService:FirebaseMessagingService() {
    lateinit var mNotificationManager:NotificationManager
    var fbAuth = FirebaseAuth.getInstance()

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        /**ringtone**/
        val notifRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(applicationContext,notifRingtoneUri)
        ringtone.play()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            ringtone.isLooping = false
        }
        /**vibration**/
        val vibrator:Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val patern = longArrayOf(100,300,300,300)
        vibrator.vibrate(patern,-1)

        val builder = NotificationCompat.Builder(this,"CHANNEL_ID")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder.setSmallIcon(R.drawable.icon_home_selected)
        }
        else{
            builder.setSmallIcon(R.drawable.icon_home_selected)
        }

        val resultIntent = Intent(this,ChatActivity::class.java)
        resultIntent.putExtra("uid",p0.data.get("uid"))
        val pendingIntent = PendingIntent.getActivity(this,0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        builder
            .setSmallIcon(R.drawable.logo_notif)
            .setContentTitle(p0.data.get("title"))
            .setContentText(p0.data.get("body"))
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(p0.notification?.channelId))
            .setAutoCancel(true)
            .setPriority(Notification.PRIORITY_MAX)

        mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channerlId = "WHATS APPP COBA"
            val channel = NotificationChannel(channerlId,"TITLE CHANNEL",NotificationManager.IMPORTANCE_HIGH)
            mNotificationManager.createNotificationChannel(channel)
            builder.setChannelId(channerlId)
        }

        mNotificationManager.notify(100,builder.build())
    }
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val auth = FirebaseAuth.getInstance()
        val ref = DbReference().refUidNode(auth.currentUser?.uid.toString())

        if(fbAuth.currentUser!=null){
            ref
                .child("profile")
                .child("fcmToken")
                .setValue(p0)
        }
    }

}