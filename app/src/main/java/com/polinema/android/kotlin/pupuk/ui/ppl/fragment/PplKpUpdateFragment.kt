package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.polinema.android.kotlin.pupuk.viewmodel.PplKpUpdateViewModel
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PplKpUpdateFragmentBinding
import kotlinx.android.synthetic.main.ppl_kp_update_fragment.*

class PplKpUpdateFragment : Fragment() {

    private lateinit var viewModel: PplKpUpdateViewModel
    private lateinit var binding: PplKpUpdateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.ppl_kp_update_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(PplKpUpdateViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showData()
        button()
    }

    private fun button() {
        cancel_button_detailPPLU.setOnClickListener {activity!!.supportFragmentManager.popBackStack()}

        back_button_detailPPLU.setOnClickListener {
            txDetailPPLU.text = "Detail"
            atxPPLU.isEnabled = false
            atx1PPLU.isEnabled = false
            showData()
            back_button_detailPPLU.visibility = View.GONE
            update_button_detailPPLU.visibility = View.GONE
            cancel_button_detailPPLU.visibility = View.VISIBLE
            upgrade_button_detailPPLU.visibility = View.VISIBLE
        }

        upgrade_button_detailPPLU.setOnClickListener {
            txDetailPPLU.text = "Update"
            atxPPLU.isEnabled = true
            atx1PPLU.isEnabled = true
            back_button_detailPPLU.visibility = View.VISIBLE
            update_button_detailPPLU.visibility = View.VISIBLE
            cancel_button_detailPPLU.visibility = View.GONE
            upgrade_button_detailPPLU.visibility = View.GONE
        }

        update_button_detailPPLU.setOnClickListener {
            viewModel.updatePpl(arguments!!.getString("poktan").toString()).observe(viewLifecycleOwner, Observer {
                if (it.status == 1) {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.FramePPL, PplKpFragment(), PplKpFragment().javaClass.simpleName)
                        .commit()
                } else { Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show() }
            })
        }
    }
    private fun showData() {
        viewModel.show(arguments!!.getString("poktan").toString()).observeForever {
            Log.e("xx", it.toString())
            viewModel.poktan = it[0].poktan
            viewModel.nama = it[0].poktan
            viewModel.email.set(it[0].email)
        }
    }

}