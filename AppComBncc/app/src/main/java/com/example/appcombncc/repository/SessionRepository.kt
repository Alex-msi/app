package com.example.appcombncc.repository

import com.example.appcombncc.util.SessionManager

class SessionRepository(
    private val sessionManager: SessionManager
) {
    fun salvarSessaoAutenticada(
        googleUid: String,
        email: String,
        nome: String,
        loginEpochMs: Long
    ) {
        sessionManager.salvarSessaoAutenticada(
            googleUid = googleUid,
            email = email,
            nome = nome,
            loginEpochMs = loginEpochMs
        )
    }
}
