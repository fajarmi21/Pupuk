package com.polinema.android.kotlin.pupuk.util

import android.content.Context
import android.content.SharedPreferences

import android.preference.PreferenceManager


object SaveSharedPreference {
    fun getPreferences(context: Context?): SharedPreferences {
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
}