package com.faleev.firstapplication

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.faleev.firstapplication.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRadioGroup()
        setupRegisterButton()
    }

    private fun setupRadioGroup() {
        binding.registrationTypeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.phoneRegistration -> switchToPhoneMode()
                R.id.emailRegistration -> switchToEmailMode()
            }
        }
    }

    private fun switchToEmailMode() {
        binding.inputField.apply {
            hint = "Введите почту"
            inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            setText("") // Очищаем поле
            imeOptions = EditorInfo.IME_ACTION_NEXT // Опция "Далее" на клавиатуре
        }
    }

    private fun switchToPhoneMode() {
        binding.inputField.apply {
            hint = "Введите телефон"
            inputType = InputType.TYPE_CLASS_PHONE
            setText("") // Очищаем поле
            imeOptions = EditorInfo.IME_ACTION_NEXT
        }
    }

    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            val input = binding.inputField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()
            val repeatPassword = binding.confirmPasswordField.text.toString().trim()

            if (validateInput(input, password, repeatPassword)) {
                // Регистрация через Firebase
                auth.createUserWithEmailAndPassword(input, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Сохраняем данные локально
                            AuthHelper.saveUserData(
                                requireContext(),
                                input,
                                password,
                                autoLogin = false
                            )
                            findNavController().navigate(R.id.action_registration_to_home)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Ошибка: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
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
        binding.registrationTypeGroup.checkedRadioButtonId == R.id.emailRegistration

    private fun isPhoneRegistration() =
        binding.registrationTypeGroup.checkedRadioButtonId == R.id.phoneRegistration

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}