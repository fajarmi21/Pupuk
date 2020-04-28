package com.polinema.android.kotlin.pupuk.ui.login

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.ActivityLoginBinding
import com.polinema.android.kotlin.pupuk.ui.kp.KpActivity
import com.polinema.android.kotlin.pupuk.util.CustomProgressDialog
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getLevel
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getLoggedStatus
import com.polinema.android.kotlin.pupuk.viewmodel.LoginViewModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor


class LoginActivity : AppCompatActivity(), AnkoLogger {
    private var binding: ActivityLoginBinding? = null
    private var viewmodel: LoginViewModel? = null
    private var customProgressDialog: CustomProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewmodel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding?.viewmodel = viewmodel
        customProgressDialog = CustomProgressDialog(this)

        // CALL getInternetStatus() function to check for internet and display error dialog
        if (InternetDialog(this).internetStatus) {
            if (getLoggedStatus(applicationContext)) {
                when(getLevel(applicationContext)) {
                    4 -> {
                        startActivity(intentFor<KpActivity>().clearTask().clearTop())
                        finish()
                    }
                }
            }
            initObservables()
        } else {
            CustomDialog(this).show(supportFragmentManager, "")
        }
    }

    private fun initObservables() {
        viewmodel?.progressDialog?.observe(this, Observer {
            if (it!!) customProgressDialog?.show() else customProgressDialog?.dismiss()
        })

        viewmodel?.userLogin?.observe(this, Observer { user ->
            Toast.makeText(this, user?.message, Toast.LENGTH_LONG).show()
            when(user?.level) {
                "4" -> {
                    startActivity(intentFor<KpActivity>().clearTask().clearTop())
                    SaveSharedPreference.setLoggedIn(applicationContext, true, user.username, 4)
                    finish()
                }
            }
        })
    }
    }

    internal class CustomDialog(private val context: Activity) : DialogFragment() {
        @Nullable
        override fun onCreateView(
            inflater: LayoutInflater,
            @Nullable container: ViewGroup?,
            @Nullable savedInstanceState: Bundle?
        ): View {
            val view: View = inflater.inflate(R.layout.dialog_no_internet, container, false)
            dialog!!.setTitle("Sample")
            val doneBtn: Button = view.findViewById(R.id.btnSpinAndWinRedeem) as Button
            doneBtn.setOnClickListener(doneAction)
            return view
        }

        private var doneAction =
            View.OnClickListener {
//                if ((InternetDialog(context).internetStatus)
                dialog!!.dismiss()
                if (!InternetDialog(context).internetStatus) {
                    context.finish()
                    context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            }
    }

    class InternetDialog(private val context: Activity) {
        val internetStatus: Boolean
            get() {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            }
    }