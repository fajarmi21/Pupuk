package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.Petani
import com.polinema.android.kotlin.pupuk.model.Sektor
import com.polinema.android.kotlin.pupuk.model.Tahap
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PtDashboardViewModel : ViewModel() {
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

    fun ptT(): MutableLiveData<MutableList<Tahap>> {
        val data = MutableLiveData<MutableList<Tahap>>()
        WebServiceClient.client.create(BackEndApi::class.java).PTT()
            .enqueue(object : Callback<MutableList<Tahap>> {
                override fun onFailure(call: Call<MutableList<Tahap>>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<MutableList<Tahap>>, response: Response<MutableList<Tahap>>) {
                    data.value = response.body()
                }
            })
        return data
    }
}