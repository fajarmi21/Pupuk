package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.KpDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpDashboardViewModel
import kotlinx.android.synthetic.main.kp_dashboard_fragment.*
import java.lang.Exception

class KpDashboardFragment : Fragment() {
    private lateinit var binding: KpDashboardFragmentBinding
    private lateinit var viewModel: KpDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.kp_dashboard_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setting
        tx_userName.text = SaveSharedPreference.getUser(context)
        tx_userName.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel = ViewModelProvider(this).get(KpDashboardViewModel::class.java)
//        binding.user = viewModel
        viewModel.poktan = tx_userName.text.toString()
        // TODO: Use the ViewModel
        viewModel.KpD().observe(viewLifecycleOwner, Observer {
            try {
                binding.user = it
                binding.totPupuk.text = (it.urea.toDouble() + it.sp36.toDouble() + it.za.toDouble() + it.npk.toDouble() + it.organik.toDouble()).toString()
            } catch (e: Exception){

            }
        })
        cv1.setOnClickListener {
            val fragment = KpUsulFragment()
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.FrameKP, fragment, fragment.javaClass.simpleName)
                .addToBackStack("KpDashboardFragment")
                .commit()
        }
    }
}