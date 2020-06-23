package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polinema.android.kotlin.pupuk.viewmodel.PplUsulDetailViewModel
import com.polinema.android.kotlin.pupuk.R

class PplUsulDetailFragment : Fragment() {



    private lateinit var viewModel: PplUsulDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ppl_usul_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PplUsulDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}