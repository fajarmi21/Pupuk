package com.polinema.android.kotlin.pupuk.util.holder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.github.johnkil.print.PrintView
import com.polinema.android.kotlin.pupuk.R
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.model.TreeNode.BaseNodeViewHolder

class ProfileHolder(context: Context) : BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem>(context) {
    @SuppressLint("InflateParams")
    override fun createNodeView(
        node: TreeNode?,
        value: IconTreeItemHolder.IconTreeItem
    ): View? {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.layout_profile_node, null, false)
        val tvValue =
            view.findViewById<View>(R.id.node_value) as TextView
        tvValue.text = value.text
        val iconView = view.findViewById<View>(R.id.icon) as PrintView
        iconView.iconText = context.resources.getString(value.icon)
        return view
    }

    override fun toggle(active: Boolean) {}

    override fun getContainerStyle(): Int {
        return R.style.TreeNodeStyleCustom
    }
}