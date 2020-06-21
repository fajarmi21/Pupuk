package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.KelompokTani
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KpDashboardViewModel : ViewModel(), Callback<KelompokTani> {
    var userKpD = MutableLiveData<KelompokTani>()
    var poktan: String? = ""
    init {
        this.userKpD = MutableLiveData<KelompokTani>()
        this.poktan = ""
    }

    fun KpD(): MutableLiveData<KelompokTani> {
        WebServiceClient.client.create(BackEndApi::class.java).KPD(poktan = poktan!!)
            .enqueue(this)
         return userKpD
    }

    override fun onResponse(call: Call<KelompokTani>, response: Response<KelompokTani>) {
        userKpD.value = response.body()
    }

    override fun onFailure(call: Call<KelompokTani>, t: Throwable) {
        Log.e("gagal", t.message!!)
    }
}