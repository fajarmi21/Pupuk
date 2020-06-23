package com.polinema.android.kotlin.pupuk.network

import com.polinema.android.kotlin.pupuk.model.*
import retrofit2.Call
import retrofit2.http.*

interface BackEndApi {
    //login
    @FormUrlEncoded
    @POST("login")
    fun LOGIN(@Field("email") email: String, @Field("password") password: String): Call<Login>

    //PPL
    @FormUrlEncoded
    @POST("ppl")
    fun PD(@Field("desa") desa: String): Call<PPL>

    @FormUrlEncoded
    @POST("ppl/PplKpR")
    fun PpKpr(@Field("poktan") poktan: String): Call<MutableList<UserPKl>>

    @FormUrlEncoded
    @POST("ppl/PplKpC")
    fun PpKpc(@Field("nama_desa") id_desa: String,
              @Field("email") email: String,
              @Field("poktan") poktan: String): Call<UserPKl>

    @FormUrlEncoded
    @POST("ppl/PplKpU")
    fun PpKpu(@Field("id_poktan") id_poktan: String,
              @Field("email") email: String,
              @Field("nama") nama: String,
              @Field("id_desa") id_desa: String,
              @Field("poktan") poktan: String): Call<UserPKl>

    @FormUrlEncoded
    @POST("ppl/PplKpD")
    fun PpKpd(@Field("poktan") poktan: String): Call<UserPKl>


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

    @FormUrlEncoded
    @POST("poktan/usulanU")
    fun KpReU(@FieldMap id:HashMap<String,String>): Call<UsulanKT>

    @FormUrlEncoded
    @POST("poktan/rekap")
    fun Kpre(@Field("poktan") poktan: String): Call<MutableList<Rekap>>

    //petani
    @FormUrlEncoded
    @POST("petani")
    fun PTD(@Field("nama_petani") nama_petani: String): Call<Petani>

    @GET("petani/sektor")
    fun PTS(): Call<MutableList<Sektor>>

    @FormUrlEncoded
    @POST("petani/usulan")
    fun PTU(
        @Field("petani") petani: String,
        @Field("sektor") sektor: String,
        @Field("luas") luas: String,
        @Field("urea") urea: String,
        @Field("sp36") sp36: String,
        @Field("za") za: String,
        @Field("npk") npk: String,
        @Field("organik") organik: String,
        @Field("date") date: String,
        @Field("tahap") tahap: String
    ): Call<Pesan>

    @FormUrlEncoded
    @POST("petani/usulanR")
    fun PTUr(
        @Field("petani") petani: String
    ): Call<Usul>

    @FormUrlEncoded
    @POST("petani/usulanU")
    fun PTUu(
        @Field("petani") petani: String,
        @Field("sektor") sektor: String,
        @Field("luas") luas: String,
        @Field("urea") urea: String,
        @Field("sp36") sp36: String,
        @Field("za") za: String,
        @Field("npk") npk: String,
        @Field("organik") organik: String,
        @Field("date") date: String,
        @Field("dateF") dateF: String
    ): Call<Pesan>
}
