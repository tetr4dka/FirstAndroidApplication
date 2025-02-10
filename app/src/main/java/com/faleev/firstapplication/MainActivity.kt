package com.faleev.firstapplication

import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var inputField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration) // Замените на имя вашего XML-файла

        initViews()
        setupRadioGroupListener()
        setupRegisterButton()
    }

    private fun initViews() {
        radioGroup = findViewById(R.id.registrationTypeGroup)
        inputField = findViewById(R.id.inputField)
        passwordField = findViewById(R.id.passwordField)
        confirmPasswordField = findViewById(R.id.confirmPasswordField)
        registerButton = findViewById(R.id.registerButton)
    }

    private fun setupRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.phoneRegistration -> switchToPhoneMode()
                R.id.emailRegistration -> switchToEmailMode()
            }
        }
    }

    private fun switchToEmailMode() {
        inputField.hint = "Введите почту"
        inputField.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        inputField.text?.clear()
    }

    private fun switchToPhoneMode() {
        inputField.hint = "Введите телефон"
        inputField.inputType = InputType.TYPE_CLASS_PHONE
        inputField.text?.clear()
    }

    private fun setupRegisterButton() {
        registerButton.setOnClickListener {
            val input = inputField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val repeatPassword = confirmPasswordField.text.toString().trim()

            if (!validateInput(input, password, repeatPassword)) return@setOnClickListener
            // Действия при успешной регистрации
        }
    }

    private fun validateInput(input: String, password: String, repeatPassword: String): Boolean {
        return when {
            isEmailRegistration() && !input.contains("@") -> {
                showToast("Некорректный email")
                false
            }
            isPhoneRegistration() && !input.startsWith("+") -> {
                showToast("Номер должен начинаться с +")
                false
            }
            password.length < 8 -> {
                showToast("Пароль должен содержать минимум 8 символов")
                false
            }
            password != repeatPassword -> {
                showToast("Пароли не совпадают")
                false
            }
            else -> true
        }
    }

    private fun isEmailRegistration() =
        radioGroup.checkedRadioButtonId == R.id.emailRegistration

    private fun isPhoneRegistration() =
        radioGroup.checkedRadioButtonId == R.id.phoneRegistration

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}