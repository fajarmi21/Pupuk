package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.PplVerifikasi
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.model.response
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplUsulViewModel : ViewModel() {
    fun PplUsul(desa : String): MutableLiveData<MutableList<PplVerifikasi>> {
        val data = MutableLiveData<MutableList<PplVerifikasi>>()
        WebServiceClient.client.create(BackEndApi::class.java).PpV(desa)
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

    fun PplUsulU(id : HashMap<String,String>): MutableLiveData<response> {
        Log.e("yy", id.toString())
        val data = MutableLiveData<response>()
        WebServiceClient.client.create(BackEndApi::class.java).PpVU(id)
            .enqueue(object : Callback<response>{
                override fun onFailure(call: Call<response>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<response>, response: Response<response>) {
                    data.value = response.body()
                }

            })
        return data
    }

    fun PplUsulT(id : HashMap<String,String>, alasan : String): MutableLiveData<response> {
        Log.e("yy", id.toString())
        val data = MutableLiveData<response>()
        WebServiceClient.client.create(BackEndApi::class.java).PpVUT(id, alasan)
            .enqueue(object : Callback<response>{
                override fun onFailure(call: Call<response>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<response>, response: Response<response>) {
                    data.value = response.body()
                }

            })
        return data
    }
}