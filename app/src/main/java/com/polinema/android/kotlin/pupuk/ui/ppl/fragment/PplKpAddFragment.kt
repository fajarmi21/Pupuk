package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import com.polinema.android.kotlin.pupuk.viewmodel.PplKpAddViewModel
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PplKpAddFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import kotlinx.android.synthetic.main.ppl_kp_add_fragment.*

class PplKpAddFragment : Fragment() {

    private lateinit var viewModel: PplKpAddViewModel
    private lateinit var binding: PplKpAddFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.ppl_kp_add_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PplKpAddViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add_button_detailPPL.setOnClickListener {
            viewModel.pplKp(SaveSharedPreference.getUser(context)).observe(viewLifecycleOwner, Observer {
                Log.e("sukses", it.message)
                if (it.status == 1) {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.FramePPL, PplKpFragment(), PplKpFragment().javaClass.simpleName)
                        .commit()
                }
                else { Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show() }
            })
        }
        cancel_button_detailPPL.setOnClickListener { activity!!.supportFragmentManager.popBackStack() }
    }

}