package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PtAddUsulanFragmentBinding
import com.polinema.android.kotlin.pupuk.databinding.PtEditUsulanFragmentBinding
import com.polinema.android.kotlin.pupuk.util.MinMaxFilter
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.viewmodel.PtAddUsulanViewModel
import com.polinema.android.kotlin.pupuk.viewmodel.PtEditUsulanViewModel
import java.math.RoundingMode

class PtEditUsulanFragment : Fragment() {
    private lateinit var viewModel: PtEditUsulanViewModel
    private lateinit var viewModel1: PtAddUsulanViewModel
    private lateinit var binding: PtEditUsulanFragmentBinding
    private var dateF = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pt_edit_usulan_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PtEditUsulanViewModel::class.java)
        viewModel1 = ViewModelProvider(this).get(PtAddUsulanViewModel::class.java)
        viewModel1.ptS().observeForever {it2->
            val items = ArrayList<String>()
            it2.forEach {
                items.add(it.nama_tanaman)
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
            binding.txaSektorE.setAdapter(adapter)
        }
        viewModel.PTUr(getUser(context)).observeForever {
            dateF = it.date
            binding.txaIdUserE.setText(getUser(context))
            binding.txDateE.text = it.date
            binding.txaSektorE.setText(it.sektor)

            binding.idLuasE.helperText = """max : ${it.luas_lahan}/ha"""
            binding.idUreaE.helperText = """max : ${it.urea}/ha"""
            binding.idSp36E.helperText = """max : ${it.sp36}/ha"""
            binding.idZaE.helperText = """max : ${it.za}/ha"""
            binding.idNpkE.helperText = """max : ${it.npk}/ha"""
            binding.idOrganikE.helperText = """max : ${it.organik}/ha"""

            val urea = roundOfDecimal(it.luas.toDouble() * 300.00)
            val sp36 = roundOfDecimal(it.luas.toDouble() * 50.00)
            val za = roundOfDecimal(it.luas.toDouble() * 200.00)
            val npk = roundOfDecimal(it.luas.toDouble() * 400.00)
            val organik = roundOfDecimal(it.luas.toDouble() * 500.00)

            binding.txaLuasE.filters = arrayOf(MinMaxFilter(0.0, it.luas_lahan.toDouble()), InputFilter.LengthFilter(6))
            binding.txaUreaE.filters = arrayOf(MinMaxFilter(0.0, urea), InputFilter.LengthFilter(6))
            binding.txaSp36E.filters = arrayOf(MinMaxFilter(0.0, sp36), InputFilter.LengthFilter(6))
            binding.txaZaE.filters = arrayOf(MinMaxFilter(0.0, za), InputFilter.LengthFilter(6))
            binding.txaNpkE.filters = arrayOf(MinMaxFilter(0.0, npk), InputFilter.LengthFilter(6))
            binding.txaOrganikE.filters = arrayOf(MinMaxFilter(0.0, organik), InputFilter.LengthFilter(6))

            binding.txaLuasE.setText(it.luas)
            binding.txaUreaE.setText(it.urea)
            binding.txaSp36E.setText(it.sp36)
            binding.txaZaE.setText(it.za)
            binding.txaNpkE.setText(it.npk)
            binding.txaOrganikE.setText(it.organik)
        }
        binding.txaLuasE.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                val urea = roundOfDecimal(text.toString().toDouble() * 300.00)
                val sp36 = roundOfDecimal(text.toString().toDouble() * 50.00)
                val za = roundOfDecimal(text.toString().toDouble() * 200.00)
                val npk = roundOfDecimal(text.toString().toDouble() * 400.00)
                val organik = roundOfDecimal(text.toString().toDouble() * 500.00)

                binding.idUreaE.helperText = """max : ${urea}/ha"""
                binding.idSp36E.helperText = """max : ${sp36}/ha"""
                binding.idZaE.helperText = """max : ${za}/ha"""
                binding.idNpkE.helperText = """max : ${npk}/ha"""
                binding.idOrganikE.helperText = """max : ${organik}/ha"""

                binding.txaUreaE.filters = arrayOf(MinMaxFilter(0.0, urea), InputFilter.LengthFilter(6))
                binding.txaSp36E.filters = arrayOf(MinMaxFilter(0.0, sp36), InputFilter.LengthFilter(6))
                binding.txaZaE.filters = arrayOf(MinMaxFilter(0.0, za), InputFilter.LengthFilter(6))
                binding.txaNpkE.filters = arrayOf(MinMaxFilter(0.0, npk), InputFilter.LengthFilter(6))
                binding.txaOrganikE.filters = arrayOf(MinMaxFilter(0.0, organik), InputFilter.LengthFilter(6))

                binding.txaUreaE.setText(urea.toString())
                binding.txaSp36E.setText(sp36.toString())
                binding.txaZaE.setText(za.toString())
                binding.txaNpkE.setText(npk.toString())
                binding.txaOrganikE.setText(organik.toString())
            }
        }
        binding.btTCancelE.setOnClickListener { addFragment(PtDashboardFragment()) }
        binding.btTAddE.setOnClickListener { add() }
    }

    private fun add() {
        val p = ArrayList<String>()
        p.add(binding.txaIdUserE.text.toString())
        p.add(binding.txaSektorE.text.toString())
        p.add(binding.txaLuasE.text.toString())
        p.add(binding.txaUreaE.text.toString())
        p.add(binding.txaSp36E.text.toString())
        p.add(binding.txaZaE.text.toString())
        p.add(binding.txaNpkE.text.toString())
        p.add(binding.txaOrganikE.text.toString())
        p.add(binding.txDateE.text.toString())
        p.add(dateF)
        viewModel.PTUu(p).observeForever {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.status == 1) {
                addFragment(PtDashboardFragment())
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.FramePT, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun roundOfDecimal(number: Double): Double {
        return number.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
    }
}