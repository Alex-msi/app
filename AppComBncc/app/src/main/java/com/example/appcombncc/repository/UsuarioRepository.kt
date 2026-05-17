package com.example.appcombncc.repository

import com.example.appcombncc.data.dao.UsuarioDao
import com.example.appcombncc.data.entity.UsuarioEntity

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {
    suspend fun salvarOuAtualizarPrimeiroLogin(
        googleUid: String,
        email: String,
        loginEpochMs: Long
    ) {
        val emailNormalizado = email.trim().lowercase()

        if (emailNormalizado.isBlank()) {
            return
        }

        val usuarioExistente = usuarioDao.getByEmailSnapshot(emailNormalizado)

        if (usuarioExistente == null) {
            usuarioDao.inserir(
                UsuarioEntity(
                    id = null,
                    googleUid = googleUid.ifBlank { null },
                    nome = null,
                    email = emailNormalizado,
                    autenticado = 1,
                    ultimoLoginEm = loginEpochMs,
                    criadoEm = loginEpochMs,
                    atualizadoEm = loginEpochMs,
                    tipo = "professor"
                )
            )
            return
        }

        usuarioDao.atualizar(
            usuarioExistente.copy(
                googleUid = googleUid.ifBlank { usuarioExistente.googleUid },
                autenticado = 1,
                ultimoLoginEm = loginEpochMs,
                atualizadoEm = loginEpochMs
            )
        )
    }
}
