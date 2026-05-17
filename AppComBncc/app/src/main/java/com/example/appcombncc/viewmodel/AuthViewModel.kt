package com.example.appcombncc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.CommonStatusCodes
import com.example.appcombncc.repository.SessionRepository
import com.example.appcombncc.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val sessionRepository: SessionRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _authResult = MutableStateFlow<AuthResult?>(null)
    val authResult: StateFlow<AuthResult?> = _authResult

    fun processarLoginComSucesso(
        googleUid: String,
        email: String,
        nome: String
    ) {
        val loginEpochMs = System.currentTimeMillis()
        sessionRepository.salvarSessaoAutenticada(
            googleUid = googleUid,
            email = email,
            nome = nome,
            loginEpochMs = loginEpochMs
        )

        viewModelScope.launch {
            usuarioRepository.salvarOuAtualizarPrimeiroLogin(
                googleUid = googleUid,
                email = email,
                loginEpochMs = loginEpochMs
            )
            _authResult.value = AuthResult.Success(email)
        }
    }


    fun sincronizarUsuarioSemNavegacao(
        googleUid: String,
        email: String,
        nome: String
    ) {
        val loginEpochMs = System.currentTimeMillis()
        if (googleUid.isBlank() || email.isBlank()) {
            return
        }

        sessionRepository.salvarSessaoAutenticada(
            googleUid = googleUid,
            email = email,
            nome = nome,
            loginEpochMs = loginEpochMs
        )

        viewModelScope.launch {
            usuarioRepository.salvarOuAtualizarPrimeiroLogin(
                googleUid = googleUid,
                email = email,
                loginEpochMs = loginEpochMs
            )
        }
    }

    fun processarFalhaLogin(statusCode: Int) {
        _authResult.value = when (statusCode) {
            CommonStatusCodes.CANCELED -> AuthResult.Cancelled
            CommonStatusCodes.NETWORK_ERROR -> AuthResult.NetworkError
            else -> AuthResult.Error("Falha na autenticação com Google. Código: $statusCode")
        }
    }

    fun consumirResultado() {
        _authResult.value = null
    }
}
