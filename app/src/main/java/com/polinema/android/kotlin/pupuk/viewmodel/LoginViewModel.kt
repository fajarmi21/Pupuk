package com.polinema.android.kotlin.pupuk.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.polinema.android.kotlin.pupuk.model.Login
import com.polinema.android.kotlin.pupuk.network.BackEndApi
import com.polinema.android.kotlin.pupuk.network.WebServiceClient
import com.polinema.android.kotlin.pupuk.util.SingleLiveEvent
import com.polinema.android.kotlin.pupuk.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application), Callback<Login> {
    var btnSelected: ObservableBoolean = ObservableBoolean(false)
    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var userLogin: MutableLiveData<Login>? = null

    init {
        progressDialog = SingleLiveEvent<Boolean>()
        email = ObservableField("")
        password = ObservableField("")
        userLogin = MutableLiveData<Login>()
    }

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
//        btnSelected!!.set(Util.isEmailValid(email!!.get()!!))
        if (Util.isEmailValid(email!!.get()!!)) btnSelected.set(true)
        Log.e("btn", btnSelected.get().toString())
    }

    fun login() {
        progressDialog?.value = true
        WebServiceClient.client.create(BackEndApi::class.java).LOGIN(email = email?.get()!!
            , password = password?.get()!!)
            .enqueue(this)
    }

    override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
        progressDialog?.value = false
        userLogin?.value = response?.body()
    }

    override fun onFailure(call: Call<Login>?, t: Throwable?) {
        progressDialog?.value = false
        Log.e("error", t!!.message.toString())
    }

}