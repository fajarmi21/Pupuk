package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KpUsulViewModel : ViewModel() {
    fun rekap(poktan : String): MutableLiveData<MutableList<UsulanKT>> {
        val data = MutableLiveData<MutableList<UsulanKT>>()
        WebServiceClient.client.create(BackEndApi::class.java).KpRe(poktan)
            .enqueue(object : Callback<MutableList<UsulanKT>> {
                override fun onFailure(call: Call<MutableList<UsulanKT>>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<MutableList<UsulanKT>>,
                    response: Response<MutableList<UsulanKT>>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun verifikasi(id : HashMap<String,String>): MutableLiveData<UsulanKT> {
        val data = MutableLiveData<UsulanKT>()
        WebServiceClient.client.create(BackEndApi::class.java).KpReU(id)
                .enqueue(object : Callback<UsulanKT>{
                    override fun onFailure(call: Call<UsulanKT>, t: Throwable) {
                        Log.e("gagal", t.message!!)
                    }

                    override fun onResponse(call: Call<UsulanKT>, response: Response<UsulanKT>) {
                        data.value = response.body()
                    }

                })
        return data
    }

    fun verifikasiT(id : String, alasan : String): MutableLiveData<UsulanKT> {
        val data = MutableLiveData<UsulanKT>()
        WebServiceClient.client.create(BackEndApi::class.java).KpReT(id, alasan)
            .enqueue(object : Callback<UsulanKT>{
                override fun onFailure(call: Call<UsulanKT>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<UsulanKT>, response: Response<UsulanKT>) {
                    data.value = response.body()
                }

            })
        return data
    }
}