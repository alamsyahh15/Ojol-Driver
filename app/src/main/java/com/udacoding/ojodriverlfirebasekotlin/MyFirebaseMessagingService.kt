package com.udacoding.ojodriverlfirebasekotlin

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.udacoding.ojodriverlfirebasekotlin.detailrequest.DetailRequest
import com.udacoding.ojodriverlfirebasekotlin.utama.HomeActivity
import org.jetbrains.anko.startActivity

@Suppress("DEPRECATION")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        // ...


       showNotification()

    }


    @SuppressLint("WrongConstant")
    private fun showNotification() {


            val notificationBuilder = NotificationCompat.Builder(this)
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pageIntent = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notificationBuilder
                .setSmallIcon(R.drawable.logo)
                .setBadgeIconType(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.logo))
                .setContentIntent(pageIntent)
                .setContentTitle("New Order,yuk Ambil")
                .setContentText("Silahkan check list")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(0, notificationBuilder.build())


    }
}
