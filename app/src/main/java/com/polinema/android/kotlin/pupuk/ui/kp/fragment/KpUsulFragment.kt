package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.github.okdroid.checkablechipview.CheckableChipView
import com.google.gson.internal.LinkedTreeMap
import com.lid.lib.LabelTextView
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.util.DummySwipeRepository
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpUsulViewModel
import kotlinx.android.synthetic.main.kp_usul_footer.*
import kotlinx.android.synthetic.main.kp_usul_fragment.*
import kotlinx.android.synthetic.main.kp_usul_item.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class KpUsulFragment : Fragment() {
    private lateinit var viewModel: KpUsulViewModel
    private val repository = DummySwipeRepository()
    private var show = 1
    private var all = 0
    private lateinit var list : MutableList<UsulanKT>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TypefaceProvider.registerDefaultIconSets()
        return inflater.inflate(R.layout.kp_usul_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(KpUsulViewModel::class.java)
        viewModel.rekap(SaveSharedPreference.getUser(context)).observe(viewLifecycleOwner, Observer {
            try {
                list = it
                rvKPR.layoutManager = LinearLayoutManager(context)
                rvKPR.adapter = RekapKTAdapter(list)
                var total = 0.0
                for (i in it){
                    total += i.luas_lahan.toDouble()
                }
                txTotalRKP.text = total.toString()
            } catch (e: Exception) {
                Log.e("ss", e.message!!)
            }
        })

        var x = 0
        btCheck.setOnClickListener {
            if (all == 1) {
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
            } else {
                textView4.visibility = View.VISIBLE
                btnVARKP.visibility = View.GONE
            }
        }

        btnVARKP.setOnClickListener {
            var id = HashMap<String,String>()
            for (i in 0 until rvKPR.adapter!!.itemCount) {
                val text = rvKPR.findViewHolderForAdapterPosition(i)!!.itemView.findViewById<TextView>(R.id.textView4)
                if (text.text.toString() != "verificated" && text.text.toString() != "can't\n" +
                        "verificated") {
                    id["""id[$i]"""] = (i + 1).toString()
                }
            }
//            rvKPR.findViewHolderForAdapterPosition(1)!!.itemView.findViewById<TextView>(R.id.textView)
            viewModel.verifikasi(id).observe(viewLifecycleOwner, Observer {
                Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                if (it.status == 1) {
                    id.mapValues { it2->
                        rvKPR.findViewHolderForAdapterPosition(it2.value.toInt() - 1)!!.itemView.findViewById<CheckableChipView>(R.id.btnVARKP).visibility = View.GONE
                        rvKPR.findViewHolderForAdapterPosition(it2.value.toInt() - 1)!!.itemView.findViewById<TextView>(R.id.textView4).visibility = View.VISIBLE
                        rvKPR.findViewHolderForAdapterPosition(it2.value.toInt() - 1)!!.itemView.findViewById<TextView>(R.id.textView4).text = "verified"
                        list[it2.value.toInt() - 1].status_poktan = "true"
                    }
                    all = 0
                    btCheck.performClick()
                }
            })
        }
    }


    inner class RekapKTAdapter(private val dataUsulan: List<UsulanKT>) :
        RecyclerView.Adapter<RekapKTAdapter.HolderRekapKT>() {
        inner class HolderRekapKT(iv: View): RecyclerView.ViewHolder(iv) {
            val no = iv.findViewById<TextView>(R.id.textView)
            val nama = iv.findViewById<TextView>(R.id.textView2)
            val luasUsul = iv.findViewById<TextView>(R.id.textView3)
            val stat = iv.findViewById<TextView>(R.id.textView4)
            val btn = iv.findViewById<CheckableChipView>(R.id.btnVARKP)
            val kpr = iv.findViewById<LinearLayout>(R.id.KPr)
            val label = iv.findViewById<LabelTextView>(R.id.labelKPU)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRekapKT {
            return HolderRekapKT(LayoutInflater.from(parent.context).inflate(R.layout.kp_usul_item, parent, false))
        }

        override fun getItemCount(): Int = dataUsulan.size

        @SuppressLint("ResourceType", "RtlHardcoded", "SimpleDateFormat")
        override fun onBindViewHolder(holder: HolderRekapKT, position: Int) {
            holder.btn.text = "Verifikasi"
            holder.no.text = SimpleDateFormat("dd/\nMM/\nyy").format(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dataUsulan[position].timestamp)!!)
            holder.nama.text = dataUsulan[position].nama_petani
            holder.stat.text = dataUsulan[position].luas_lahan
            holder.label.visibility = View.VISIBLE
            var thp = Any()
            var i = 0
            when(dataUsulan[position].tahap) {
                "m1" -> if (dataUsulan[position].m1 != null && dataUsulan[position].m1 != "false") thp = dataUsulan[position].m1  as ArrayList<*>
                "m2" -> if (dataUsulan[position].m2 != null && dataUsulan[position].m2 != "false") thp = dataUsulan[position].m2  as ArrayList<*>
                "m3" -> if (dataUsulan[position].m3 != null && dataUsulan[position].m3 != "false") thp = dataUsulan[position].m3  as ArrayList<*>
            }
            if (thp is ArrayList<*>) {
                val getrow = thp.last() as LinkedTreeMap<*, *>
                holder.luasUsul.text = getrow["sektor"].toString()
                all = 1
            } else {
                holder.luasUsul.text = "-"
                holder.stat.text = "can't\nverified"
                if(all == 0) all = 0
            }

            if (dataUsulan[position].status_poktan != null) {
                val sp = dataUsulan[position].status_poktan as ArrayList<ArrayList<String>>
                sp.forEach {
                   if (it.containsAll(listOf(dataUsulan[position].tahap, LocalDate.now().year.toString()))) {
                       holder.stat.text = "verified"
                       holder.label.visibility = View.GONE
                       all = 0
                   }
                }
            }

            if (holder.stat.text != "verified" && holder.stat.text != "can't\nverified") {
                if (show == 0) {
                    holder.stat.visibility = View.GONE
                    holder.btn.visibility = View.VISIBLE
                } else {
                    holder.stat.visibility = View.VISIBLE
                    holder.btn.visibility = View.GONE
                }
            }

            holder.btn.setOnClickListener {
                var id = HashMap<String,String>()
                id["id[0]"] = (position + 1).toString()
                viewModel.verifikasi(id).observe(viewLifecycleOwner, Observer {
                    Toast.makeText(this@KpUsulFragment.context, it.message, Toast.LENGTH_SHORT).show()
                    if (it.status == 1) {
                        dataUsulan[position].status_poktan = "true"
                        holder.stat.text = "verified"
                        holder.stat.visibility = View.VISIBLE
                        holder.btn.visibility = View.GONE
                        notifyItemChanged(position)
                        if (dataUsulan.filter { s -> s.status_poktan != "true" && s.m1 != "false" }.count() == 0) {
                            all = 0
                            btCheck.performClick()
                        }
                    }
//                    Log.e("count", dataUsulan.filter { s -> s.status_poktan != "true" && s.m1 != "false" }.count().toString())
                })
            }
            holder.kpr.setOnClickListener {
                if (thp is ArrayList<*>) {
                    val get = thp.last() as LinkedTreeMap<*, *>
                    val f = KpDetailUsulFragment()
                    val b = Bundle()
                    b.putString("0", get["date"].toString())
                    b.putString("1", get["sektor"].toString())
                    b.putString("2", get["luas"].toString())
                    b.putString("3", get["urea"].toString())
                    b.putString("4", get["sp36"].toString())
                    b.putString("5", get["za"].toString())
                    b.putString("6", get["npk"].toString())
                    b.putString("7", get["organik"].toString())
                    f.arguments = b
                    addFragment(f)
                }
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.FrameKP, fragment, fragment.javaClass.simpleName)
            .addToBackStack("KpRekapFragment")
            .commit()
    }
}