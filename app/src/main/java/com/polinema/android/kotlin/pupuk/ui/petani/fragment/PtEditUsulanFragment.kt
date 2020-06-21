package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PtAddUsulanFragmentBinding
import com.polinema.android.kotlin.pupuk.databinding.PtEditUsulanFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.viewmodel.PtAddUsulanViewModel
import com.polinema.android.kotlin.pupuk.viewmodel.PtEditUsulanViewModel

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
            binding.txaIdUserE.setText(getUser(context))
            binding.txDateE.text = it.date
            binding.txaSektorE.setText(it.sektor)
            binding.txaLuasE.setText(it.luas)
            binding.txaUreaE.setText(it.urea)
            binding.txaSp36E.setText(it.sp36)
            binding.txaZaE.setText(it.za)
            binding.txaNpkE.setText(it.npk)
            binding.txaOrganikE.setText(it.organik)
            dateF = it.date
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

}