package com.faleev.firstapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthStatus()
        }, 1500)
    }

    private fun checkAuthStatus() {
        val (email, password, autoLogin) = AuthHelper.getUserData(this)

        when {
            email != null && password != null && autoLogin ->
                startActivity(Intent(this, ContentActivity::class.java))

            email != null && password != null ->
                startActivity(Intent(this, LoginActivity::class.java))

            else ->
                startActivity(Intent(this, RegistrationActivity::class.java))
        }
        finish()
    }
}