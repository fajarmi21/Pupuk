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
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getEnd
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getLeft
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getTime
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.reset
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

        binding.bt.setOnClickListener {
            startTimer()
        }

        viewModel = ViewModelProvider(this).get(PtDashboardViewModel::class.java)
        viewModel.ptD(tx_userNamePT.text.toString()).observe(viewLifecycleOwner, Observer {
            try {
                when {
                    it.m3 != null -> {
                        if (it.m3 == "false") {
                            binding.ptU.text = it.tahap
                            binding.ptLL.text = """- /${it.luas_lahan} ha"""
                            binding.ptTanam.text = "-"
                        } else {
                            val getrow = it.m3 as LinkedTreeMap<*, *>
                            binding.ptU.text = it.tahap
                            binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                            binding.ptTanam.text = getrow["sektor"].toString()
                        }
                    }
                    it.m2 != null -> {
                        if (it.m2 == "false") {
                            binding.ptU.text = it.tahap
                            binding.ptLL.text = """- /${it.luas_lahan} ha"""
                            binding.ptTanam.text = "-"
                        } else {
                            val getrow = it.m2 as LinkedTreeMap<*, *>
                            binding.ptU.text = it.tahap
                            binding.ptLL.text = """${getrow["luas"]}/${it.luas_lahan} ha"""
                            binding.ptTanam.text = getrow["sektor"].toString()
                        }
                    }
                    it.m1 != null -> {
                        if (it.m1 == "false") {
                            binding.ptU.text = it.tahap
                            binding.ptLL.text = """- /${it.luas_lahan} ha"""
                            binding.ptTanam.text = "-"
                        } else {
                            val getrow = it.m1 as LinkedTreeMap<*, *>
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
            } catch (e: Exception) {
//                Toast.makeText(context, "Data Kosong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startTimer() {
        if (binding.bt.visibility == View.VISIBLE) binding.bt.visibility = View.GONE
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis
        timer.startCountDown(mTimeLeftInMillis)
        timer.setCountdownListener(object : CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                //TODO Add your code here
            }

            override fun countdownFinished() {
                reset(context)
                bt.visibility = View.VISIBLE
                START_TIME_IN_MILLIS = 10000
                onStart()
            }
        })
        mTimerRunning = true
    }

    override fun onStart() {
        super.onStart()

        mTimeLeftInMillis = getLeft(context, START_TIME_IN_MILLIS)
        mTimerRunning = getTime(context, false)
        if (mTimerRunning) {
            mEndTime = getEnd(context, 0)
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
            Toast.makeText(context, mTimeLeftInMillis.toString(), Toast.LENGTH_SHORT).show()
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0
                mTimerRunning = false
            } else {
                startTimer()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        setTime(context, mTimeLeftInMillis, mTimerRunning, mEndTime)
        timer.pauseCountDownTimer()
    }

    fun timer1() {
        val t : Long = 30000
        binding.timer.startCountDown(t)
        binding.timer.setCountdownListener(object : CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                //TODO Add your code here
            }

            override fun countdownFinished() {
                Log.e("sas", "1")
                Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show()
//                binding.timer.visibility = View.GONE
//                binding.timer2.visibility = View.VISIBLE
////                SaveSharedPreference.setTime(context, true)
//                timer2()
            }
        })
    }
}