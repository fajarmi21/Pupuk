package com.polinema.android.kotlin.pupuk.ui.kp.fragment

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.KpPetaniFragmentBinding
import com.polinema.android.kotlin.pupuk.model.UserKT
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference.getUser
import com.polinema.android.kotlin.pupuk.util.SwipeToDeleteCallback
import com.polinema.android.kotlin.pupuk.viewmodel.KpPetaniViewModel
import kotlinx.android.synthetic.main.kp_petani_fragment.*


class KpPetaniFragment : Fragment() {

    private lateinit var viewModel: KpPetaniViewModel
    private lateinit var binding: KpPetaniFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TypefaceProvider.registerDefaultIconSets()
        binding = DataBindingUtil.inflate(inflater, R.layout.kp_petani_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(KpPetaniViewModel::class.java)
        return binding.root
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .add(R.id.FrameKP, fragment, fragment.javaClass.simpleName)
            .addToBackStack("KpPetaniFragment")
            .commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showData()
        btAdd.setOnClickListener {
            addFragment(KpPetaniAddFragment())
        }
    }

    private fun showData() {
        viewModel.poktan = getUser(context)
        viewModel.ptKr().observe(viewLifecycleOwner, Observer {
            rvKPP.layoutManager = LinearLayoutManager(this.context)
            val adapter = UserKTAdapter(it)
            rvKPP.adapter = adapter

            val swipeHandler = object : SwipeToDeleteCallback(this.context!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT) {
                        MaterialAlertDialogBuilder(context!!)
                            .setTitle("Delete '${it[viewHolder.adapterPosition].nama_petani}'")
                            .setMessage("Are you sure delete this record?")
                            .setNegativeButton("NO", null)
                            .setPositiveButton("YES") { _, _ ->
                                viewModel.ptKd(it[viewHolder.adapterPosition].nama_petani).observe(viewLifecycleOwner, Observer { it2 ->
                                    if (it2.status == 1) {
                                        onResume()
                                        Toast.makeText(context, it2.message, Toast.LENGTH_SHORT).show()
                                    } else { Toast.makeText(context, it2.message, Toast.LENGTH_SHORT).show() }
                                })
                            }
                            .show()
                    } else {
                        val frag = KpPetaniUpdateFragment()
                        val b = Bundle()
                        b.putString("petani", it[viewHolder.adapterPosition].nama_petani)
                        frag.arguments = b
                        addFragment(frag)
                    }
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(rvKPP)
        })
    }

    inner class UserKTAdapter(val dataUser: List<UserKT>) :
        RecyclerView.Adapter<UserKTAdapter.HolderUserKT>() {
        inner class HolderUserKT(iv: View): RecyclerView.ViewHolder(iv) {
            val no = iv.findViewById<TextView>(R.id.textView)
            val nama = iv.findViewById<TextView>(R.id.textView2)
            val alamat = iv.findViewById<TextView>(R.id.textView3)
            val luas = iv.findViewById<TextView>(R.id.textView4)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderUserKT {
            return HolderUserKT(LayoutInflater.from(parent.context).inflate(R.layout.kp_petani_item, parent, false))
        }

        override fun getItemCount(): Int = dataUser.size

        @SuppressLint("ResourceType", "RtlHardcoded")
        override fun onBindViewHolder(holder: HolderUserKT, position: Int) {
            var x = ""
            x = if ((dataUser.size - position) < 10) """0${dataUser.size - position}"""
            else {
                (dataUser.size - position).toString()
            }
            holder.no.text = x
            holder.nama.text = dataUser[position].nama_petani
            holder.alamat.text = dataUser[position].alamat
            holder.luas.text = dataUser[position].luas_lahan
        }
    }

}