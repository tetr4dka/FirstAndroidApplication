package com.faleev.firstapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserStatus()
    }

    private fun checkUserStatus() {
        val (email, password, autoLogin) = AuthHelper.getUserData(requireContext())
        val navController = findNavController()

        when {
            email == null || password == null -> {
                // Пользователь не зарегистрирован
                navController.navigate(R.id.action_splash_to_registration)
            }
            autoLogin -> {
                // Пользователь выбрал "Автовход"
                navController.navigate(R.id.action_splash_to_home)
            }
            else -> {
                // Пользователь не выбрал "Автовход" — остаемся на текущем экране
                // или переходим на логин (если требуется)
            }
        }
    }
}