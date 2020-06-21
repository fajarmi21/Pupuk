package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import kotlinx.android.synthetic.main.kp_detail_usul_fragment.*

class KpDetailUsulFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.kp_detail_usul_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txaId_userDK.setText(SaveSharedPreference.getUser(context))
        txDateDK.text = arguments!!.get("0").toString()
        txa_sektorDK.setText(arguments!!.get("1").toString())
        txa_luasDK.setText(arguments!!.get("2").toString())
        txa_ureaDK.setText(arguments!!.get("3").toString())
        txa_sp36DK.setText(arguments!!.get("4").toString())
        txa_zaDK.setText(arguments!!.get("5").toString())
        txa_npkDK.setText(arguments!!.get("6").toString())
        txa_organikDK.setText(arguments!!.get("7").toString())
    }
}