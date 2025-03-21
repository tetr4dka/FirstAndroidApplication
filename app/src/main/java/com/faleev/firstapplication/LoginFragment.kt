package com.faleev.firstapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.faleev.firstapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        setupLoginButton()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val email = binding.inputField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()
            val autoLogin = binding.autoLoginCheckBox.isChecked

            val (savedEmail, savedPassword, _) = AuthHelper.getUserData(requireContext())

            if (email == savedEmail && password == savedPassword) {
                AuthHelper.saveUserData(requireContext(), email, password, autoLogin)

                if (autoLogin) {
                    // Если галочка выбрана, переходим через SplashFragment
                    navController.navigate(R.id.action_login_to_splash)
                } else {
                    // Если галочка НЕ выбрана, переходим сразу в HomeFragment
                    navController.navigate(R.id.action_login_to_home)
                }
            } else {
                showToast("Неверные данные")
            }
        }
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT).show()
    }
}