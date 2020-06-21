package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paulrybitskyi.persistentsearchview.adapters.model.SuggestionItem
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchConfirmedListener
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchQueryChangeListener
import com.paulrybitskyi.persistentsearchview.listeners.OnSuggestionChangeListener
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.KpRekapFragmentBinding
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.viewmodel.KpRekapViewModel
import java.text.SimpleDateFormat

class KpRekapFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: KpRekapViewModel
    private lateinit var binding: KpRekapFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.kp_rekap_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(KpRekapViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchView()
        initRecyclerView()
    }

    private fun initSearchView() {
        with(binding.persistentSearchView) {
            setOnLeftBtnClickListener(this@KpRekapFragment)
            setOnClearInputBtnClickListener(this@KpRekapFragment)
            setOnRightBtnClickListener(this@KpRekapFragment)
            showRightButton()
            setVoiceRecognitionDelegate(VoiceRecognitionDelegate(this@KpRekapFragment))
            setOnSearchConfirmedListener(mOnSearchConfirmedListener)
            setOnSearchQueryChangeListener(mOnSearchQueryChangeListener)
            setOnSuggestionChangeListener(mOnSuggestionChangeListener)
            setDismissOnTouchOutside(true)
            setDimBackground(true)
            isProgressBarEnabled = true
            isVoiceInputButtonEnabled = true
            isClearInputButtonEnabled = true
            setQueryInputGravity(Gravity.START or Gravity.CENTER)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initRecyclerView() {
        viewModel.Kpr(getUser(context)).observeForever {
            try {
                binding.rvRKP.layoutManager = LinearLayoutManager(this.context)
                var x = ArrayList<Any>()
                var data = HashMap<Any, Any>()
                it.forEach {
                    if (!it.m1.isNullOrEmpty()) {
                        it.m1.map {i ->
                            val s = if (i.sektor != null) i.sektor else "-"
                            val tp =
                                    if (i.sektor != null)
                                        (i.urea.toDouble() + i.sp36.toDouble() + i.za.toDouble() + i.npk.toDouble() + i.organik.toDouble()).toString()
                                    else "-"
                            val thn = SimpleDateFormat("yyyy").format(SimpleDateFormat("dd-MM-yyyy").parse(i.date)!!)
                            var thp = HashMap<Any, Any>()
                            if (data.containsKey(thn)) {
                                thp = data[thn] as HashMap<Any, Any>
                                var pt = ArrayList<Any>()
                                if (thp.containsKey("Tahap 1")) {
                                    pt = thp["Tahap 1"] as ArrayList<Any>
                                }
                                pt.add(ItemC(it.nama_petani,s, tp))
                                thp.put("Tahap 1", pt)
                                data.put(thn, thp)
                            } else {
                                thp["Tahap 1"] = arrayListOf(ItemC(it.nama_petani, s, tp))
                                data[thn] = thp
                            }
                        }
                    }

                    if (!it.m2.isNullOrEmpty()) {
                        it.m2.map {i ->
                            val s = if (i.sektor != null) i.sektor else "-"
                            val tp =
                                    if (i.sektor != null)
                                        (i.urea.toDouble() + i.sp36.toDouble() + i.za.toDouble() + i.npk.toDouble() + i.organik.toDouble()).toString()
                                    else "-"
                            val thn = SimpleDateFormat("yyyy").format(SimpleDateFormat("dd-MM-yyyy").parse(i.date)!!)
                            var thp = HashMap<Any, Any>()
                            if (data.containsKey(thn)) {
                                thp = data[thn] as HashMap<Any, Any>
                                var pt = ArrayList<Any>()
                                if (thp.containsKey("Tahap 2")) {
                                    pt = thp["Tahap 2"] as ArrayList<Any>
                                }
                                pt.add(ItemC(it.nama_petani, s, tp))
                                thp.put("Tahap 2", pt)
                                data.put(thn, thp)
                            } else {
                                thp["Tahap 2"] = arrayListOf(ItemC(it.nama_petani, s, tp))
                                data[thn] = thp
                            }
                        }
                    }

                    if (!it.m3.isNullOrEmpty()) {
                        it.m3.map {i ->
                            val s = if (i.sektor != null) i.sektor else "-"
                            val tp =
                                    if (i.sektor != null)
                                        (i.urea.toDouble() + i.sp36.toDouble() + i.za.toDouble() + i.npk.toDouble() + i.organik.toDouble()).toString()
                                    else "-"
                            val thn = SimpleDateFormat("yyyy").format(SimpleDateFormat("dd-MM-yyyy").parse(i.date)!!)
                            var thp = HashMap<Any, Any>()
                            if (data.containsKey(thn)) {
                                thp = data[thn] as HashMap<Any, Any>
                                var pt = ArrayList<Any>()
                                if (thp.containsKey("Tahap 3")) {
                                    pt = thp["Tahap 3"] as ArrayList<Any>
                                }
                                pt.add(ItemC(it.nama_petani, s, tp))
                                thp.put("Tahap 3", pt)
                                data.put(thn, thp)
                            } else {
                                thp["Tahap 3"] = arrayListOf(ItemC(it.nama_petani, s, tp))
                                data[thn] = thp
                            }
                        }
                    }
                }

                data.forEach { (t, u) ->
                    var total = 0.0
                    val v = u as HashMap<*, *>
                    x.add(ItemH(t.toString()))
                    v.forEach { (t, u) ->
                        var tt = 0.0
                        x.add(ItemCH(t.toString()))
                        val w = u as ArrayList<*>
                        w.forEach {
                            if (it is ItemC) {
                                tt += it.tp.toDouble()
                                x.add(ItemC(it.petani, it.usul, String.format("%.2f", it.tp.toDouble())))
                            }
                        }
                        total += tt
                        x.add(ItemCF(String.format("%.2f", tt)))
                    }
                    x.add(ItemF(String.format("%.2f", total)))
                }
                binding.rvRKP.adapter = RekapKTAdapter(x)
            } catch (e: Exception) {
                Log.e("ss", e.message)
            }
        }
//        binding.rvRKP.addOnScrollListener(object : HeaderedRecyclerViewListener(context!!) {
//
//            override fun showHeader() {
//                AnimationUtils.showHeader(binding.persistentSearchView)
//            }
//
//            override fun hideHeader() {
//                AnimationUtils.hideHeader(binding.persistentSearchView)
//            }
//
//        })
    }


    private val mOnSearchConfirmedListener = OnSearchConfirmedListener { searchView, query ->
    }


    private val mOnSearchQueryChangeListener = OnSearchQueryChangeListener { searchView, oldQuery, newQuery ->
        Log.e("aad", newQuery)
    }


    private val mOnSuggestionChangeListener = object : OnSuggestionChangeListener {

        override fun onSuggestionPicked(suggestion: SuggestionItem) {
        }

        override fun onSuggestionRemoved(suggestion: SuggestionItem) {
        }

    }

    override fun onClick(v: View?) {
        when(view!!.id) {
            R.id.leftBtnIv -> onLeftButtonClicked()
            R.id.clearInputBtnIv -> onClearInputButtonClicked()
            R.id.rightBtnIv -> onRightButtonClicked()
        }
    }

    private fun onLeftButtonClicked() {
    }


    private fun onClearInputButtonClicked() {
    }


    private fun onRightButtonClicked() {
        Toast.makeText(context,"Right button clicked.", Toast.LENGTH_SHORT).show()
    }

    inner class ItemH(val tahun: String)
    inner class ItemCH(val tahap: String)
    inner class ItemC(val petani: String, val usul: String, val tp: String)
    inner class ItemCF(val total: String)
    inner class ItemF(val ttotal: String)

    class RekapKTAdapter(val dataRekap: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        companion object {
            private const val ITEM_HEADER = 0
            private const val ITEM_CONTENT_HEADER = 1
            private const val ITEM_CONTENT = 2
            private const val ITEM_CONTENT_FOOTER = 3
            private const val ITEM_FOOTER = 4
            private var number = 0
        }

        class MenuHeaderHolder(v: View) : RecyclerView.ViewHolder(v) {
            private val itemTahunHeader = v.findViewById(R.id.tahunTv) as TextView

            fun bindContent(text: ItemH){
                itemTahunHeader.text = text.tahun
                itemTahunHeader.paintFlags = Paint.UNDERLINE_TEXT_FLAG
//                itemTTahunHeader.text = text.ttahun
            }
        }

        class MenuContentHHolder(v: View) : RecyclerView.ViewHolder(v) {
            val itemTahapItem = v.findViewById(R.id.tahapTv) as TextView

            fun bindContent(text: ItemCH){
                itemTahapItem.text = text.tahap
            }
        }

        class MenuContentHolder(v: View) : RecyclerView.ViewHolder(v) {
            val itemNo = v.findViewById(R.id.NoTv) as TextView
            val itemPetani = v.findViewById(R.id.PetaniTv) as TextView
            val itemSektor = v.findViewById(R.id.UsulTv) as TextView
            val itemUsul = v.findViewById(R.id.PupukUTv) as TextView

            fun bindContent(number: Int, text: ItemC){
                itemNo.text = number.toString()
                itemPetani.text = text.petani
                itemSektor.text = text.usul
                itemUsul.text = text.tp.replace(",",".")
            }
        }

        class MenuContentFHolder(v: View) : RecyclerView.ViewHolder(v) {
            val itemUsul = v.findViewById(R.id.TPupukUTv) as TextView

            fun bindContent(text: ItemCF){
                itemUsul.text = text.total.replace(",",".")
            }
        }

        class MenuFooterHolder(v: View) : RecyclerView.ViewHolder(v) {
            private val itemTTotalHeader = v.findViewById(R.id.TTPupukUTv) as TextView

            fun bindContent(text: ItemF){
                itemTTotalHeader.text = text.ttotal.replace(",",".")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                ITEM_HEADER -> MenuHeaderHolder(LayoutInflater.from(parent.context).inflate(R.layout.kp_rekap_item_header_fragment, parent, false))
                ITEM_CONTENT_HEADER -> MenuContentHHolder(LayoutInflater.from(parent.context).inflate(R.layout.kp_rekap_item_contenth_fragment, parent, false))
                ITEM_CONTENT -> MenuContentHolder(LayoutInflater.from(parent.context).inflate(R.layout.kp_rekap_item_contentc_fragment, parent, false))
                ITEM_CONTENT_FOOTER -> MenuContentFHolder(LayoutInflater.from(parent.context).inflate(R.layout.kp_rekap_item_contentf_fragment, parent, false))
                ITEM_FOOTER -> MenuFooterHolder(LayoutInflater.from(parent.context).inflate(R.layout.kp_rekap_item_footer_fragment, parent, false))
                else -> throw throw IllegalArgumentException("Undefined view type")
            }
        }

        override fun getItemCount(): Int = dataRekap.size

        override fun getItemViewType(position: Int): Int {
            return when (dataRekap[position]) {
                is ItemH -> ITEM_HEADER
                is ItemCH -> ITEM_CONTENT_HEADER
                is ItemC -> ITEM_CONTENT
                is ItemCF -> ITEM_CONTENT_FOOTER
                is ItemF -> ITEM_FOOTER
                else -> throw IllegalArgumentException("Undefined view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                ITEM_HEADER -> {
                    val headerHolder = holder as MenuHeaderHolder
                    number = 0
                    headerHolder.bindContent(dataRekap[position] as ItemH)
                }
                ITEM_CONTENT_HEADER -> {
                    val headerItemHolder = holder as MenuContentHHolder
                    number = 0
                    headerItemHolder.bindContent(dataRekap[position] as ItemCH)
                }
                ITEM_CONTENT -> {
                    val contentHolder = holder as MenuContentHolder
                    number += 1
                    contentHolder.bindContent(number, dataRekap[position] as ItemC)
                }
                ITEM_CONTENT_FOOTER -> {
                    val contentHolder = holder as MenuContentFHolder
                    number = 0
                    contentHolder.bindContent(dataRekap[position] as ItemCF)
                }
                ITEM_FOOTER -> {
                    val footerHolder = holder as MenuFooterHolder
                    number = 0
                    footerHolder.bindContent(dataRekap[position] as ItemF)
                }
                else -> throw IllegalArgumentException("Undefined view type")

            }
        }
    }
}