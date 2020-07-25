package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beardedhen.androidbootstrap.BootstrapButton
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.PplVerifikasi
import com.polinema.android.kotlin.pupuk.model.data
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.PplUsulViewModel
import kotlinx.android.synthetic.main.ppl_usul_fragment.*

class PplUsulFragment : Fragment() {
    private lateinit var viewModel: PplUsulViewModel
    private var show = 0
    private var all = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TypefaceProvider.registerDefaultIconSets()
        viewModel = ViewModelProvider(this).get(PplUsulViewModel::class.java)
        return inflater.inflate(R.layout.ppl_usul_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        viewModel.PplUsul(SaveSharedPreference.getUser(context)).observeForever {
            try {
                rvPPLV.layoutManager = LinearLayoutManager(context)
                val adapter = PrcAdapter(it)
                rvPPLV.adapter = adapter
            } catch (e: Exception) {
                Log.e("ss", e.message!!)
            }
        }

        btCheckPPL.setOnClickListener {
            if (all == 1) {
                if (show == 1) show = 0 else show = 1
                rvPPLV.adapter!!.notifyDataSetChanged()
            } else {
                show = 0
            }
        }
    }

    inner class PrcAdapter (val dataPrc:List<PplVerifikasi>):
            RecyclerView.Adapter<PrcAdapter.HolderPrcA>(){
        inner class HolderPrcA(iv: View): RecyclerView.ViewHolder(iv){
            var no = iv.findViewById<TextView>(R.id.DetailnoUsulPpl)
            var nama = iv.findViewById<TextView>(R.id.DetailUsulPplNama)
            var luas = iv.findViewById<TextView>(R.id.DetailUsulPplLuas)
            var btnGr = iv.findViewById<LinearLayout>(R.id.btnGVARPPL)
            var btnGt = iv.findViewById<LinearLayout>(R.id.btnGVTRPPL)
            var btnTl = iv.findViewById<BootstrapButton>(R.id.btnVARPPL)
            var btnTm = iv.findViewById<BootstrapButton>(R.id.btntVARPPL)
            var card = iv.findViewById<CardView>(R.id.UsulPplItem)
            var status = iv.findViewById<TextView>(R.id.txStatusPPL)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrcAdapter.HolderPrcA {
            return HolderPrcA(LayoutInflater.from(parent.context).inflate(R.layout.item_ppl_usul_fragment, parent, false))
        }

        override fun getItemCount(): Int = dataPrc.size

        override fun onBindViewHolder(holder: PrcAdapter.HolderPrcA, position: Int) {
            holder.no.text = (position + 1).toString()
            holder.nama.text = dataPrc[position].poktan
            holder.luas.text = String.format("%.2f", dataPrc[position].luas.toDouble()).replace(",", ".")

            var id = HashMap<String,String>()
            var i = 0
            var x = 0
            dataPrc[position].data.forEach {
                if (it.status_poktan && !it.verifikasi) {
                    id["""id[$i]"""] = it.id
                    x += 1
                }
                i += 1
            }

            if (dataPrc[position].data.filter { s -> s.status_poktan && s.verifikasi && s.admin }.count() == dataPrc[position].data.size) {
                holder.status.text = "Diterima ADMIN"
            } else if (dataPrc[position].data.filter { s -> s.status_poktan && s.verifikasi != null}.count() == dataPrc[position].data.size) {
                holder.status.text = "Diverifikasi"
            }

            if (x > 0) {
                if (show == 1) holder.btnGr.visibility = View.VISIBLE else holder.btnGr.visibility = View.GONE
            } else {
                holder.btnGt.visibility = View.VISIBLE
            }
//            Log.e("xx", id.toString())
            holder.btnTl.setOnClickListener {
                viewModel.PplUsulU(id).observeForever {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    if (it.status == 1) {
                        btCheckPPL.performClick()
                        init()
                    }
                }
            }
            holder.btnTm.setOnClickListener {
                val flatDialog = FlatDialog(context)
                flatDialog.setTitle("Konfirmasi")
                        .setSubtitle("""Apakah anda yakin ingin menolak usulan dari ${dataPrc[position].poktan}?""")
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
                                viewModel.PplUsulT(id, flatDialog.firstTextField).observeForever {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                    if (it.status == 1) {
                                        flatDialog.dismiss()
                                        notifyItemChanged(position)
                                        btCheckPPL.performClick()
                                        init()
//                                        if (dataPrc.filter { it.data.filter { it.verifikasi == null }.count() == 0 }.count() == 0) {
////                                            all = 0
////                                            btCheckPPL.performClick()
//                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Alasan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .withSecondButtonListner { flatDialog.dismiss() }
                        .show()
            }
            holder.card.setOnClickListener {
                val fragment = PplUsulDetailFragment()
                val b = Bundle()
                b.putString("poktan", dataPrc[position].poktan)
                fragment.arguments = b
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.FramePPL, fragment, fragment.javaClass.simpleName)
                    .addToBackStack("PplUsulFragment")
                    .commit()
            }
        }
    }
}