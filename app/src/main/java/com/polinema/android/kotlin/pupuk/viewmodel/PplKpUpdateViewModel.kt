package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserPKl
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import com.polinema.android.kotlin.pupuk.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PplKpUpdateViewModel : ViewModel() {
    var id_desa = "3506222001"
    var id = ""
    var id_poktan = ObservableField("")
    var poktan = ObservableField("")
    var email = ObservableField("")
    var btn = ObservableBoolean(false)
    var password = "ppl"

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btn.set(Util.isEmailValid(email.get()!!))
    }

    fun show(poktan : String): MutableLiveData<MutableList<UserPKl>> {
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

    fun updatePpl(id_poktan: String): MutableLiveData<UserPKl> {
        val data = MutableLiveData<UserPKl>()
        WebServiceClient.client.create(BackEndApi::class.java).PpKpu(
            id_poktan = id_poktan,
            id_desa = id_desa.toString(),
            poktan = poktan.get().toString())
            .enqueue(object : Callback<UserPKl>{
                override fun onFailure(call: Call<UserPKl>, t: Throwable) {
                    Log.e("gagal", t.message!!)
                }

                override fun onResponse(call: Call<UserPKl>, response: Response<UserPKl>) {
                    data.value = response.body()
                }
            })
        return data
    }
}