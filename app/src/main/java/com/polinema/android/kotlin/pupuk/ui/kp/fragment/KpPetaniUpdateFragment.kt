package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.KpPetaniUpdateFragmentBinding
import com.polinema.android.kotlin.pupuk.viewmodel.KpPetaniUpdateViewModel
import kotlinx.android.synthetic.main.kp_petani_fragment.*
import kotlinx.android.synthetic.main.kp_petani_update_fragment.*
import java.util.*


class KpPetaniUpdateFragment : Fragment() {

    private lateinit var viewModel: KpPetaniUpdateViewModel
    private lateinit var binding: KpPetaniUpdateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.kp_petani_update_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(KpPetaniUpdateViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        spinner()
        showData()
        button()
    }

    private fun button() {
        cancel_button_detailKPU.setOnClickListener {activity!!.supportFragmentManager.popBackStack()}

        back_button_detailKPU.setOnClickListener {
            txDetailKPU.text = "Detail"
            atx1KPU.isEnabled = false
            atx2KPU.isEnabled = false
            atx3KPU.isEnabled = false
            atx4KPU.isEnabled = false
            atx5KPU.isEnabled = false
            atx6KPU.isEnabled = false
            showData()
            back_button_detailKPU.visibility = View.GONE
            update_button_detailKPU.visibility = View.GONE
            cancel_button_detailKPU.visibility = View.VISIBLE
            upgrade_button_detailKPU.visibility = View.VISIBLE
        }

        upgrade_button_detailKPU.setOnClickListener {
            txDetailKPU.text = "Update"
            atx1KPU.isEnabled = true
            atx2KPU.isEnabled = true
            atx3KPU.isEnabled = true
            atx4KPU.isEnabled = true
            atx5KPU.isEnabled = true
            atx6KPU.isEnabled = true
            back_button_detailKPU.visibility = View.VISIBLE
            update_button_detailKPU.visibility = View.VISIBLE
            cancel_button_detailKPU.visibility = View.GONE
            upgrade_button_detailKPU.visibility = View.GONE
        }

        update_button_detailKPU.setOnClickListener {
            viewModel.update(arguments!!.getString("petani").toString()).observe(viewLifecycleOwner, Observer {
                if (it.status == 1) {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    activity!!.supportFragmentManager
                        .beginTransaction()
                            .replace(R.id.FrameKP, KpPetaniFragment(), KpPetaniFragment().javaClass.simpleName)
                            .commit()
                } else { Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show() }
            })
        }
    }

    private fun spinner() {
        val tanam = arrayOf("tanaman pangan")
        val adapter = ArrayAdapter(context!!, R.layout.dropdown_menu_item, tanam)
        val editTextFilledExposedDropdown: AutoCompleteTextView = view!!.findViewById(R.id.filled_exposed_dropdownKPU)
        editTextFilledExposedDropdown.setAdapter(adapter)
    }

    private fun showData() {
        viewModel.show(arguments!!.getString("petani").toString()).observe(viewLifecycleOwner, Observer {
            viewModel.nik.set(it[0].nik)
            viewModel.petani.set(it[0].nama_petani)
            viewModel.email.set(it[0].email)
            viewModel.alamat.set(it[0].alamat)
            viewModel.sektor.set(it[0].sektor)
            viewModel.luas.set(it[0].luas_lahan)
        })
    }
}