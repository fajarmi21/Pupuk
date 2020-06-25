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
    val status: Int,
    val message: String,
    val id_poktan: String,
    val desa: String,
    val timestamp: String,
    val poktan: String,
    val id_usulan: String,
    val nama_petani: String,
    val luas_lahan: String,
    val tahap: String,
    val m1: Any,
    val m2: Any,
    val m3: Any,
    var status_poktan: Any,
    val status_ppl: Any,
    val status_admin: Any,
    val keterangan: String,
    var isSelected: Boolean = false
)