package com.udacoding.ojodriverlfirebasekotlin.detailrequest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.udacoding.ojodriverlfirebasekotlin.R
import com.udacoding.ojodriverlfirebasekotlin.utama.HomeActivity
import com.udacoding.ojodriverlfirebasekotlin.utama.home.model.Booking
import com.udacoding.ojodriverlfirebasekotlin.utils.Constan
import kotlinx.android.synthetic.main.activity_detail_request.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity



class DetailRequest : AppCompatActivity(), OnMapReadyCallback {

    var status : Int? = null
    var booking : Booking? = null

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_request)




        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapsdetail) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var auth = FirebaseAuth.getInstance()

        booking = Booking()
        booking = intent.getSerializableExtra(Constan.booking) as Booking
        status = intent.getIntExtra(Constan.status,0)

        detailAwal.text = booking?.lokasiAwal
        detailTujuan.text = booking?.lokasiTujuan
        detailTanggal.text = booking?.tanggal
        detailPrice.text = booking?.harga

        if(status == 2){
            detailbutton.text = getString(R.string.complete)
        }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(Constan.tb_Booking)

        var key = ""

        val query = myRef.orderByChild("tanggal").equalTo(booking?.tanggal)
        query.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {

                for(issu in p0?.children!!)

                key = issu.key
            }

            override fun onCancelled(p0: DatabaseError?) {
            }

        })

        detailbutton.onClick {

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(Constan.tb_Booking)
            if(status == 1) {

                myRef.child(key).child("status").setValue(2)
                myRef.child(key).child("driver").setValue(auth.currentUser?.uid)

                startActivity<HomeActivity>()
            }
            else if(status == 2){

                myRef.child(key).child("status").setValue(4)
                startActivity<HomeActivity>()


            }




        }


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latlng = booking?.latAwal?.toDouble()?.let { booking?.lonAwal?.toDouble()?.let { it1 -> LatLng(it, it1) } }
        val latlng1 = booking?.latTujuan?.toDouble()?.let { booking?.lonTujuan?.toDouble()?.let { it1 -> LatLng(it, it1) } }

        mMap.addMarker(latlng?.let { MarkerOptions().position(it).title("Awal").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)) })
        mMap.addMarker(latlng1?.let { MarkerOptions().position(it).title("tujuan").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)) })

        var builder = LatLngBounds.builder()
        builder.include(latlng)
        builder.include(latlng1)
       // mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),12))

        mMap.setOnCameraChangeListener(GoogleMap.OnCameraChangeListener {
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    builder.build(),
                    17
                )
            )
        })


        // Add a marker in Sydney and move the camera
    }
    }
