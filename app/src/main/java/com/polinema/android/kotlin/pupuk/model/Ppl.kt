package com.polinema.android.kotlin.pupuk.model

data class Ppl(
    val all: Int,
    val daftar: Int,
    val belum: Int,
    val tidak: Int,
    val luas: Double,
    val urea: Double,
    val sp36: Double,
    val za: Double,
    val npk: Double,
    val organik: Double,
    val tpupuk: Double,
    val durea: Double,
    val dsp36: Double,
    val dza: Double,
    val dnpk: Double,
    val dorganik: Double,
    val tdpupuk: Double
)

data class UserPPL(
    val status: Int,
    val message: String,
    val id_poktan: String,
    val email: String,
    val desa: String,
    val poktan: String
)

data class PplVerifikasi(
    val poktan: String,
    val luas: String,
    var data: ArrayList<data>
)
data class data(
    var id: String,
    var petani: String,
    var status_poktan: Boolean,
    var verifikasi: Boolean,
    var admin: Boolean
)

data class response(
    val status: Int,
    val message: String
)