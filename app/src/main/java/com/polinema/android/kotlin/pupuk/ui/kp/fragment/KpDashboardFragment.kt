package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.graphics.Paint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.KpDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpDashboardViewModel
import kotlinx.android.synthetic.main.kp_dashboard_fragment.*

class KpDashboardFragment : Fragment() {

//    companion object {
//        private var newInstance() =
//            KpDashboardViewModel()
//    }

    private lateinit var binding: KpDashboardFragmentBinding
    private lateinit var viewModel: KpDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.kp_dashboard_fragment, container, false)
        return binding.root
//        return inflater.inflate(R.layout.kp_dashboard_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setting
        tx_userName.text = SaveSharedPreference.getUser(context)
        tx_userName.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel = ViewModelProviders.of(this).get(KpDashboardViewModel::class.java)
//        binding.user = viewModel
        viewModel.poktan = tx_userName.text.toString()
        // TODO: Use the ViewModel
        viewModel.KpD().observe(viewLifecycleOwner, Observer {
//            UAll.text = it.all
            binding.user = it
        })
        cv1.setOnClickListener {
            val fragment = KpRekapFragment()
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.FrameKP, fragment, fragment.javaClass.simpleName)
                .addToBackStack("KpDashboardFragment")
                .commit()
        }
    }
}