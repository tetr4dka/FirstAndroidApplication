package com.faleev.firstapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.faleev.firstapplication.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

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
        binding.inputField.hint = "Введите почту"
        binding.inputField.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    private fun switchToPhoneMode() {
        binding.inputField.hint = "Введите телефон"
        binding.inputField.inputType = android.text.InputType.TYPE_CLASS_PHONE
    }

    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            val input = binding.inputField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()
            val repeatPassword = binding.confirmPasswordField.text.toString().trim()

            if (validateInput(input, password, repeatPassword)) {
                AuthHelper.saveUserData(requireContext(), input, password, autoLogin = false)
                navController.navigate(R.id.action_registration_to_home)
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
        android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show()
    }
}