package com.udacoding.ojodriverlfirebasekotlin.utama.home.model

import java.io.Serializable

class Booking :Serializable{
    var tanggal: String? = null
    var uid: String? = null
    var lokasiAwal: String? = null
    var latAwal: Double? = null
    var lonAwal: Double? = null
    var lokasiTujuan: String? = null
    var latTujuan: Double? = null
    var lonTujuan: Double? = null
    var harga: String? = null
    var jarak: String? = null
    var status: Int? = null
            var driver : String?  = null

    init {


    }

}