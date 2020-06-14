package com.polinema.android.kotlin.pupuk.ui.petani.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.diegodobelo.expandingview.ExpandingItem
import com.diegodobelo.expandingview.ExpandingList
import com.google.gson.internal.LinkedTreeMap
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PtRekapFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.viewmodel.PtRekapViewModel
import java.text.SimpleDateFormat

data class SubRekap(val sektor: String, val luas: String, val urea: String, val sp36: String, val za: String, val npk: String, val organik: String, val date: String, val tahap: String? = null)

class PtRekapFragment : Fragment() {
    private lateinit var viewModel: PtRekapViewModel
    private lateinit var binding: PtRekapFragmentBinding
    lateinit var mExpandingList : ExpandingList

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pt_rekap_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PtRekapViewModel::class.java)
        mExpandingList = binding.rekapPT

        viewModel.ptDR(SaveSharedPreference.getUser(context)).observeForever {
            try {
                val arr = HashMap<String, Any>()
                if (it.m1 != null) {
                    val getrow = it.m1 as ArrayList<*>
                    for (i in getrow) {
                        val get = i as LinkedTreeMap<*, *>
                        val d = SimpleDateFormat("yyyy").format(SimpleDateFormat("dd-MM-yyyy").parse(get["date"].toString())!!)
                        var r = ArrayList<SubRekap>()
                        if (arr.containsKey(d)) {
                            r = arr[d] as ArrayList<SubRekap>
                        }
                        r.add(
                                SubRekap(
                                        get["sektor"].toString(),
                                        get["luas"].toString(),
                                        get["urea"].toString(),
                                        get["sp36"].toString(),
                                        get["za"].toString(),
                                        get["npk"].toString(),
                                        get["organik"].toString(),
                                        get["date"].toString(),
                                        "tahap 1"
                                )
                        )
                        arr[d] = r
                    }
                }
                if (it.m2 != null) {
                    val getrow = it.m2 as ArrayList<*>
                    for (i in getrow) {
                        val get = i as LinkedTreeMap<*, *>
                        val d = SimpleDateFormat("yyyy").format(SimpleDateFormat("dd-MM-yyyy").parse(get["date"].toString())!!)
                        var r = ArrayList<SubRekap>()
                        if (arr.containsKey(d)) {
                            r = arr[d] as ArrayList<SubRekap>
                        }
                        r.add(
                                SubRekap(
                                        get["sektor"].toString(),
                                        get["luas"].toString(),
                                        get["urea"].toString(),
                                        get["sp36"].toString(),
                                        get["za"].toString(),
                                        get["npk"].toString(),
                                        get["organik"].toString(),
                                        get["date"].toString(),
                                        "tahap 2"
                                )
                        )
                        arr[d] = r
                    }
                }
                if (it.m3 != null) {
                    val getrow = it.m3 as ArrayList<*>
                    for (i in getrow) {
                        val get = i as LinkedTreeMap<*, *>
                        val d = SimpleDateFormat("yyyy").format(SimpleDateFormat("dd-MM-yyyy").parse(get["date"].toString())!!)
                        var r = ArrayList<SubRekap>()
                        if (arr.containsKey(d)) {
                            r = arr[d] as ArrayList<SubRekap>
                        }
                        r.add(
                                SubRekap(
                                        get["sektor"].toString(),
                                        get["luas"].toString(),
                                        get["urea"].toString(),
                                        get["sp36"].toString(),
                                        get["za"].toString(),
                                        get["npk"].toString(),
                                        get["organik"].toString(),
                                        get["date"].toString(),
                                        "tahap 3"
                                )
                        )
                        arr[d] = r
                    }
                }

                for ((key, value) in arr.entries) {
                    addItem(key, value as ArrayList<SubRekap>, R.color.blue, R.drawable.ic_ghost)
                }
                Log.e("ss", arr.toString())
            } catch (e: Exception) {
                Log.e("ss", e.message!!)
            }
        }
    }

    private fun addItem(title : String, subItems : ArrayList<SubRekap>, colorRes : Int, iconRes : Int) {
        val item = mExpandingList.createNewItem(R.layout.pt_rekap_layout)

        if (item != null) {
            item.setIndicatorColorRes(colorRes)
            item.setIndicatorIconRes(iconRes)
            (item.findViewById<View>(R.id.judul) as TextView).text = title
            item.createSubItems(subItems.size)
            for (i in 0 until item.subItemsCount) {
                var view = item.getSubItemView(i)
                configureSubItem(item, view, subItems[i])
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun configureSubItem(item : ExpandingItem, view: View, subTitle : SubRekap) {
        (view.findViewById<View>(R.id.sub_judul) as TextView).text = """${subTitle.tahap} => ${subTitle.sektor}"""
        view.findViewById<View>(R.id.rlsubItem).setOnClickListener {
            val f = PtDetailRekapFragment()
            val b = Bundle()
            b.putString("0", subTitle.date)
            b.putString("1", subTitle.sektor)
            b.putString("2", subTitle.luas)
            b.putString("3", subTitle.urea)
            b.putString("4", subTitle.sp36)
            b.putString("5", subTitle.za)
            b.putString("6", subTitle.npk)
            b.putString("7", subTitle.organik)
            f.arguments = b
            addFragment(f)
        }
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.FramePT, fragment, fragment.javaClass.simpleName)
            .addToBackStack("PtRekapFragment")
            .commit()
    }
}