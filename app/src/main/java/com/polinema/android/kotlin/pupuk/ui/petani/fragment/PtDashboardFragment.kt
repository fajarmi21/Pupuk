package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.PtDashboardViewModel
import kotlinx.android.synthetic.main.pt_dashboard_fragment.*

class PtDashboardFragment : Fragment() {
    private lateinit var viewModel: PtDashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pt_dashboard_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setting
        tx_userNamePT.text = SaveSharedPreference.getUser(context)
        tx_userNamePT.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel = ViewModelProvider(this).get(PtDashboardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}