package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import com.polinema.android.kotlin.pupuk.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KpPetaniUpdateViewModel : ViewModel(){
    var desa = "3506222001"
    var poktan = ""
    var nik = ObservableField("")
    var petani = ObservableField("")
    var alamat = ObservableField("")
    var sektor = ObservableField("")
    var luas = ObservableField("")
    var email = ObservableField("")
    var btn = ObservableBoolean(false)
    var password = "user"

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btn.set(Util.isEmailValid(email.get()!!))
    }

    fun show(nama: String): MutableLiveData<MutableList<UserKT>> {
        val data = MutableLiveData<MutableList<UserKT>>()
        WebServiceClient.client.create(BackEndApi::class.java).KpRr(kode = "r", poktan = poktan, petani = nama)
            .enqueue(object : Callback<MutableList<UserKT>>{
                override fun onFailure(call: Call<MutableList<UserKT>>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<MutableList<UserKT>>, response: Response<MutableList<UserKT>>) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun update(nama: String): MutableLiveData<UserKT> {
        val data = MutableLiveData<UserKT>()
        WebServiceClient.client.create(BackEndApi::class.java).KpRu(
            kode = "u",
            nama = nama,
            nik = nik.get().toString(),
            nama_petani = petani.get().toString(),
            alamat = alamat.get().toString(),
            sektor = sektor.get().toString(),
            luas_lahan = luas.get().toString(),
            username = petani.get().toString(),
            email = email.get().toString())
            .enqueue(object : Callback<UserKT>{
                override fun onFailure(call: Call<UserKT>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<UserKT>, response: Response<UserKT>) {
                    data.value = response.body()
                }
            })
        return data
    }
}