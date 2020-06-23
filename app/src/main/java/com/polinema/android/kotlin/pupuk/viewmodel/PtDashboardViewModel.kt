package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.Petani
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PtDashboardViewModel : ViewModel() {
    var luas = ""
    var urea = ""
    var sp36 = ""
    var za = ""
    var npk = ""
    var organik = ""
    var status = false
    var daftar = ""

    fun ptD(nama_petani: String): MutableLiveData<Petani> {
        val data = MutableLiveData<Petani>()
        WebServiceClient.client.create(BackEndApi::class.java).PTD(nama_petani)
                .enqueue(object : Callback<Petani>{
                    override fun onFailure(call: Call<Petani>, t: Throwable) {
                        Log.e("gagal", t.message!!)
                    }

                    override fun onResponse(call: Call<Petani>, response: Response<Petani>) {
                        data.value = response.body()
                    }
                })
        return data
    }
}