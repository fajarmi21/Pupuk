package com.polinema.android.kotlin.pupuk.model

data class KelompokTani(val all: String, val daftar: String, val belum: String, val tidak: String)
data class UserKT(
    val status: Int,
    val message: String,
    val desa: String,
    val poktan: String,
    val nik: String,
    val petani: String,
    val nama_petani: String,
    val alamat: String,
    val sektor: String,
    val luas_lahan: String,
    val email: String,
    val password: String
)
data class UsulanKT(
    val status: Int,
    val message: String,
    val id_usulan: String,
    val nama_petani: String,
    val luas_lahan: String,
    val m1: tanam,
    val m2: String,
    val m3: String,
    val status_poktan: String,
    val status_ppl: String,
    val status_admin: String,
    val keterangan: String,
    val timestamp: String
)
class tanam(
    val sda: String
)