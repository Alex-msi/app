package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcombncc.repository.UsuarioRepository
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    fun salvarOuAtualizarPrimeiroLogin(
        googleUid: String,
        email: String,
        loginEpochMs: Long
    ) {
        viewModelScope.launch {
            repository.salvarOuAtualizarPrimeiroLogin(
                googleUid = googleUid,
                email = email,
                loginEpochMs = loginEpochMs
            )
        }
    }
}
