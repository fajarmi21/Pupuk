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
import androidx.lifecycle.Observer
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PplDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.ui.kp.fragment.KpUsulFragment
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.viewmodel.PplDashboardViewModel
import kotlinx.android.synthetic.main.kp_dashboard_fragment.*
import kotlinx.android.synthetic.main.kp_dashboard_fragment.tx_userName
import kotlinx.android.synthetic.main.ppl_dashboard_fragment.*
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
        tx_userName.text = getUser(context)
        tx_userName.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel.pD(tx_userName.text.toString()).observe(viewLifecycleOwner, Observer {
            try {
                binding.user = it
//                Log.e("xx", it.toString())
                if (it.urea != 0.toDouble()) {
                    binding.lpupukPPL.visibility = View.VISIBLE
                    binding.ppltpupuk.text = String.format("%.2f", it.tpupuk).replace(",", ".")
                    binding.pplluas.text = String.format("%.2f", it.luas).replace(",", ".")
                    binding.pplurea.text = String.format("%.2f", it.urea).replace(",", ".")
                    binding.pplsp36.text = String.format("%.2f", it.sp36).replace(",", ".")
                    binding.pplza.text = String.format("%.2f", it.za).replace(",", ".")
                    binding.pplnpk.text = String.format("%.2f", it.npk).replace(",", ".")
                    binding.pplorganik.text = String.format("%.2f", it.organik).replace(",", ".")
                }

                if (it.durea != 0.toDouble()) {
                    binding.ltpupukPPL.visibility = View.VISIBLE
                    binding.ppltdpupuk.text = String.format("%.2f", it.tdpupuk).replace(",", ".")
                    binding.ppldluas.text = String.format("%.2f", it.luas).replace(",", ".")
                    binding.ppldurea.text = String.format("%.2f", it.durea).replace(",", ".")
                    binding.ppldsp36.text = String.format("%.2f", it.dsp36).replace(",", ".")
                    binding.ppldza.text = String.format("%.2f", it.dza).replace(",", ".")
                    binding.ppldnpk.text = String.format("%.2f", it.dnpk).replace(",", ".")
                    binding.ppldorganik.text = String.format("%.2f", it.dorganik).replace(",", ".")
                }
            } catch (e: Exception) {
                Log.e("error", e.message!!)
            }
        })
        binding.cv1ppl.setOnClickListener {
            val fragment = PplUsulFragment()
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.FramePPL, fragment, fragment.javaClass.simpleName)
                    .addToBackStack("PplDashboardFragment")
                    .commit()
        }
    }

}