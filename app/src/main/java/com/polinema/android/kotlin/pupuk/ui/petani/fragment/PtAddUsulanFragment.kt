package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PtAddUsulanFragmentBinding
import com.polinema.android.kotlin.pupuk.model.Petani
import com.polinema.android.kotlin.pupuk.util.MinMaxFilter
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.PtAddUsulanViewModel
import kotlinx.android.synthetic.main.pt_add_usulan_fragment.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PtAddUsulanFragment : Fragment() {

    private lateinit var viewModel: PtAddUsulanViewModel
    private lateinit var binding: PtAddUsulanFragmentBinding
    var thp = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pt_add_usulan_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PtAddUsulanViewModel::class.java)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.txaIdUser.setText(SaveSharedPreference.getUser(context))
        binding.txDate.text = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

        viewModel.ptS().observe(viewLifecycleOwner, Observer {
            val items = ArrayList<String>()
            it.forEach {
                items.add(it.nama_tanaman)
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
            binding.txaSektor.setAdapter(adapter)
        })

        viewModel.ptD(binding.txaIdUser.text.toString()).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                thp = when(it.tahap) {
                    "m1" -> "m2"
                    "m2" -> "m3"
                    else -> "m1"
                }

                binding.idLuas.helperText = """max : ${it.luas_lahan}/ha"""
                binding.txaLuas.filters = arrayOf(MinMaxFilter(0.0, it.luas_lahan.toDouble()), InputFilter.LengthFilter(6))

                binding.txaLuas.doOnTextChanged { text, _, _, _ ->
                    if (!text.isNullOrEmpty()) {
                        if (text.toString() == "0") {
                            binding.txaUrea.setText("0")
                            binding.txaSp36.setText("0")
                            binding.txaZa.setText("0")
                            binding.txaNpk.setText("0")
                            binding.txaOrganik.setText("0")
                        }

                        val urea = roundOfDecimal(text.toString().toDouble() * 300.00)
                        val sp36 = roundOfDecimal(text.toString().toDouble() * 50.00)
                        val za = roundOfDecimal(text.toString().toDouble() * 200.00)
                        val npk = roundOfDecimal(text.toString().toDouble() * 400.00)
                        val organik = roundOfDecimal(text.toString().toDouble() * 500.00)

                        binding.txaUrea.isEnabled = true
                        binding.txaSp36.isEnabled = true
                        binding.txaZa.isEnabled = true
                        binding.txaNpk.isEnabled = true
                        binding.txaOrganik.isEnabled = true
                        binding.btTAdd.isEnabled = true

                        binding.idUrea.helperText = """max : ${urea}/ha"""
                        binding.idSp36.helperText = """max : ${sp36}/ha"""
                        binding.idZa.helperText = """max : ${za}/ha"""
                        binding.idNpk.helperText = """max : ${npk}/ha"""
                        binding.idOrganik.helperText = """max : ${organik}/ha"""

                        binding.txaUrea.filters = arrayOf(MinMaxFilter(0.0, urea), InputFilter.LengthFilter(6))
                        binding.txaSp36.filters = arrayOf(MinMaxFilter(0.0, sp36), InputFilter.LengthFilter(6))
                        binding.txaZa.filters = arrayOf(MinMaxFilter(0.0, za), InputFilter.LengthFilter(6))
                        binding.txaNpk.filters = arrayOf(MinMaxFilter(0.0, npk), InputFilter.LengthFilter(6))
                        binding.txaOrganik.filters = arrayOf(MinMaxFilter(0.0, organik), InputFilter.LengthFilter(6))
                    } else {
                        reset()
                    }
                }
            }
        })
        binding.btTAdd.setOnClickListener { add() }
    }

    private fun add() {
        val p = ArrayList<String>()
        p.add(binding.txaIdUser.text.toString())
        p.add(binding.txaSektor.text.toString())
        p.add(binding.txaLuas.text.toString())
        p.add(binding.txaUrea.text.toString())
        p.add(binding.txaSp36.text.toString())
        p.add(binding.txaZa.text.toString())
        p.add(binding.txaNpk.text.toString())
        p.add(binding.txaOrganik.text.toString())
        p.add(binding.txDate.text.toString())
        p.add(thp)
//        Log.e("sass", p[1])
        viewModel.ptU(p).observeForever {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            if (it.status == 1) reset()
        }
    }

    private fun reset() {
        binding.txaLuas.clearFocus()
        binding.txaSektor.text.clear()
        binding.txaLuas.text!!.clear()
        binding.txaUrea.text!!.clear()
        binding.txaSp36.text!!.clear()
        binding.txaZa.text!!.clear()
        binding.txaNpk.text!!.clear()
        binding.txaOrganik.text!!.clear()

        binding.txaUrea.isEnabled = false
        binding.txaSp36.isEnabled = false
        binding.txaZa.isEnabled = false
        binding.txaNpk.isEnabled = false
        binding.txaOrganik.isEnabled = false
        binding.btTAdd.isEnabled = false

        binding.idUrea.helperText = null
        binding.idSp36.helperText = null
        binding.idZa.helperText = null
        binding.idNpk.helperText = null
        binding.idOrganik.helperText = null

        binding.txaUrea.filters = arrayOf()
        binding.txaSp36.filters = arrayOf()
        binding.txaZa.filters = arrayOf()
        binding.txaNpk.filters = arrayOf()
        binding.txaOrganik.filters = arrayOf()
    }

    private fun roundOfDecimal(number: Double): Double {
        return number.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
    }
}