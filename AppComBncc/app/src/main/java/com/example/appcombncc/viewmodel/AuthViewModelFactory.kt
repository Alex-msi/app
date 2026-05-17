package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcombncc.repository.SessionRepository
import com.example.appcombncc.repository.UsuarioRepository

class AuthViewModelFactory(
    private val sessionRepository: SessionRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(sessionRepository, usuarioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
