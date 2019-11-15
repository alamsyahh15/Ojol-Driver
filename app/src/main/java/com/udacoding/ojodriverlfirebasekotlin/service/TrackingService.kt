package com.udacoding.ojodriverlfirebasekotlin.service

import com.google.firebase.auth.FirebaseAuth;


import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.os.IBinder;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.app.Service;
import android.location.Location
import android.util.Log
import android.util.Log.d
import com.google.android.gms.location.*
import com.google.firebase.database.*
import com.udacoding.ojodriverlfirebasekotlin.utils.Constan
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest



/**
 * Created by nandoseptianhusni on 21/08/18.
 */
class TrackingService : Service() {


    private var mAuth: FirebaseAuth? = null
    protected var stopReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            //Unregister the BroadcastReceiver when the notification is tapped//

            unregisterReceiver(this)

            //Stop the Service//

            stopSelf()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        mAuth = FirebaseAuth.getInstance()


        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {

        val ref = FirebaseDatabase.getInstance().getReference(Constan.tb_Uaser)

        val query = ref.orderByChild("uid").equalTo(mAuth?.currentUser?.uid)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (issue in p0.children) {
                    val key = issue.key

                    val request = LocationRequest()
                    request.interval = 1000
                    request.fastestInterval = 1000
                    request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    val client =
                        LocationServices.getFusedLocationProviderClient(this@TrackingService)
                    val permission = ContextCompat.checkSelfPermission(
                        this@TrackingService,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    if (permission == PackageManager.PERMISSION_GRANTED) {

                        client.requestLocationUpdates(request, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                val location = locationResult?.lastLocation
                                if (location != null) {
                                    key?.let {
                                        ref.child(it).child("latitude")
                                            .setValue(location.latitude.toString())
                                    }
                                    key?.let {
                                        ref.child(it).child("longitude")
                                            .setValue(location.longitude.toString())
                                    }

                                }
                            }
                        }, null)
                    }

                }

            }
        })

    }
}

//    private fun requestLocationUpdates() {
//        val request = LocationRequest()
//

//
//        //Specify how often your app should request the device’s location//
//
//        request.setInterval(1000)
//
//        //Get the most accurate location data available//
//
//        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//        val client = LocationServices.getFusedLocationProviderClient(this)
//
//        val permission = ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//
//        //If the app currently has access to the location permission...//
//
//        if (permission == PackageManager.PERMISSION_GRANTED) {
//
//
//            val ref = FirebaseDatabase.getInstance().getReference(Constan.tb_Uaser)
//
//            val query = ref.orderByChild("uid").equalTo(mAuth?.currentUser?.uid)
//
//            query.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError?) {
//                }
//
//                override fun onDataChange(p0: DataSnapshot?) {
//                    for (issue in p0?.children!!) {
//                        key = issue.key
//
//                        Log.d("kunci", key)
//
//                    }
//                }
//            })
//
//
//
//        }
//
//
//    }
//}