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

class IconTreeItemHolder(context: Context) : BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem>(context) {
    private var tvValue: TextView? = null
    private var arrowView: PrintView? = null

    @SuppressLint("InflateParams")
    override fun createNodeView(
        node: TreeNode,
        value: IconTreeItem
    ): View? {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.layout_icon_node, null, false)
        tvValue = view.findViewById<View>(R.id.node_value) as TextView
        tvValue!!.text = value.text
        val iconView = view.findViewById<View>(R.id.icon) as PrintView
        iconView.iconText = context.resources.getString(value.icon)
        arrowView = view.findViewById<View>(R.id.arrow_icon) as PrintView
        view.findViewById<View>(R.id.btn_addFolder)
            .setOnClickListener {
                val newFolder =
                    TreeNode(
                        IconTreeItem(
                            R.string.ic_folder,
                            "New Folder",
                                "New Folder 2"
                        )
                    )
                treeView.addNode(node, newFolder)
            }
        view.findViewById<View>(R.id.btn_delete)
            .setOnClickListener { treeView.removeNode(node) }

        //if My computer
        if (node.level == 1) {
            view.findViewById<View>(R.id.btn_delete).visibility = View.GONE
        }
        return view
    }

    override fun toggle(active: Boolean) {
        arrowView!!.iconText = context.resources
            .getString(if (active) R.string.ic_keyboard_arrow_down else R.string.ic_keyboard_arrow_right)
    }

    class IconTreeItem(var icon: Int, var text: String, var luas: String, var verif: Boolean? = false)
}