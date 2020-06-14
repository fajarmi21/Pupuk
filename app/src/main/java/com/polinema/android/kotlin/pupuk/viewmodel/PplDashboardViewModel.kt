package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.PPL
import com.polinema.android.kotlin.pupuk.model.Petani
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplDashboardViewModel : ViewModel() {
    fun pD(desa: String): MutableLiveData<PPL> {
        val data = MutableLiveData<PPL>()
        WebServiceClient.client.create(BackEndApi::class.java).PD(desa)
            .enqueue(object : Callback<PPL> {
                override fun onFailure(call: Call<PPL>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<PPL>,
                    response: Response<PPL>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }
}