package com.polinema.android.kotlin.pupuk.model

data class PPL(val all: String, val daftar: String, val belum: String, val tidak: String)

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
        val data: ArrayList<data>
)
data class data(
        val petani: String,
        val verifikasi: Boolean)

data class response(
        val status: Int,
        val message: String
)