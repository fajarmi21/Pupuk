package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserPKl
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplKpViewModel : ViewModel() {
    var poktan: String? = ""

    fun ppKp(): MutableLiveData<MutableList<UserPKl>> {
        val data = MutableLiveData<MutableList<UserPKl>>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpr(poktan = poktan!!)
            .enqueue(object : Callback<MutableList<UserPKl>> {
                override fun onFailure(call: Call<MutableList<UserPKl>>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<MutableList<UserPKl>>,
                    response: Response<MutableList<UserPKl>>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }
    fun ppKpd(poktan: String): MutableLiveData<UserPKl> {
        val data = MutableLiveData<UserPKl>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpd(poktan = poktan!!)
            .enqueue(object : Callback<UserPKl> {
                override fun onFailure(call: Call<UserPKl>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(
                    call: Call<UserPKl>,
                    response: Response<UserPKl>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }
}