package com.faleev.firstapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.faleev.firstapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            onLoginClick()
        }
    }

    private fun onLoginClick() {
        val email = binding.inputField.text.toString()
        val password = binding.passwordField.text.toString()
        val autoLogin = binding.autoLoginCheckBox.isChecked

        val (savedEmail, savedPassword, _) = AuthHelper.getUserData(this)

        if (email == savedEmail && password == savedPassword) {
            AuthHelper.saveUserData(this, email, password, autoLogin)
            startActivity(Intent(this, ContentActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Неверные данные", Toast.LENGTH_SHORT).show()
        }
    }
}