package com.faleev.firstapplication

import android.content.Context

object AuthHelper {
    fun saveUserData(context: Context, email: String, password: String, autoLogin: Boolean) {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).edit().apply {
            putString("email", email)
            putString("password", password)
            putBoolean("auto_login", autoLogin)
            apply()
        }
    }

    fun getUserData(context: Context): Triple<String?, String?, Boolean> {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return Triple(
            prefs.getString("email", null),
            prefs.getString("password", null),
            prefs.getBoolean("auto_login", false)
        )
    }
}