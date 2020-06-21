package com.polinema.android.kotlin.pupuk.util

import android.content.Context
import android.content.SharedPreferences

import android.preference.PreferenceManager

object SaveSharedPreference {
    private fun getPreferences(context: Context?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     * @param level
     */
    fun setLoggedIn(context: Context?, loggedIn: Boolean, username: String?, level: Int) {
        val editor = getPreferences(context).edit()
        editor.putBoolean("logged_in_status", loggedIn)
        editor.putString("logged_in_username", username)
        editor.putInt("logged_in_level", level)
        editor.apply()
    }

    fun setTime(context: Context?, left: Long, time: Boolean, end: Long, code: Int) {
        val editor = getPreferences(context).edit()
        editor.putLong("millisLeft", left)
        editor.putBoolean("timeMode", time)
        editor.putLong("endTime", end)
        editor.putInt("Code", code)
        editor.apply()
    }

    fun setCode(context: Context?, code: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt("Code", code)
        editor.apply()
    }

    fun setAdd(context: Context?, code: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt("Add", code)
        editor.apply()
    }

    fun reset(context: Context?) {
        getPreferences(context).edit().remove("millisLeft").apply()
        getPreferences(context).edit().remove("timeMode").apply()
        getPreferences(context).edit().remove("endTime").apply()
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    fun getLoggedStatus(context: Context?): Boolean {
        return getPreferences(context).getBoolean("logged_in_status", false)
    }

    fun getLevel(context: Context?): Int {
        return getPreferences(context).getInt("logged_in_level", 0)
    }

    fun getUser(context: Context?): String {
        return getPreferences(context).getString("logged_in_username", null)!!
    }

    fun getLeft(context: Context?, def: Long): Long {
        return getPreferences(context).getLong("millisLeft", def)
    }

    fun getTime(context: Context?, def: Boolean): Boolean {
        return getPreferences(context).getBoolean("timeMode", def)
    }

    fun getEnd(context: Context?, def: Long): Long {
        return getPreferences(context).getLong("endTime", def)
    }

    fun getCode(context: Context?, def: Int): Int {
        return getPreferences(context).getInt("Code", def)
    }

    fun getAdd(context: Context?): Int {
        return getPreferences(context).getInt("Add", 0)
    }
}