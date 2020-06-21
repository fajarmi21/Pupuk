package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.Pesan
import com.polinema.android.kotlin.pupuk.model.Petani
import com.polinema.android.kotlin.pupuk.model.Usul
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PtEditUsulanViewModel : ViewModel() {
    fun PTUr(nama_petani: String): MutableLiveData<Usul> {
        val data = MutableLiveData<Usul>()
        WebServiceClient.client.create(BackEndApi::class.java).PTUr(nama_petani)
            .enqueue(object : Callback<Usul> {
                override fun onFailure(call: Call<Usul>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<Usul>, response: Response<Usul>) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun PTUu(p: ArrayList<String>): MutableLiveData<Pesan> {
        val data = MutableLiveData<Pesan>()
        WebServiceClient.client.create(BackEndApi::class.java).PTUu(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7],p[8],p[9])
            .enqueue(object : Callback<Pesan> {
                override fun onFailure(call: Call<Pesan>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<Pesan>, response: Response<Pesan>) {
                    data.value = response.body()
                }
            })
        return data
    }
}