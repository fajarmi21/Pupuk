package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserPPL
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplKpViewModel : ViewModel() {
    var poktan: String? = ""

    fun ppKp(): MutableLiveData<MutableList<UserPPL>> {
        val data = MutableLiveData<MutableList<UserPPL>>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpr(poktan = poktan!!)
            .enqueue(object : Callback<MutableList<UserPPL>> {
                override fun onFailure(call: Call<MutableList<UserPPL>>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<MutableList<UserPPL>>,
                    response: Response<MutableList<UserPPL>>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun ppKpd(poktan: String): MutableLiveData<UserPPL> {
        val data = MutableLiveData<UserPPL>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpd(poktan = poktan!!)
            .enqueue(object : Callback<UserPPL> {
                override fun onFailure(call: Call<UserPPL>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<UserPPL>,
                    response: Response<UserPPL>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }
}