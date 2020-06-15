package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.PtDetailRekapViewModel
import kotlinx.android.synthetic.main.pt_detail_rekap_fragment.*

class PtDetailRekapFragment : Fragment() {
    private lateinit var viewModel: PtDetailRekapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pt_detail_rekap_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PtDetailRekapViewModel::class.java)
        txaId_userR.setText(SaveSharedPreference.getUser(context))
        txDateR.text = arguments!!.get("0").toString()
        txa_sektorR.setText(arguments!!.get("1").toString())
        txa_luasR.setText(arguments!!.get("2").toString())
        txa_ureaR.setText(arguments!!.get("3").toString())
        txa_sp36R.setText(arguments!!.get("4").toString())
        txa_zaR.setText(arguments!!.get("5").toString())
        txa_npkR.setText(arguments!!.get("6").toString())
        txa_organikR.setText(arguments!!.get("7").toString())
    }

}