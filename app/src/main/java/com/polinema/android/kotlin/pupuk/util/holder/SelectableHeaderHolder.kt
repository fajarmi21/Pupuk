package com.polinema.android.kotlin.pupuk.util.holder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.github.johnkil.print.PrintView
import com.polinema.android.kotlin.pupuk.R
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.model.TreeNode.BaseNodeViewHolder

class SelectableHeaderHolder(context: Context) : BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem>(
    context
) {
    private var tvValue: TextView? = null
    private var tvLuas: TextView? = null
    private var arrowView: PrintView? = null
    private var nodeSelector: CheckBox? = null

    @SuppressLint("InflateParams")
    override fun createNodeView(
        node: TreeNode,
        value: IconTreeItemHolder.IconTreeItem
    ): View? {
        val inflater = LayoutInflater.from(context)
        val view: View =
            inflater.inflate(R.layout.layout_selectable_header, null, false)
        tvValue = view.findViewById<View>(R.id.node_value) as TextView
        tvLuas = view.findViewById<View>(R.id.node_luas) as TextView
        tvValue!!.text = value.text
        tvLuas!!.text = value.luas
        val iconView = view.findViewById<View>(R.id.icon) as PrintView
        iconView.iconText = context.resources.getString(value.icon)
        arrowView = view.findViewById<View>(R.id.arrow_icon) as PrintView
        if (node.isLeaf) {
            arrowView!!.visibility = View.GONE
        }
        nodeSelector =
            view.findViewById<View>(R.id.node_selector) as CheckBox
        nodeSelector!!.setOnCheckedChangeListener { _, isChecked ->
//            node.isSelected = isChecked
            for (n in node.children) {
                treeView.selectNode(n, isChecked)
            }
        }
//        nodeSelector!!.isChecked = node.isSelected
        return view
    }

    override fun toggle(active: Boolean) {
        arrowView!!.iconText = context.resources
            .getString(if (active) R.string.ic_keyboard_arrow_down else R.string.ic_keyboard_arrow_right)
    }

    override fun toggleSelectionMode(editModeEnabled: Boolean) {
        nodeSelector!!.visibility = if (editModeEnabled) View.VISIBLE else View.GONE
        nodeSelector!!.isChecked = mNode.isSelected
    }
}