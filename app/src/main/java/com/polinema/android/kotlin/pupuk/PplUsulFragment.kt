package com.polinema.android.kotlin.pupuk

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PplUsulFragment : Fragment() {

    companion object {
        fun newInstance() = PplUsulFragment()
    }

    private lateinit var viewModel: PplUsulViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ppl_usul_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PplUsulViewModel::class.java)
        // TODO: Use the ViewModel
    }

}