package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.asp.fliptimerviewlibrary.CountDownClock
import com.google.gson.internal.LinkedTreeMap
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PtDashboardFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getAdd
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getCode
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getEnd
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getLeft
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getTime
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.reset
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.setAdd
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.setCode
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.setTime
import com.polinema.android.kotlin.pupuk.viewmodel.PtDashboardViewModel
import kotlinx.android.synthetic.main.pt_dashboard_fragment.*
import java.lang.Exception


class PtDashboardFragment : Fragment() {
    private lateinit var binding: PtDashboardFragmentBinding
    private lateinit var viewModel: PtDashboardViewModel

    private var START_TIME_IN_MILLIS: Long = 30000
    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = 0
    private var mEndTime: Long = 0
    private var mCode = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pt_dashboard_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting
        tx_userNamePT.text = getUser(context)
        tx_userNamePT.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel = ViewModelProvider(this).get(PtDashboardViewModel::class.java)
        viewModel.ptD(tx_userNamePT.text.toString()).observe(viewLifecycleOwner, Observer {
            try {
                if (it != null) {
                    when {
                        it.m3 != null -> {
                            val m3 = it.m3 as ArrayList<Any>
                            if (m3.last() == "false") {
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """- /${it.luas_lahan} ha"""
                                binding.ptTanam.text = "-"
                            } else {
                                val getrow = m3.last() as LinkedTreeMap<*, *>
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                                binding.ptTanam.text = getrow["sektor"].toString()
                            }
                        }
                        it.m2 != null -> {
                            val m2 = it.m2 as ArrayList<Any>
                            if (m2.last() == "false") {
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """- /${it.luas_lahan} ha"""
                                binding.ptTanam.text = "-"
                            } else {
                                val getrow = m2.last() as LinkedTreeMap<*, *>
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                                binding.ptTanam.text = getrow["sektor"].toString()
                            }
                        }
                        it.m1 != null -> {
                            val m1 = it.m1 as ArrayList<Any>
                            if (m1.last() == "false") {
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """- /${it.luas_lahan} ha"""
                                binding.ptTanam.text = "-"
                            } else {
                                val getrow = m1.last() as LinkedTreeMap<*, *>
                                binding.ptU.text = it.tahap
                                binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                                binding.ptTanam.text = getrow["sektor"].toString()
                            }
                        }
                    }
                    if (it.status_poktan == null || it.status_ppl == null || it.status_admin == null) binding.ptSt.text =
                            "Diproses"
                    else if (it.status_poktan == "false" || it.status_ppl == "false" || it.status_admin == "false") binding.ptSt.text =
                            "Ditolak"
                    else {
                        binding.ptSt.text = "Diterima"
                    }
                } else {
                    binding.PtPUsul.visibility = View.GONE
                    binding.ptU.text = "KOSONG"
                    binding.ptLL.text = "-"
                    binding.ptTanam.text = "-"
                    binding.ptSt.text = "-"
                }
            } catch (e: Exception) {
//                Toast.makeText(context, "Data Kosong", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnAddUsulanPT.setOnClickListener { addFragment(PtAddUsulanFragment()) }
        binding.btnEditUsulanPT.setOnClickListener { addFragment(PtEditUsulanFragment()) }
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.FramePT, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    private fun startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis
        timer.startCountDown(mTimeLeftInMillis)
        timer.setCountdownListener(object : CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                //TODO Add your code here
            }

            override fun countdownFinished() {
                reset(context)
                when(mCode) {
                    1 -> {
                        START_TIME_IN_MILLIS = 30000
                        setCode(context, 0)
                        setAdd(context, 0)
                    }
                    0 -> {
                        START_TIME_IN_MILLIS = 40000
                        setCode(context, 1)
                        setAdd(context, 2)
                    }
                }
                onStart()
            }
        })
        mTimerRunning = true
    }

    override fun onStart() {
        super.onStart()

        mTimeLeftInMillis = getLeft(context, START_TIME_IN_MILLIS)
        mTimerRunning = getTime(context, false)
        mCode = getCode(context, mCode)
//        Toast.makeText(context, "$mTimerRunning , $mTimeLeftInMillis", Toast.LENGTH_SHORT).show()
        when(mCode) {
            1 -> btnAddUsulanPT.visibility = View.GONE
            0 -> btnAddUsulanPT.visibility = View.VISIBLE
        }

        when {
            getAdd(context) == 1 -> {
                binding.btnAddUsulanPT.visibility = View.GONE
                binding.btnEditUsulanPT.visibility = View.VISIBLE
            }
            getAdd(context) == 0 -> {
                binding.btnAddUsulanPT.visibility = View.VISIBLE
                binding.btnEditUsulanPT.visibility = View.GONE
            }
            else -> {
                binding.btnAddUsulanPT.visibility = View.GONE
                binding.btnEditUsulanPT.visibility = View.GONE
            }
        }

        when {
            mTimerRunning -> {
                mEndTime = getEnd(context, 0)
                mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
                if (mTimeLeftInMillis < 0) {
                    mTimeLeftInMillis = getLeft(context, START_TIME_IN_MILLIS)
                    mTimerRunning = false
                    startTimer()
                } else {
                    startTimer()
                }
            }
            !mTimerRunning && mTimeLeftInMillis == 0L -> {
                reset(context)
                onStart()
            }
            else -> startTimer()
        }
    }

    override fun onStop() {
        super.onStop()
        setTime(context, mTimeLeftInMillis, mTimerRunning, mEndTime, mCode)
        timer.pauseCountDownTimer()
    }
}