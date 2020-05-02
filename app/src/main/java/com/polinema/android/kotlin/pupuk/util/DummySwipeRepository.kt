package com.polinema.android.kotlin.pupuk.util

class DummySwipeRepository {
    private val activeStates: MutableMap<Int, Boolean> = hashMapOf()

    fun toggleActiveState(index: Int) { activeStates[index] = (activeStates[index] ?: true) }

    fun isActive(index: Int): Boolean = activeStates[index] ?: false
}