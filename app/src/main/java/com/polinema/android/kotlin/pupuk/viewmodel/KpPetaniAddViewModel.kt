package com.polinema.android.kotlin.pupuk.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import com.polinema.android.kotlin.pupuk.util.Util
import retrofit2.*

class KpPetaniAddViewModel : ViewModel() {
    var desa = "3506222001"
    var poktan = ""
    var nik = ObservableField("")
    var petani = ObservableField("")
    var alamat = ObservableField("")
    var sektor = ObservableField("")
    var luas = ObservableField("")
    var email = ObservableField("")
    var btn = ObservableBoolean(false)
    var password = "user"

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btn.set(Util.isEmailValid(email.get()!!))
    }

    fun ptKc(): MutableLiveData<UserKT> {
        val data = MutableLiveData<UserKT>()
        WebServiceClient.client.create(BackEndApi::class.java).KpRc(
            kode = "c",
            desa = desa,
            poktan = poktan,
            nik = nik.get()!!,
            petani = petani.get()!!,
            alamat = alamat.get()!!,
            sektor = sektor.get()!!,
            luas = luas.get()!!,
            email = email.get()!!,
            password = password
        )
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