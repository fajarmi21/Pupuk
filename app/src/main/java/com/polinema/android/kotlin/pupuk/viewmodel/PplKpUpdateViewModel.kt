package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserPPL
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import com.polinema.android.kotlin.pupuk.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplKpUpdateViewModel : ViewModel() {
    var poktan = ObservableField("")
    var nama = ""
    var email = ObservableField("")

    fun show(poktan : String): MutableLiveData<MutableList<UserPPL>> {
        val data = MutableLiveData<MutableList<UserPPL>>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpr(poktan = poktan)
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

    fun updatePpl(pok: String): MutableLiveData<UserPPL> {
        val data = MutableLiveData<UserPPL>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpu(
            email = email.get()!!,
            nama = pok,
            poktan = poktan.get()!!
        )
            .enqueue(object : Callback<UserPPL>{
                override fun onFailure(call: Call<UserPPL>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<UserPPL>, response: Response<UserPPL>) {
                    data.value = response.body()
                }
            })
        return data
    }
}