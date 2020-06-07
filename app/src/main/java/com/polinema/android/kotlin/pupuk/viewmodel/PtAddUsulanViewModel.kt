package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.Pesan
import com.polinema.android.kotlin.pupuk.model.Petani
import com.polinema.android.kotlin.pupuk.model.Sektor
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PtAddUsulanViewModel : ViewModel() {
    fun ptD(nama_petani: String): MutableLiveData<Petani> {
        val data = MutableLiveData<Petani>()
        WebServiceClient.client.create(BackEndApi::class.java).PTD(nama_petani)
            .enqueue(object : Callback<Petani> {
                override fun onFailure(call: Call<Petani>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<Petani>, response: Response<Petani>) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun ptS(): MutableLiveData<MutableList<Sektor>> {
        val data = MutableLiveData<MutableList<Sektor>>()
        WebServiceClient.client.create(BackEndApi::class.java).PTS()
                .enqueue(object : Callback<MutableList<Sektor>> {
                    override fun onFailure(call: Call<MutableList<Sektor>>, t: Throwable) {
                        Log.e("gagal", t.message!!)
                    }

                    override fun onResponse(call: Call<MutableList<Sektor>>, response: Response<MutableList<Sektor>>) {
                        data.value = response.body()
                    }
                })
        return data
    }

    fun ptU(p: ArrayList<String>): MutableLiveData<Pesan> {
        val data = MutableLiveData<Pesan>()
        WebServiceClient.client.create(BackEndApi::class.java).PTU(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7],p[8],p[9])
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