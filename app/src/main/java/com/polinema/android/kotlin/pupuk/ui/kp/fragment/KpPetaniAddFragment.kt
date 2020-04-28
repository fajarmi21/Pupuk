package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.os.Bundle
import android.util.Log
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
import com.polinema.android.kotlin.pupuk.databinding.KpPetaniAddFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpPetaniAddViewModel
import kotlinx.android.synthetic.main.kp_petani_add_fragment.*


class KpPetaniAddFragment : Fragment() {
    private lateinit var viewModel: KpPetaniAddViewModel
    private lateinit var binding: KpPetaniAddFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.kp_petani_add_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(KpPetaniAddViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.poktan = SaveSharedPreference.getUser(context)
        spinner()
        add_button_detailKPP.setOnClickListener {
            viewModel.ptKc().observe(viewLifecycleOwner, Observer {
                Log.e("sukses", it.message)
                if (it.status == 1) {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.FrameKP, KpPetaniFragment(), KpPetaniFragment().javaClass.simpleName)
                        .commit()
                }
                else { Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show() }
            })
        }
        cancel_button_detailKPP.setOnClickListener { activity!!.supportFragmentManager.popBackStack() }
    }

    private fun spinner() {
        val tanam = arrayOf("tanaman pangan")
        val adapter = ArrayAdapter(context!!, R.layout.dropdown_menu_item, tanam)
        val editTextFilledExposedDropdown: AutoCompleteTextView = view!!.findViewById(R.id.filled_exposed_dropdown)
        editTextFilledExposedDropdown.setAdapter(adapter)
    }
}