package com.polinema.android.kotlin.pupuk.util

import android.text.InputFilter
import android.text.Spanned


class MinMaxFilter : InputFilter {
    private var mIntMin: Double
    private var mIntMax: Double

    constructor(minValue: Double, maxValue: Double) {
        mIntMin = minValue
        mIntMax = maxValue
    }

    constructor(minValue: String, maxValue: String) {
        mIntMin = minValue.toDouble()
        mIntMax = maxValue.toDouble()
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toDouble()
            if (isInRange(mIntMin, mIntMax, input)) return null
        } catch (nfe: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(a: Double, b: Double, c: Double): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}