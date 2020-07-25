package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import kotlinx.android.synthetic.main.ppl_usul_detail_d_fragment.*

class PplUsulDetailDFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ppl_usul_detail_d_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txaId_userDPp.setText(arguments!!.get("8").toString())
        txDateDPp.text = arguments!!.get("0").toString()
        txa_sektorDPp.setText(arguments!!.get("1").toString())
        txa_luasDPp.setText(arguments!!.get("2").toString())
        txa_ureaDPp.setText(arguments!!.get("3").toString())
        txa_sp36DPp.setText(arguments!!.get("4").toString())
        txa_zaDPp.setText(arguments!!.get("5").toString())
        txa_npkDPp.setText(arguments!!.get("6").toString())
        txa_organikDPp.setText(arguments!!.get("7").toString())
    }
}