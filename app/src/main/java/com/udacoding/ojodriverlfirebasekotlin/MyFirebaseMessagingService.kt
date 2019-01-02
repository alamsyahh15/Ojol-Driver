package com.udacoding.ojodriverlfirebasekotlin

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.udacoding.ojodriverlfirebasekotlin.detailrequest.DetailRequest

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // ...



//
//        Log.d("notif","hei :"+remoteMessage?.data?.get("message"))
//        Log.d("notif","hei :"+remoteMessage?.data?.get("data"))
//        //notification
//        Log.d("notif","hei :"+remoteMessage?.data?.get("notification"))
//        Log.d("notif","hei :"+remoteMessage?.from)

       showNotification("")

    }


    private fun showNotification(msg: String) {
        val notificationBuilder = NotificationCompat.Builder(this)
        val intent: Intent
        intent = Intent(this, DetailRequest::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        notificationBuilder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("hei")
            .setContentText(msg)
            .setAutoCancel(true)
            .setLights(Color.BLUE, 1000, 1000)
            .setSound(defaultSoundUri)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }
}
