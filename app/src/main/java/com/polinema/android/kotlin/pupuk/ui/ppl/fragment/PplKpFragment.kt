package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.databinding.PplKpFragmentBinding
import com.polinema.android.kotlin.pupuk.model.UserPPL
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.util.SwipeToDeleteCallback
import com.polinema.android.kotlin.pupuk.viewmodel.PplKpViewModel
import kotlinx.android.synthetic.main.ppl_kp_fragment.*
import java.lang.Exception

class PplKpFragment : Fragment() {
    private lateinit var viewModel: PplKpViewModel
    private lateinit var binding: PplKpFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TypefaceProvider.registerDefaultIconSets()
        binding = DataBindingUtil.inflate(inflater, R.layout.ppl_kp_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(PplKpViewModel::class.java)
        return binding.root
    }

    private fun addFragment(fragment: Fragment) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .add(R.id.FramePPL, fragment, fragment.javaClass.simpleName)
            .addToBackStack("PplKpFragment")
            .commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PplKpViewModel::class.java)
        showData()
        PplbtAdd.setOnClickListener {
            addFragment(PplKpAddFragment())
        }
    }

    private fun showData() {
//        viewModel.poktan = SaveSharedPreference.getUser(context)
        viewModel.ppKp().observe(viewLifecycleOwner, Observer {
            try {
                rvPPL.layoutManager = LinearLayoutManager(this.context)
                val adapter = UserPPLAdapter(it)
                rvPPL.adapter = adapter

                val swipeHandler = object : SwipeToDeleteCallback(this.context!!) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        if (direction == ItemTouchHelper.LEFT) {
                            MaterialAlertDialogBuilder(context!!)
                                .setTitle("Delete '${it[viewHolder.adapterPosition].poktan}'")
                                .setMessage("Are you sure delete this record?")
                                .setNegativeButton("NO", null)
                                .setPositiveButton("YES") { _, _ ->
                                    viewModel.ppKpd(it[viewHolder.adapterPosition].poktan).observe(viewLifecycleOwner, Observer { it2 ->
                                        if (it2.status == 1) {
                                            addFragment(PplKpFragment())
                                            Toast.makeText(context, it2.message, Toast.LENGTH_SHORT).show()
                                        } else { Toast.makeText(context, it2.message, Toast.LENGTH_SHORT).show() }
                                    })
                                }
                                .show()
                        } else {
                            val frag = PplKpUpdateFragment()
                            val b = Bundle()
                            b.putString("poktan", it[viewHolder.adapterPosition].poktan)
                            frag.arguments = b
                            addFragment(frag)
                        }
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(rvPPL)
            } catch (e: Exception) {
                Log.e("ss", e.message!!)
            }
        })
    }

    inner class UserPPLAdapter(val dataUser: List<UserPPL>) :
        RecyclerView.Adapter<UserPPLAdapter.HolderUserPPL>() {
        inner class HolderUserPPL(iv: View): RecyclerView.ViewHolder(iv) {
            val no = iv.findViewById<TextView>(R.id.textView)
            val nama = iv.findViewById<TextView>(R.id.textView2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderUserPPL {
            return HolderUserPPL(LayoutInflater.from(parent.context).inflate(R.layout.ppl_kp_item, parent, false))
        }

        override fun getItemCount(): Int = dataUser.size

        @SuppressLint("ResourceType", "RtlHardcoded")
        override fun onBindViewHolder(holder: HolderUserPPL, position: Int) {
            var x = ""
            x = if ((dataUser.size - position) < 10) """0${dataUser.size - position}"""
            else {
                (dataUser.size - position).toString()
            }
            holder.no.text = x
            holder.nama.text = dataUser[position].poktan
        }
    }

}