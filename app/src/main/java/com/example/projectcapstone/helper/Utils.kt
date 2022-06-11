package com.example.projectcapstone.helper

import android.util.Patterns

object Helper {
    fun isEmailValid(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    interface ApiCallbackString {
        fun onResponse(success: Boolean, message: String)
    }
}