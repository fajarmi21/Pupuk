package com.polinema.android.kotlin.pupuk.util.holder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.polinema.android.kotlin.pupuk.R
import com.polinema.android.kotlin.pupuk.model.data
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.model.TreeNode.BaseNodeViewHolder

class SelectableItemHolder(context: Context?) : BaseNodeViewHolder<data>(context) {
    private var tvValue: TextView? = null
    private var nodeSelector: CheckBox? = null
    private var bool: Boolean = true

    @SuppressLint("InflateParams")
    override fun createNodeView(
        node: TreeNode,
        value: data
    ): View? {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.layout_selectable_item, null, false)
        tvValue = view.findViewById<View>(R.id.node_value) as TextView
        tvValue!!.text = value.petani
        bool = !value.verifikasi
        nodeSelector = view.findViewById<View>(R.id.node_selector) as CheckBox
        nodeSelector!!.setOnCheckedChangeListener { _, isChecked ->
            node.isSelected = isChecked
        }
        nodeSelector!!.isChecked = node.isSelected
        if (node.isLastChild) {
            view.findViewById<View>(R.id.bot_line).visibility = View.INVISIBLE
        }
        return view
    }


    override fun toggleSelectionMode(editModeEnabled: Boolean) {
        nodeSelector!!.visibility = if (bool && editModeEnabled) View.VISIBLE else View.GONE
        nodeSelector!!.isChecked = mNode.isSelected
    }
}