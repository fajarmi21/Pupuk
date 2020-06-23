package com.polinema.android.kotlin.pupuk.model

data class PPL(val all: String, val daftar: String, val belum: String, val tidak: String)

data class UserPKl(
    val status: Int,
    val message: String,
    val id_poktan: String,
    val desa: String,
    val poktan: String
)