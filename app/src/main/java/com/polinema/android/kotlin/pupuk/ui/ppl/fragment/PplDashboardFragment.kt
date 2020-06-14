package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PplDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.PplDashboardViewModel
import kotlinx.android.synthetic.main.kp_dashboard_fragment.*
import java.lang.Exception

class PplDashboardFragment : Fragment() {
    private lateinit var viewModel: PplDashboardViewModel
    private lateinit var binding: PplDashboardFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.ppl_dashboard_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PplDashboardViewModel::class.java)
        tx_userName.text = SaveSharedPreference.getUser(context)
        tx_userName.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel.pD(tx_userName.text.toString()).observeForever {
            try {
                binding.user = it
            } catch (e: Exception) {
                Log.e("error", e.message!!)
            }
        }
    }

}