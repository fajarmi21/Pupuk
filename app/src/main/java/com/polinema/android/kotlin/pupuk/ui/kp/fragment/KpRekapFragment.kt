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
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.ui.petani.fragment.PtDetailRekapFragment
import com.polinema.android.kotlin.pupuk.util.DummySwipeRepository
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpRekapViewModel
import kotlinx.android.synthetic.main.kp_petani_fragment.*
import kotlinx.android.synthetic.main.kp_rekap_fragment.*
import kotlinx.android.synthetic.main.kp_rekap_item.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import java.lang.Exception
import java.text.SimpleDateFormat


class KpRekapFragment : Fragment() {
    private lateinit var viewModel: KpRekapViewModel
    private val repository = DummySwipeRepository()
    private var show = 1
    private var all = 0
    private lateinit var list : MutableList<UsulanKT>

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
            try {
                Log.e("ss", it.toString())
                list = it
                rvKPR.layoutManager = LinearLayoutManager(context)
                rvKPR.adapter = RekapKTAdapter(list)
            } catch (e: Exception) {

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
                        rvKPR.findViewHolderForAdapterPosition(it2.value.toInt() - 1)!!.itemView.findViewById<TextView>(R.id.textView4).text = "verificated"
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
            holder.stat.text = dataUsulan[position].luas_lahan
            var thp = Any()
            var i = 0
            when(dataUsulan[position].tahap) {
                "m1" -> if (dataUsulan[position].m1 != null && dataUsulan[position].m1 != "false") thp = dataUsulan[position].m1  as ArrayList<*>
                "m2" -> if (dataUsulan[position].m2 != null && dataUsulan[position].m2 != "false") thp = dataUsulan[position].m2  as ArrayList<*>
                "m3" -> if (dataUsulan[position].m3 != null && dataUsulan[position].m3 != "false") thp = dataUsulan[position].m3  as ArrayList<*>
            }
            if (thp is ArrayList<*>) {
                val getrow = thp.last() as LinkedTreeMap<*, *>
                i = thp.lastIndex - 1
                holder.luasUsul.text = getrow["sektor"].toString()
                all = 1
            } else {
                holder.luasUsul.text = "-"
                holder.stat.text = "can't\nverificated"
                if(all == 0) all = 0
            }

            val sp = dataUsulan[position].status_poktan as ArrayList<String>
            if (sp[i] != "true" && holder.stat.text != "can't\nverificated") {
                if (show == 0) {
                    holder.stat.visibility = View.GONE
                    holder.btn.visibility = View.VISIBLE
                } else {
                    holder.stat.visibility = View.VISIBLE
                    holder.btn.visibility = View.GONE
                }
            }
            else if (sp[i] == "true" && holder.stat.text != "can't\nverificated") {
                holder.stat.text = "verificated"
                all = 0
            }

            holder.btn.setOnClickListener {
                var id = HashMap<String,String>()
                id["id[0]"] = (position + 1).toString()
                viewModel.verifikasi(id).observe(viewLifecycleOwner, Observer {
                    Toast.makeText(this@KpRekapFragment.context, it.message, Toast.LENGTH_SHORT).show()
                    if (it.status == 1) {
                        dataUsulan[position].status_poktan = "true"
                        holder.stat.text = "verificated"
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
                    val f = KpDetailRekapFragment()
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