package com.polinema.android.kotlin.pupuk.ui.ppl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.PplVerifikasi
import com.polinema.android.kotlin.pupuk.model.data
import com.polinema.android.kotlin.pupuk.util.DummySwipeRepository
import com.polinema.android.kotlin.pupuk.util.SaveSharedPreference
import com.polinema.android.kotlin.pupuk.util.holder.IconTreeItemHolder
import com.polinema.android.kotlin.pupuk.util.holder.IconTreeItemHolder.IconTreeItem
import com.polinema.android.kotlin.pupuk.util.holder.ProfileHolder
import com.polinema.android.kotlin.pupuk.util.holder.SelectableHeaderHolder
import com.polinema.android.kotlin.pupuk.util.holder.SelectableItemHolder
import com.polinema.android.kotlin.pupuk.viewmodel.PplUsulViewModel
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.view.AndroidTreeView

class PplUsulFragment : Fragment() {
    private lateinit var viewModel: PplUsulViewModel
    private val repository = DummySwipeRepository()
    private var show = 1
    private var all = 0
    private var btn = 0
    private lateinit var list : MutableList<PplVerifikasi>

    private var tView: AndroidTreeView? = null
    private var selectionModeEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TypefaceProvider.registerDefaultIconSets()
        viewModel = ViewModelProvider(this).get(PplUsulViewModel::class.java)
        return inflater.inflate(R.layout.ppl_usul_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        val containerView =
                view!!.findViewById(R.id.containerPPL) as ViewGroup
        val root = TreeNode.root()
        val ar = ArrayList<TreeNode>()
        val av = ArrayList<ArrayList<data>>()
        viewModel.PplUsul(SaveSharedPreference.getUser(context)).observeForever {
            try {
                it.forEach {
                    ar.add(TreeNode(IconTreeItem(R.string.ic_person, it.poktan, it.luas)).setViewHolder(activity?.let { SelectableHeaderHolder(it) }))
                    av.add(it.data)
                }
                for (i in 0 until ar.size) {
                    fillFolder(ar[i], av[i])
                }
                root.addChildren(ar)

                tView = AndroidTreeView(activity, root)
                tView!!.setDefaultAnimation(true)
                containerView.addView(tView!!.view)
            } catch (e: Exception) {
                Log.e("ss", e.message!!)
            }
        }

        val selectionModeButton: View = view!!.findViewById(R.id.btCheckPPL)
        selectionModeButton.setOnClickListener {
            if (tView!!.selected.isNullOrEmpty()) {
                selectionModeEnabled = !selectionModeEnabled
                tView!!.isSelectionModeEnabled = selectionModeEnabled
            } else {
                var ar = HashMap<String,String>()
                tView!!.selected.forEach {
                    val x = it.value as data
                    if (!x.verifikasi) ar["""id[${ar.size}]"""] = x.petani
                }
                Log.e("xx", ar.toString())
                viewModel.PplUsulU(ar).observeForever {
                    try {
                        if (it.status == 1) {
                            tView!!.deselectAll()
                            tView!!.collapseAll()
                            selectionModeButton.performClick()
                        }
                        Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e("error", e.message!!)
                    }
                }
            }
        }
    }

    private fun fillFolder(folder : TreeNode, av: ArrayList<data>) {
        av.forEach {
            folder.addChildren(TreeNode(it).setViewHolder(SelectableItemHolder(activity)))
        }
//        val file1 = TreeNode("File1").setViewHolder(SelectableItemHolder(activity))
//        val file2 = TreeNode("File2").setViewHolder(SelectableItemHolder(activity))
//        val file3 = TreeNode("File3").setViewHolder(SelectableItemHolder(activity))
//        folder.addChildren(file1, file2, file3)
    }
}