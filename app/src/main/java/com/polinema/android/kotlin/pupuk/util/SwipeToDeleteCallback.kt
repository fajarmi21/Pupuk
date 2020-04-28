package com.polinema.android.kotlin.pupuk.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.polinema.android.kotlin.pupuk.R

abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.icn_delete)!!
    private val infoIcon = ContextCompat.getDrawable(context, R.drawable.icn_info)!!
    private val backgroundColorL = Color.parseColor("#f44336")
    private val backgroundColorR = Color.parseColor("#28A2CF")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        /**
         * To disable "swipe" for specific item return 0 here.
         * For example:
         * if (viewHolder?.itemViewType == YourAdapter.SOME_TYPE) return 0
         * if (viewHolder?.adapterPosition == 0) return 0
         */
        if (viewHolder.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
//        val isCanceled = dX == 0f && !isCurrentlyActive

//        if (isCanceled) {
//            clearCanvas(c, itemView.left.toFloat(), itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            return
//        }

        when {
            dX < 0 -> {
                val intrinsicWidth = deleteIcon.intrinsicWidth
                val intrinsicHeight = deleteIcon.intrinsicHeight
                val background = ColorDrawable()
                // Draw the red delete background
                background.color = backgroundColorL
                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                background.draw(c)

                // Calculate position of delete icon
                val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
                val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
                val deleteIconRight = itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + intrinsicHeight

                // Draw the delete icon
                deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                deleteIcon.draw(c)
            }
            dX > 0 -> {
                val intrinsicWidth = infoIcon.intrinsicWidth
                val intrinsicHeight = infoIcon.intrinsicHeight
                val background = ColorDrawable()
                // Draw the red delete background
                background.color = backgroundColorR
                background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView
                    .bottom)
                background.draw(c)

                // Calculate position of edit icon
                val infoIconMargin = (itemHeight - intrinsicHeight) / 2
                val infoIconTop = itemView.top + infoIconMargin
                val infoIconLeft = itemView.left + infoIconMargin
                val infoIconRight = itemView.left + infoIconMargin + intrinsicWidth
                val infoIconBottom = infoIconTop + intrinsicHeight

                // Draw the delete icon
                infoIcon.setBounds(infoIconLeft, infoIconTop, infoIconRight, infoIconBottom)
                infoIcon.draw(c)
            }
            else -> {
                clearCanvas(c, itemView.right  + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}

internal enum class ButtonsState {
    GONE, LEFT_VISIBLE, RIGHT_VISIBLE
}