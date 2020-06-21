package com.polinema.android.kotlin.pupuk.model
import com.google.gson.annotations.SerializedName


data class Petani(
    val alamat: String,
    val id_petani: String,
    val id_usulan: String,
    val keterangan: Any,
    val luas_lahan: String,
    val luas_usulan: String,
    val m1: Any,
    val m2: Any,
    val m3: Any,
    val nama_desa: String,
    val nama_petani: String,
    val nik: String,
    val poktan: String,
    val sektor: String,
    val status_admin: Any,
    val status_poktan: Any,
    val status_ppl: Any,
    val tahap: String,
    val timestamp: String
)

class Sektor(
    val nama_tanaman: String
)

class Usul(
    val sektor: String,
    val luas: String,
    val urea: String,
    val sp36: String,
    val za: String,
    val npk: String,
    val organik: String,
    val date: String
)

data class Pesan(
    val status: Int,
    val message: String
)