package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
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
                binding.kpdl.text = String.format("%.2f", it.luas.toDouble()).replace(",", ".")
                binding.totPupuk.text = (it.urea.toDouble() + it.sp36.toDouble() + it.za.toDouble() + it.npk.toDouble() + it.organik.toDouble()).toString()

                if (it.belum.toInt() > 0) binding.labelKPD.visibility = View.VISIBLE

                if (it.admin.verifikasi.urea != 0.0) {
                    binding.llkpdt.visibility = View.VISIBLE
                    binding.kpdtl.text = it.luas
                    binding.kpdtu.text = String.format("%.2f", it.admin.verifikasi.urea).replace(",", ".")
                    binding.kpdts.text = String.format("%.2f", it.admin.verifikasi.sp36).replace(",", ".")
                    binding.kpdtz.text = String.format("%.2f", it.admin.verifikasi.za).replace(",", ".")
                    binding.kpdtn.text = String.format("%.2f", it.admin.verifikasi.npk).replace(",", ".")
                    binding.kpdto.text = String.format("%.2f", it.admin.verifikasi.organik).replace(",", ".")
                    binding.kpdtt.text = String.format("%.2f", (binding.kpdtu.text.toString().toDouble() +
                            binding.kpdts.text.toString().toDouble() +
                            binding.kpdtz.text.toString().toDouble() +
                            binding.kpdtn.text.toString().toDouble() +
                            binding.kpdto.text.toString().toDouble())).replace(",", ".")
                }
            } catch (e: Exception){
                Log.e("ss", e.message!!)
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