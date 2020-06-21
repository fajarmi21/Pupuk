package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.Rekap
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KpRekapViewModel : ViewModel() {
    fun Kpr(poktan: String): MutableLiveData<MutableList<Rekap>> {
        val data = MutableLiveData<MutableList<Rekap>>()
        WebServiceClient.client.create(BackEndApi::class.java).Kpre(poktan)
                .enqueue(object : Callback<MutableList<Rekap>> {
                    override fun onFailure(call: Call<MutableList<Rekap>>, t: Throwable) {
                        Log.e("gagal", t.message!!)
                    }

                    override fun onResponse(
                            call: Call<MutableList<Rekap>>,
                            response: Response<MutableList<Rekap>>
                    ) {
                        data.value = response.body()
                    }
                })
        return data
    }
}