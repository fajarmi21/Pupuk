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

class PplKpAddViewModel : ViewModel() {
//    var id_poktan = ""
    var poktan = ""
    var email = ObservableField("")
    var btn = ObservableBoolean(false)
    var password = "ppl"

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btn.set(Util.isEmailValid(email.get()!!))
    }

    fun pplKp(desa: String): MutableLiveData<UserPPL> {
        val data = MutableLiveData<UserPPL>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpc(
            id_desa = desa,
            email = email.get()!!,
            poktan = poktan
        )
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