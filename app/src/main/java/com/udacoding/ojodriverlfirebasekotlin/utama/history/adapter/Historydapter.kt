package com.udacoding.ojodriverlfirebasekotlin.utama.history.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.udacoding.ojodriverlfirebasekotlin.R
import com.udacoding.ojodriverlfirebasekotlin.utama.home.model.Booking


import kotlinx.android.synthetic.main.booking_item.view.*


class Historydapter(
    private val mValues: List<Booking>
) : androidx.recyclerview.widget.RecyclerView.Adapter<Historydapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
      holder.mAwal.text = item.lokasiAwal
        holder.mTanggal.text = item.tanggal
        holder.mTujuan.text = item.lokasiTujuan



    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
        var mAwal: TextView = mView.itemAwal
        val mTujuan: TextView = mView.itemTujuan
        val mTanggal : TextView = mView.itemTanggal

    }
}
