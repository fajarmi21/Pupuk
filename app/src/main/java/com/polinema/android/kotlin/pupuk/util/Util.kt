package com.polinema.android.kotlin.pupuk.util

import android.util.Patterns

class Util {
    companion object {
        fun isEmailValid(email: String): Boolean {
//            val expression = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+"
//            val pattern = Pattern.compile(expression)
//            val matcher = pattern.matcher(email.trim())
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}