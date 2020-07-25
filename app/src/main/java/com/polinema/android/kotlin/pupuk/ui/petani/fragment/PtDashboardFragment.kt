package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import android.annotation.SuppressLint
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
import com.google.gson.internal.LinkedTreeMap
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PtDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.viewmodel.PtDashboardViewModel
import kotlinx.android.synthetic.main.pt_dashboard_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit


class PtDashboardFragment : Fragment() {
    private lateinit var binding: PtDashboardFragmentBinding
    private lateinit var viewModel: PtDashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pt_dashboard_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PtDashboardViewModel::class.java)
        binding.lifecycleOwner = this
        binding.user = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting
        tx_userNamePT.text = getUser(context)
        tx_userNamePT.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel.ptD(tx_userNamePT.text.toString()).observe(viewLifecycleOwner, Observer {
            try {
                if (it.tahap != null) {
                    binding.PtPUsul.visibility = View.VISIBLE
                    when {
                        it.m3 != null -> {
                            if (it.m3.last() == "false") {
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """- /${it.luas_lahan} ha"""
                                binding.ptTanam.text = "-"
                                binding.PtPUsul.visibility = View.GONE
                            } else {
                                val getrow = it.m3.last() as LinkedTreeMap<*, *>
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                                binding.ptTanam.text = getrow["sektor"].toString()

                                binding.ptdluas.text = getrow["luas"].toString()
                                binding.ptdurea.text = String.format("%.2f", getrow["urea"].toString().toDouble()).replace(",", ".")
                                binding.ptdsp36.text = String.format("%.2f", getrow["sp36"].toString().toDouble()).replace(",", ".")
                                binding.ptdza.text = String.format("%.2f", getrow["za"].toString().toDouble()).replace(",", ".")
                                binding.ptdnpk.text = String.format("%.2f", getrow["npk"].toString().toDouble()).replace(",", ".")
                                binding.ptdorganik.text = String.format("%.2f", getrow["organik"].toString().toDouble()).replace(",", ".")
                                binding.ptdtotal.text = String.format("%.2f", (getrow["urea"].toString().toDouble()
                                        + getrow["sp36"].toString().toDouble()
                                        + getrow["za"].toString().toDouble()
                                        + getrow["npk"].toString().toDouble()
                                        + getrow["organik"].toString().toDouble())).replace(",", ".")
                            }
                        }
                        it.m2 != null -> {
                            if (it.m2.last() == "false") {
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """- /${it.luas_lahan} ha"""
                                binding.ptTanam.text = "-"
                                binding.PtPUsul.visibility = View.GONE
                            } else {
                                val getrow = it.m2.last() as LinkedTreeMap<*, *>
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                                binding.ptTanam.text = getrow["sektor"].toString()

                                binding.ptdluas.text = getrow["luas"].toString()
                                binding.ptdurea.text = String.format("%.2f", getrow["urea"].toString().toDouble()).replace(",", ".")
                                binding.ptdsp36.text = String.format("%.2f", getrow["sp36"].toString().toDouble()).replace(",", ".")
                                binding.ptdza.text = String.format("%.2f", getrow["za"].toString().toDouble()).replace(",", ".")
                                binding.ptdnpk.text = String.format("%.2f", getrow["npk"].toString().toDouble()).replace(",", ".")
                                binding.ptdorganik.text = String.format("%.2f", getrow["organik"].toString().toDouble()).replace(",", ".")
                                binding.ptdtotal.text = String.format("%.2f", (getrow["urea"].toString().toDouble()
                                        + getrow["sp36"].toString().toDouble()
                                        + getrow["za"].toString().toDouble()
                                        + getrow["npk"].toString().toDouble()
                                        + getrow["organik"].toString().toDouble())).replace(",", ".")
                            }
                        }
                        it.m1 != null -> {
                            if (it.m1.last() == "false") {
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """- /${it.luas_lahan} ha"""
                                binding.ptTanam.text = "-"
                                binding.PtPUsul.visibility = View.GONE
                            } else {
                                val getrow = it.m1.last() as LinkedTreeMap<*,*>
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                                binding.ptTanam.text = getrow["sektor"].toString()

                                binding.ptdluas.text = getrow["luas"].toString()
                                binding.ptdurea.text = String.format("%.2f", getrow["urea"].toString().toDouble()).replace(",", ".")
                                binding.ptdsp36.text = String.format("%.2f", getrow["sp36"].toString().toDouble()).replace(",", ".")
                                binding.ptdza.text = String.format("%.2f", getrow["za"].toString().toDouble()).replace(",", ".")
                                binding.ptdnpk.text = String.format("%.2f", getrow["npk"].toString().toDouble()).replace(",", ".")
                                binding.ptdorganik.text = String.format("%.2f", getrow["organik"].toString().toDouble()).replace(",", ".")
                                binding.ptdtotal.text = String.format("%.2f", (getrow["urea"].toString().toDouble()
                                        + getrow["sp36"].toString().toDouble()
                                        + getrow["za"].toString().toDouble()
                                        + getrow["npk"].toString().toDouble()
                                        + getrow["organik"].toString().toDouble())).replace(",", ".")
                            }
                        }
                    }

                    if (binding.ptU.text != "KOSONG") {
                        when {
                            it.status_admin != null -> {
                                binding.ptSt.text = "Diterima"
                                binding.PtPDiterima.visibility = View.VISIBLE
                                binding.pttluas.text = binding.ptdluas.text
                                binding.ptturea.text = String.format("%.2f", it.status_admin.verifikasi.urea).replace(",", ".")
                                binding.pttsp36.text = String.format("%.2f", it.status_admin.verifikasi.sp36).replace(",", ".")
                                binding.pttza.text = String.format("%.2f", it.status_admin.verifikasi.za).replace(",", ".")
                                binding.pttnpk.text = String.format("%.2f", it.status_admin.verifikasi.npk).replace(",", ".")
                                binding.pttorganik.text = String.format("%.2f", it.status_admin.verifikasi.organik).replace(",", ".")
                                binding.ptttotal.text = String.format("%.2f", (binding.ptturea.text.toString().toDouble() +
                                        binding.pttsp36.text.toString().toDouble() +
                                        binding.pttza.text.toString().toDouble() +
                                        binding.pttnpk.text.toString().toDouble() +
                                        binding.pttorganik.text.toString().toDouble())).replace(",", ".")
                            }
                            it.status_ppl != null -> {
                                if (!it.status_ppl.status) {
                                    binding.ptSt.text = "Ditolak PPL"
                                    binding.ptStA.text = it.status_ppl.alasan
                                    binding.PtPDiterima.visibility = View.GONE
                                    binding.ptA.visibility = View.VISIBLE
                                } else {
                                    binding.ptSt.text = "Diterima PPL"
                                    binding.PtPDiterima.visibility = View.GONE
                                }
                            }
                            it.status_poktan != null -> {
                                if (!it.status_poktan.status) {
                                    binding.ptSt.text = "Ditolak KP"
                                    binding.ptStA.text = it.status_poktan.alasan
                                    binding.PtPDiterima.visibility = View.GONE
                                    binding.ptA.visibility = View.VISIBLE
                                } else {
                                    binding.ptSt.text = "Diterima KP"
                                    binding.PtPDiterima.visibility = View.GONE
                                }
                            }
                            else -> {
                                binding.ptSt.text = "Diproses"
                                binding.PtPDiterima.visibility = View.GONE
                            }
                        }
                    }
                } else {
                    binding.PtPUsul.visibility = View.GONE
                    binding.ptU.text = "KOSONG"
                    binding.ptLL.text = "-"
                    binding.ptTanam.text = "-"
                    binding.ptSt.text = "-"
                }

                viewModel.ptT().observeForever {
                    it.forEach {
                        val fmt =
                                DateTimeFormatterBuilder() // month-year
                                        .appendPattern("MM") // default value for day
                                        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1) // create formatter
                                        .parseDefaulting(ChronoField.YEAR, LocalDate.now().year.toLong())
                                        .toFormatter()
                        val dt = ChronoUnit.DAYS.between(LocalDate.parse(it.bulan, fmt), LocalDate.now())
                        if (LocalDate.now().isBefore(LocalDate.parse(it.bulan, fmt).plusDays(25)) && LocalDate.now().isAfter(LocalDate.parse(it.bulan, fmt))) {
                            if (binding.ptSt.text == "Diterima") {
                                binding.btnAddUsulanPT.visibility = View.GONE
                                binding.btnEditUsulanPT.visibility = View.GONE
                            } else if (it.tahap == binding.ptU.text.toString()) {
                                binding.btnAddUsulanPT.visibility = View.GONE
                                binding.btnEditUsulanPT.visibility = View.VISIBLE
                                binding.btnEditUsulanPT.setOnClickListener { addFragment(PtEditUsulanFragment()) }
                            }
                            else{
                                binding.btnEditUsulanPT.visibility = View.GONE
                                binding.btnAddUsulanPT.visibility = View.VISIBLE

                                val addFrag = PtAddUsulanFragment()
                                val mBundle = Bundle()
                                mBundle.putString("tahap", it.tahap)
                                addFrag.arguments = mBundle
                                binding.btnAddUsulanPT.setOnClickListener { addFragment(addFrag) }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("e", e.message!!)
            }
        })
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.FramePT, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}