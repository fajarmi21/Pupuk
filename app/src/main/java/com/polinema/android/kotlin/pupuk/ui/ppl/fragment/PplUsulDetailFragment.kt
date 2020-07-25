package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.LinkedTreeMap
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PplUsulDetailFragmentBinding
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.viewmodel.KpUsulViewModel
import java.text.SimpleDateFormat

class PplUsulDetailFragment : Fragment() {
    private lateinit var binding: PplUsulDetailFragmentBinding
    private lateinit var viewModel: KpUsulViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.ppl_usul_detail_fragment, container, false)
        viewModel = ViewModelProvider(this).get(KpUsulViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.txaIdPoktan.setText(arguments?.get("poktan").toString())
        init()
    }

    private fun init() {
        viewModel.rekap(arguments?.get("poktan").toString()).observeForever {
            try {
                binding.rvPPLVD.layoutManager = LinearLayoutManager(context)
                val adapter = DetailAdapter(it)
                binding.rvPPLVD.adapter = adapter
            } catch (e: Exception) {
                Log.e("ss", e.message!!)
            }
        }
    }

    inner class DetailAdapter (val dataPrc:List<UsulanKT>):
            RecyclerView.Adapter<DetailAdapter.HolderPrcA>(){
        inner class HolderPrcA(iv: View): RecyclerView.ViewHolder(iv){
            val no = iv.findViewById<TextView>(R.id.DDetailnoUsulPpl)
            val petani = iv.findViewById<TextView>(R.id.DDetailUsulPplNama)
            val total = iv.findViewById<TextView>(R.id.DDetailUsulPplTotal)
            val luas = iv.findViewById<TextView>(R.id.DDetailUsulPplLuas)
            val status = iv.findViewById<TextView>(R.id.txStatusPPLD)
            val cv = iv.findViewById<CardView>(R.id.UsulPplItemD)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.HolderPrcA {
            return HolderPrcA(LayoutInflater.from(parent.context).inflate(R.layout.item_ppl_usul_detail_fragment, parent, false))
        }

        override fun getItemCount(): Int = dataPrc.size

        override fun onBindViewHolder(holder: DetailAdapter.HolderPrcA, position: Int) {
            val p = dataPrc[position]
            holder.no.text = (position + 1).toString()
            holder.petani.text = p.nama_petani
            var thp = Any()
            when(p.tahap) {
                "m1" -> if (p.m1 != null && p.m1 != "false") thp = p.m1  as ArrayList<*>
                "m2" -> if (p.m2 != null && p.m2 != "false") thp = p.m2  as ArrayList<*>
                "m3" -> if (p.m3 != null && p.m3 != "false") thp = p.m3  as ArrayList<*>
            }
            if (thp is ArrayList<*>) {
                val getrow = thp.last() as LinkedTreeMap<*, *>
                holder.luas.text = getrow["luas"].toString()
                holder.total.text =  String.format("%.2f",
                    (
                        getrow["urea"].toString().toDouble() +
                        getrow["sp36"].toString().toDouble() +
                        getrow["za"].toString().toDouble() +
                        getrow["npk"].toString().toDouble() +
                        getrow["organik"].toString().toDouble()
                    )
                ).replace(",", ".")
            }

            when {
                p.status_admin != null -> holder.status.text = "Diterima ADMIN"
                p.status_ppl == null && p.status_poktan == null -> holder.status.text = "Diproses"
                p.status_ppl == null && p.status_poktan.status == "true" -> holder.status.text = "Diterima KP"
                p.status_ppl == null && p.status_poktan.status == "false" -> holder.status.text = "Ditolak KP"
                p.status_ppl.status == "true" -> holder.status.text = "Diterima PPL"
                p.status_ppl.status == "false" -> holder.status.text = "Ditolak PPL"
            }

            holder.cv.setOnClickListener {
                if (thp is ArrayList<*>) {
                    val get = thp.last() as LinkedTreeMap<*, *>
                    val f = PplUsulDetailDFragment()
                    val b = Bundle()
                    b.putString("0", get["date"].toString())
                    b.putString("1", get["sektor"].toString())
                    b.putString("2", get["luas"].toString())
                    b.putString("3", get["urea"].toString())
                    b.putString("4", get["sp36"].toString())
                    b.putString("5", get["za"].toString())
                    b.putString("6", get["npk"].toString())
                    b.putString("7", get["organik"].toString())
                    b.putString("8", holder.petani.text.toString())
                    f.arguments = b
                    addFragment(f)
                }
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.FramePPL, fragment, fragment.javaClass.simpleName)
                .addToBackStack("PplUsulDetailFragment")
                .commit()
    }
}