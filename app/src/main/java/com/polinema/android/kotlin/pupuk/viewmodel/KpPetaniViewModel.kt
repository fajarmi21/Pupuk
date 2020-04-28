package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.*

class KpPetaniViewModel : ViewModel() {
    var poktan: String? = ""

    fun ptKr(): MutableLiveData<MutableList<UserKT>> {
        val data = MutableLiveData<MutableList<UserKT>>()
        WebServiceClient.client.create(BackEndApi::class.java).KpR(kode = "r", poktan = poktan!!)
            .enqueue(object : Callback<MutableList<UserKT>> {
                override fun onFailure(call: Call<MutableList<UserKT>>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<MutableList<UserKT>>,
                    response: Response<MutableList<UserKT>>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun ptKd(petani: String): MutableLiveData<UserKT> {
        val data = MutableLiveData<UserKT>()
        WebServiceClient.client.create(BackEndApi::class.java).KpRd(kode = "d", petani = petani)
            .enqueue(object : Callback<UserKT> {
                override fun onFailure(call: Call<UserKT>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<UserKT>,
                    response: Response<UserKT>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }
}