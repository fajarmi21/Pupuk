package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.github.okdroid.checkablechipview.CheckableChipView
import com.google.gson.internal.LinkedTreeMap
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.util.DummySwipeRepository
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpRekapViewModel
import kotlinx.android.synthetic.main.kp_rekap_fragment.*
import kotlinx.android.synthetic.main.kp_rekap_item.*
import java.text.SimpleDateFormat


class KpRekapFragment : Fragment() {
    private lateinit var viewModel: KpRekapViewModel
    private val repository = DummySwipeRepository()
    private var show = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TypefaceProvider.registerDefaultIconSets()
        return inflater.inflate(R.layout.kp_rekap_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(KpRekapViewModel::class.java)
        viewModel.rekap(SaveSharedPreference.getUser(context)).observe(viewLifecycleOwner, Observer {
            rvKPR.layoutManager = LinearLayoutManager(context)
            rvKPR.adapter = RekapKTAdapter(it)
        })

        var x = 0
        btCheck.setOnClickListener {
            if (x == 0) {
                textView4.visibility = View.GONE
                btnVARKP.visibility = View.VISIBLE
                x = 1
                show = 0
                rvKPR.adapter!!.notifyDataSetChanged()
            }
            else {
                textView4.visibility = View.VISIBLE
                btnVARKP.visibility = View.GONE
                x = 0
                show = 1
                rvKPR.adapter!!.notifyDataSetChanged()
            }
        }
        btnVARKP.setOnClickListener {
            viewModel.verifikasi(SaveSharedPreference.getUser(context)).observe(viewLifecycleOwner, Observer {

            })
        }
    }

    inner class RekapKTAdapter(private val dataUsulan: List<UsulanKT>) :
        RecyclerView.Adapter<RekapKTAdapter.HolderRekapKT>() {
        inner class HolderRekapKT(iv: View): RecyclerView.ViewHolder(iv) {
            val no = iv.findViewById<TextView>(R.id.textView)
            val nama = iv.findViewById<TextView>(R.id.textView2)
            val alamat = iv.findViewById<TextView>(R.id.textView3)
            val luas = iv.findViewById<TextView>(R.id.textView4)
            val btn = iv.findViewById<CheckableChipView>(R.id.btnVARKP)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRekapKT {
            return HolderRekapKT(LayoutInflater.from(parent.context).inflate(R.layout.kp_rekap_item, parent, false))
        }

        override fun getItemCount(): Int = dataUsulan.size

        @SuppressLint("ResourceType", "RtlHardcoded", "SimpleDateFormat")
        override fun onBindViewHolder(holder: HolderRekapKT, position: Int) {
            holder.btn.text = "Verifikasi"
            holder.no.text = SimpleDateFormat("dd/\nMM/\nyy").format(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dataUsulan[position].timestamp)!!)
            holder.nama.text = dataUsulan[position].nama_petani
            holder.alamat.text = dataUsulan[position].luas_lahan
            if (dataUsulan[position].m1 != null && dataUsulan[position].m1 != "false") {
                val getrow = dataUsulan[position].m1 as LinkedTreeMap<*, *>
                holder.luas.text = getrow["sd"].toString()
                if (show == 0) {
                    holder.luas.visibility = View.GONE
                    holder.btn.visibility = View.VISIBLE
                } else {
                    holder.luas.visibility = View.VISIBLE
                    holder.btn.visibility = View.GONE
                }
            } else {
                holder.luas.text = ""
            }
        }


    }
}