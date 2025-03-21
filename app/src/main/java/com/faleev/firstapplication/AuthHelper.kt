package com.faleev.firstapplication

import android.content.Context

object AuthHelper {
    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_AUTO_LOGIN = "auto_login"

    // Сохраняем данные локально (как раньше)
    fun saveUserData(context: Context, email: String, password: String, autoLogin: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_AUTO_LOGIN, autoLogin)
            apply()
        }
    }

    // Получаем данные из SharedPreferences
    fun getUserData(context: Context): Triple<String?, String?, Boolean> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return Triple(
            prefs.getString(KEY_EMAIL, null),
            prefs.getString(KEY_PASSWORD, null),
            prefs.getBoolean(KEY_AUTO_LOGIN, false)
        )
    }
}