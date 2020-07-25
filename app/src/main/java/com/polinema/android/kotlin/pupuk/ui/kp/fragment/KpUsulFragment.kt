package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.github.okdroid.checkablechipview.CheckableChipView
import com.google.gson.internal.LinkedTreeMap
import com.lid.lib.LabelTextView
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.Sp
import com.polinema.android.kotlin.pupuk.model.UsulanKT
import com.polinema.android.kotlin.pupuk.util.DummySwipeRepository
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.KpUsulViewModel
import kotlinx.android.synthetic.main.kp_usul_fragment.*
import kotlinx.android.synthetic.main.kp_usul_header.*
import java.text.SimpleDateFormat
import java.time.LocalDate


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
        Log.e("xx", SaveSharedPreference.getUser(context))
        viewModel.rekap(SaveSharedPreference.getUser(context)).observe(viewLifecycleOwner, Observer {
            try {
                list = it
                rvKPR.layoutManager = LinearLayoutManager(context)
                rvKPR.adapter = RekapKTAdapter(list)
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
                if (text.text.toString() != "Diterima" && text.text.toString() != "Ditolak") {
//                    Log.e("idxx", list[i].id_petani)
                    id["""id[$i]"""] = list[i].id_petani
                }
            }
//            Log.e("id", id.toString())
//            rvKPR.findViewHolderForAdapterPosition(1)!!.itemView.findViewById<TextView>(R.id.textView)
            viewModel.verifikasi(id).observe(viewLifecycleOwner, Observer {
                Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                if (it.status == 1) {
                    var x = 0
                    id.mapValues {
                        rvKPR.findViewHolderForAdapterPosition(x)!!.itemView.findViewById<LinearLayout>(R.id.btnGVARKP).visibility = View.GONE
                        rvKPR.findViewHolderForAdapterPosition(x)!!.itemView.findViewById<TextView>(R.id.labelKPU).visibility = View.GONE
                        rvKPR.findViewHolderForAdapterPosition(x)!!.itemView.findViewById<TextView>(R.id.textView4).visibility = View.VISIBLE
                        rvKPR.findViewHolderForAdapterPosition(x)!!.itemView.findViewById<TextView>(R.id.textView4).text = "Diterima"
                        x += 1
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
            val btnG = iv.findViewById<LinearLayout>(R.id.btnGVARKP)
            val btn = iv.findViewById<BootstrapButton>(R.id.btnVARKP)
            val btnt = iv.findViewById<BootstrapButton>(R.id.btntVARKP)
            val kpr = iv.findViewById<LinearLayout>(R.id.KPr)
            val label = iv.findViewById<LabelTextView>(R.id.labelKPU)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRekapKT {
            return HolderRekapKT(LayoutInflater.from(parent.context).inflate(R.layout.kp_usul_item, parent, false))
        }

        override fun getItemCount(): Int = dataUsulan.size

        @SuppressLint("ResourceType", "RtlHardcoded", "SimpleDateFormat")
        override fun onBindViewHolder(holder: HolderRekapKT, position: Int) {
            holder.nama.text = dataUsulan[position].nama_petani
            holder.stat.text = dataUsulan[position].luas_lahan
            holder.label.visibility = View.VISIBLE
            var thp = Any()
            when(dataUsulan[position].tahap) {
                "m1" -> if (dataUsulan[position].m1 != null && dataUsulan[position].m1 != "false") thp = dataUsulan[position].m1 as ArrayList<*>
                "m2" -> if (dataUsulan[position].m2 != null && dataUsulan[position].m2 != "false") thp = dataUsulan[position].m2  as ArrayList<*>
                "m3" -> if (dataUsulan[position].m3 != null && dataUsulan[position].m3 != "false") thp = dataUsulan[position].m3  as ArrayList<*>
            }
            if (thp is ArrayList<*>) {
                val getrow = thp.last() as LinkedTreeMap<*, *>
                holder.luasUsul.text = getrow["sektor"].toString()
                holder.no.text = SimpleDateFormat("dd/\nMM/\nyy").format(SimpleDateFormat("dd-MM-yyyy").parse(getrow["date"].toString())!!)
                all = 1
            } else {
                holder.luasUsul.text = "-"
                holder.stat.text = "Ditolak"
                holder.label.visibility = View.GONE
                if(all == 0) all = 0
            }

            if (dataUsulan[position].status_poktan != null) {
                when {
                    dataUsulan[position].status_poktan.status == "true" -> {
                        holder.stat.text = "Diterima"
                        holder.label.visibility = View.GONE
                    }
                    dataUsulan[position].status_poktan.status == "false" -> {
                        holder.stat.text = "Ditolak"
                        holder.label.visibility = View.GONE
                    }
                }
            }
            if (dataUsulan.filter { s -> s.status_poktan == null }.count() == 0) {
                all = 0
            }

            if (holder.stat.text != "Diterima" && holder.stat.text != "Ditolak") {
                if (show == 0) {
                    holder.stat.visibility = View.GONE
                    holder.btnG.visibility = View.VISIBLE
                } else {
                    holder.stat.visibility = View.VISIBLE
                    holder.btnG.visibility = View.GONE
                }
            }

            holder.btn.setOnClickListener {
                var id = HashMap<String,String>()
                id["id[0]"] = dataUsulan[position].id_petani
                Log.e("id", id.toString())
                viewModel.verifikasi(id).observe(viewLifecycleOwner, Observer {
                    Toast.makeText(this@KpUsulFragment.context, it.message, Toast.LENGTH_SHORT).show()
                    if (it.status == 1) {
                        dataUsulan[position].status_poktan = Sp("true", "","","")
                        holder.stat.visibility = View.VISIBLE
                        holder.btnG.visibility = View.GONE
                        holder.label.visibility = View.GONE
                        holder.stat.text = "Diterima"
                        notifyItemChanged(position)
                        Log.e("xxx", dataUsulan.filter { _ -> false }.count().toString())
                        if (dataUsulan.filter { s -> s.status_poktan == null }.count() == 0) {
                            all = 0
                            btCheck.performClick()
                        }
                    }
//                    Log.e("count", dataUsulan.filter { s -> s.status_poktan != "true" && s.m1 != "false" }.count().toString())
                })
            }

            holder.btnt.setOnClickListener {
                val flatDialog = FlatDialog(context)
                flatDialog.setTitle("Konfirmasi")
                    .setSubtitle("""Apakah anda yakin ingin menolak usulan dari ${dataUsulan[position].nama_petani}?""")
                    .setBackgroundColor(Color.parseColor("#1a2849"))
                    .setFirstButtonColor(Color.parseColor("#d3f6f3"))
                    .setSecondButtonColor(Color.parseColor("#fbd1b7"))
                    .setTitleColor(Color.parseColor("#ffffff"))
                    .setSubtitleColor(Color.parseColor("#ffffff"))
                    .setFirstTextFieldHintColor(Color.parseColor("#A9A9A9"))
                    .setFirstTextFieldBorderColor(Color.parseColor("#ffffff"))
                    .setFirstTextFieldTextColor(Color.parseColor("#ffffff"))
                    .setFirstButtonTextColor(Color.parseColor("#000000"))
                    .setSecondButtonTextColor(Color.parseColor("#000000"))
                    .setFirstTextFieldHint("alasan")
                    .setFirstButtonText("OK")
                    .setSecondButtonText("BATAL")
                    .withFirstButtonListner {
                        if (!flatDialog.firstTextField.isNullOrBlank()) {
                            viewModel.verifikasiT(dataUsulan[position].id_petani, flatDialog.firstTextField).observeForever {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                if (it.status == 1) {
                                    dataUsulan[position].status_poktan = Sp("false", "","","")
                                    holder.stat.visibility = View.VISIBLE
                                    holder.btnG.visibility = View.GONE
                                    holder.label.visibility = View.GONE
                                    holder.stat.text = "Ditolak"
                                    flatDialog.dismiss()
                                    notifyItemChanged(position)
                                    if (dataUsulan.filter { s -> s.status_poktan == null}.count() == 0) {
                                        all = 0
                                        btCheck.performClick()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(context, "Alasan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .withSecondButtonListner { flatDialog.dismiss() }
                    .show()
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
                    b.putString("8", dataUsulan[position].nama_petani)
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