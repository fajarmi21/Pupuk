package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpRekapViewModel
import kotlinx.android.synthetic.main.kp_rekap_fragment.*
import nz.co.trademe.covert.Covert
import java.text.SimpleDateFormat

class KpRekapFragment : Fragment() {
    private lateinit var viewModel: KpRekapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.kp_rekap_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(KpRekapViewModel::class.java)

        val covertConfig = Covert.Config(
                iconRes = R.drawable.ic_star_border_black_24dp, // The icon to show
                iconDefaultColorRes = R.color.colorBlack,            // The color of the icon
                actionColorRes = R.color.colorPrimary           // The color of the background
        )
        val covert = Covert.with(covertConfig)
//                .setIsActiveCallback {
//                    // This is a callback to check if the item is active, i.e checked
//                    Log.e("sa", it.adapterPosition.toString())
//                }
//                .doOnSwipe { viewHolder, _ ->
//                    // This callback is fired when a ViewHolder is swiped
//                    repository.toggleActiveState(viewHolder.adapterPosition)
//                }
                .attachTo(rvKPR)
        viewModel.rekap(SaveSharedPreference.getUser(context)).observe(viewLifecycleOwner, Observer {
            rvKPR.layoutManager = LinearLayoutManager(this.context)
            val adapter = RekapKTAdapter(covert, it)
            rvKPR.adapter = adapter
        })
    }

    inner class RekapKTAdapter(private val covert: Covert, private val dataUsulan: List<UsulanKT>) :
        RecyclerView.Adapter<RekapKTAdapter.HolderUserKT>() {
        inner class HolderUserKT(iv: View): RecyclerView.ViewHolder(iv) {
            val no = iv.findViewById<TextView>(R.id.textView)
            val nama = iv.findViewById<TextView>(R.id.textView2)
            val alamat = iv.findViewById<TextView>(R.id.textView3)
            val luas = iv.findViewById<TextView>(R.id.textView4)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderUserKT {
            return HolderUserKT(LayoutInflater.from(parent.context).inflate(R.layout.kp_petani_item, parent, false))
        }

        override fun getItemCount(): Int = dataUsulan.size

        @SuppressLint("ResourceType", "RtlHardcoded", "SimpleDateFormat")
        override fun onBindViewHolder(holder: HolderUserKT, position: Int) {
            covert.drawCornerFlag(holder)
            var x = ""
            if ((dataUsulan.size - position) < 10) x = """0${dataUsulan.size - position}"""
            else { x = (dataUsulan.size - position).toString() }
            holder.no.text = SimpleDateFormat("dd/\nMM/\nyy").format(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dataUsulan[position].timestamp)!!)
            holder.nama.text = dataUsulan[position].nama_petani
            holder.alamat.text = dataUsulan[position].luas_lahan
            holder.luas.text = dataUsulan[position].status_poktan
        }
    }
}