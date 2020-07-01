package com.polinema.android.kotlin.pupuk.model

data class KelompokTani(
        val all: String,
        val daftar: String,
        val belum: String,
        val tidak: String,
        val luas: String,
        val urea: String,
        val sp36: String,
        val za: String,
        val npk: String,
        val organik: String
)

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
    val tahap: String,
    val m1: Any,
    val m2: Any,
    val m3: Any,
    var status_poktan: Any,
    val status_ppl: Any,
    val status_admin: Any,
    val keterangan: String,
    val timestamp: String,
    var isSelected: Boolean = false
)

data class Rekap(
    val id_petani: String,
    val id_desa: String,
    val id_kelompok: String,
    val nik: String,
    val nama_petani: String,
    val alamat: String,
    val sektor: String,
    val luas_lahan: String,
    val tahap: String,
    val id_usulan: String,
    val m1: List<thp>,
    val m2: List<thp>,
    val m3: List<thp>,
    val status_poktan: List<List<String>>,
    val status_ppl: Any,
    val status_admin: Any,
    val keterangan: Any,
    val timestamp: String,
    val kode_desa: String,
    val nama_desa: String,
    val id_poktan: String,
    val poktan: String
)

class thp(
    val sektor: String,
    val luas: String,
    val urea: String,
    val sp36: String,
    val za: String,
    val npk: String,
    val organik: String,
    val date: String
)