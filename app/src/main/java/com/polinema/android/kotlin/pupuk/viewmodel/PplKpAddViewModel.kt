package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.model.UserPKl
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import com.polinema.android.kotlin.pupuk.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplKpAddViewModel : ViewModel() {
    var desa = "3506222001"
    var id = ""
    var poktan = ""
    var email = ObservableField("")
    var btn = ObservableBoolean(false)
    var password = "ppl"

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btn.set(Util.isEmailValid(email.get()!!))
    }

    fun pplKp(): MutableLiveData<UserPKl> {
        val data = MutableLiveData<UserPKl>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpc(
            id_poktan = id,
            id_desa = desa,
            poktan = poktan
        )
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