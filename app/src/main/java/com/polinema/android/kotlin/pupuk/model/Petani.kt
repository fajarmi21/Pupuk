package com.polinema.android.kotlin.pupuk.model
import com.google.gson.annotations.SerializedName


data class Petani(
    val alamat: String,
    val id_petani: String,
    val id_usulan: String,
    val keterangan: Any,
    val luas_lahan: String,
    val luas_usulan: String,
    val m1: ArrayList<Any>,
    val m2: ArrayList<Any>,
    val m3: ArrayList<Any>,
    val nama_desa: String,
    val nama_petani: String,
    val nik: String,
    val poktan: String,
    val sektor: String,
    val status_admin: Verif,
    val status_poktan: Verif,
    val status_ppl: Verif,
    val tahap: String,
    val timestamp: String
)

class Verif(
    val status: Boolean,
    val alasan: String,
    val tahap: String,
    val tahun: String,
    val verifikasi: Verifikasi
)

class Verifikasi(
    val urea: Double,
    val sp36: Double,
    val za: Double,
    val npk: Double,
    val organik: Double
)

class Tahap(
    val tahap: String,
    val bulan: String
)

class Sektor(
    val nama_tanaman: String
)

class Usul(
    val sektor: String,
    val luas: String,
    val luas_lahan: String,
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