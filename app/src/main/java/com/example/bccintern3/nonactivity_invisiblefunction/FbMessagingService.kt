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
import androidx.core.app.NotificationCompat
import com.example.bccintern3.R
import com.example.bccintern3.activity_home.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FbMessagingService:FirebaseMessagingService() {
    lateinit var mNotificationManager:NotificationManager

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        /**ringtone**/
        val notifRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(applicationContext,notifRingtoneUri)
        ringtone.play()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            ringtone.isLooping = false
        }

        val resourceImage = getResources().getIdentifier(p0.getNotification()?.getIcon(), "drawable", getPackageName());

        /**vibration**/
        val vibrator:Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val patern = longArrayOf(100,300,300,300)
        vibrator.vibrate(patern,-1)

        val builder = NotificationCompat.Builder(this,"CHANNEL_ID")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder.setSmallIcon(R.drawable.icon_home_selected)
            builder.setSmallIcon(resourceImage)
        }
        else{
            builder.setSmallIcon(R.drawable.icon_home_selected)
            builder.setSmallIcon(resourceImage)
        }

        val resultIntent = Intent(this,HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivities(this,1, arrayOf(resultIntent),PendingIntent.FLAG_UPDATE_CURRENT)
        builder
            .setContentTitle(p0.notification?.title)
            .setContentText(p0.notification?.body)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(p0.notification?.body))
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

}