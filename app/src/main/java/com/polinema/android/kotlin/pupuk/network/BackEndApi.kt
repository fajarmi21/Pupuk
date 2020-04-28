package com.polinema.android.kotlin.pupuk.network

import com.polinema.android.kotlin.pupuk.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface BackEndApi {
    //login
    @FormUrlEncoded
    @POST("login")
    fun LOGIN(@Field("email") email: String, @Field("password") password: String): Call<Login>

    //kelompoktani
    @FormUrlEncoded
    @POST("poktan")
    fun KPD(@Field("poktan") poktan: String): Call<KelompokTani>

    @FormUrlEncoded
    @POST("poktan/petani")
    fun KpR(@Field("kode") kode: String, @Field("poktan") poktan: String): Call<MutableList<UserKT>>

    @FormUrlEncoded
    @POST("poktan/petani")
    fun KpRr(@Field("kode") kode: String, @Field("poktan") poktan: String, @Field("petani") petani: String): Call<MutableList<UserKT>>

    @FormUrlEncoded
    @POST("poktan/petani")
    fun KpRu(
            @Field("kode") kode: String,
            @Field("nama") nama: String,
            @Field("nik") nik: String,
            @Field("nama_petani") nama_petani: String,
            @Field("alamat") alamat: String,
            @Field("sektor") sektor: String,
            @Field("luas_lahan") luas_lahan: String,
            @Field("username") username: String,
            @Field("email") email: String
    ): Call<UserKT>

    @FormUrlEncoded
    @POST("poktan/petani")
    fun KpRd(@Field("kode") kode: String, @Field("petani") petani: String): Call<UserKT>

    @FormUrlEncoded
    @POST("poktan/petani")
    fun KpRc(
        @Field("kode") kode: String,
        @Field("desa") desa: String,
        @Field("poktan") poktan: String,
        @Field("nik") nik: String,
        @Field("petani") petani: String,
        @Field("alamat") alamat: String,
        @Field("sektor") sektor: String,
        @Field("luas") luas: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserKT>

    @FormUrlEncoded
    @POST("poktan/usulan")
    fun KpRe(@Field("poktan") poktan: String): Call<MutableList<UsulanKT>>

    //petani
    @get:GET("petani")
    val posts: Observable<List<Petani>>
}
