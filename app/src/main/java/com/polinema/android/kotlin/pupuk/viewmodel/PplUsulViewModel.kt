package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.PplVerifikasi
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplUsulViewModel : ViewModel() {
    fun PplUsul(poktan : String): MutableLiveData<MutableList<PplVerifikasi>> {
        val data = MutableLiveData<MutableList<PplVerifikasi>>()
        WebServiceClient.client.create(BackEndApi::class.java).PpV(poktan = poktan)
            .enqueue(object : Callback<MutableList<PplVerifikasi>> {
                override fun onFailure(call: Call<MutableList<PplVerifikasi>>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<MutableList<PplVerifikasi>>,
                    response: Response<MutableList<PplVerifikasi>>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun PplUsulU(id : HashMap<String,String>): MutableLiveData<PplVerifikasi> {
        val data = MutableLiveData<PplVerifikasi>()
        WebServiceClient.client.create(BackEndApi::class.java).PpVU(id = id)
            .enqueue(object : Callback<PplVerifikasi>{
                override fun onFailure(call: Call<PplVerifikasi>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<PplVerifikasi>, response: Response<PplVerifikasi>) {
                    data.value = response.body()
                }

            })
        return data
    }
}