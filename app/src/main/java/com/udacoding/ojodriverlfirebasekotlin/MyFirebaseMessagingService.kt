package com.udacoding.ojodriverlfirebasekotlin

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.udacoding.ojodriverlfirebasekotlin.detailrequest.DetailRequest
import com.udacoding.ojodriverlfirebasekotlin.utama.HomeActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // ...




       showNotification()

    }


    private fun showNotification() {

        var status =0

        if(status == 0) {

             status!! + 1

            val notificationBuilder = NotificationCompat.Builder(this)
            val intent: Intent
            intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notificationBuilder
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("New Order,yuk Ambil")
                .setContentText("Silahkan check list")
                .setAutoCancel(true)

                .setSound(defaultSoundUri)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(0, notificationBuilder.build())

        }
    }
}
