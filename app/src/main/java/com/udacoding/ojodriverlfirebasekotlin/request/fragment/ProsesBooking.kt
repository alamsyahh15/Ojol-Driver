package com.udacoding.ojodriverlfirebasekotlin.request.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.udacoding.ojodriverlfirebasekotlin.R
import com.udacoding.ojodriverlfirebasekotlin.detailrequest.DetailRequest
import com.udacoding.ojodriverlfirebasekotlin.request.fragment.adapter.BookingAdapter
import com.udacoding.ojodriverlfirebasekotlin.utama.home.model.Booking
import com.udacoding.ojodriverlfirebasekotlin.utils.Constan
import kotlinx.android.synthetic.main.fragment_item_list.*
import org.jetbrains.anko.support.v4.startActivity

class ProsesBooking : Fragment() {


    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)


        this.exPlore()

        return view
    }

    private fun exPlore() {
        val auth = FirebaseAuth.getInstance()


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(Constan.tb_Booking)

        val data = ArrayList<Booking>()
        val query = myRef.orderByChild("driver").equalTo(auth.currentUser?.uid)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (issue in dataSnapshot.children) {

                    val dataFirebase = issue.getValue(Booking::class.java)

                    if (dataFirebase?.status == 2) {


                        val booking = Booking()
                        booking.tanggal = dataFirebase.tanggal
                        booking.uid = dataFirebase.uid
                        booking.lokasiAwal = dataFirebase.lokasiAwal
                        booking.latAwal = dataFirebase.latAwal
                        booking.lonAwal = dataFirebase.lonAwal
                        booking.latTujuan = dataFirebase.latAwal
                        booking.lonTujuan = dataFirebase.lonTujuan
                        booking.lokasiTujuan = dataFirebase.lokasiTujuan
                        booking.jarak = dataFirebase.jarak
                        booking.harga = dataFirebase.harga
                        booking.status = dataFirebase.status
                        data.add(booking)



                        showData(data)

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })


    }

    private fun showData(data: ArrayList<Booking>) {

        try {
            list.adapter = BookingAdapter(data, object : OnListFragmentInteractionListener,
                RequestBooking.OnListFragmentInteractionListener {
                override fun onListFragmentInteraction(item: Booking?) {

                    startActivity<DetailRequest>(Constan.booking to item!!, Constan.status to 2)
                }
            })
            list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        } catch (e: IllegalStateException) {

        }
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Booking?)
    }

}