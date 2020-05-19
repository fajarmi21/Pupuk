package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.internal.LinkedTreeMap
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.KpDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.databinding.PtDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.PtDashboardViewModel
import kotlinx.android.synthetic.main.pt_dashboard_fragment.*

class PtDashboardFragment : Fragment() {
    private lateinit var binding: PtDashboardFragmentBinding
    private lateinit var viewModel: PtDashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pt_dashboard_fragment, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting
        tx_userNamePT.text = SaveSharedPreference.getUser(context)
        tx_userNamePT.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel = ViewModelProvider(this).get(PtDashboardViewModel::class.java)
        viewModel.ptD(tx_userNamePT.text.toString()).observe(viewLifecycleOwner, Observer {
            binding.ptLL.text = """${it.luas_usulan}/${it.luas_lahan} ha"""
            if (it.m1 != "false" && it.m1 != "null") {
                val getrow = it.m1 as LinkedTreeMap<*, *>
                binding.ptU.text = ""
            }
        })
    }

}