package com.example.appcombncc.util

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun salvarSessaoAutenticada(
        googleUid: String,
        email: String,
        nome: String,
        loginEpochMs: Long = System.currentTimeMillis(),
        tokenValidoAte: Long? = null
    ) {
        prefs.edit()
            .putBoolean(KEY_AUTENTICADO, true)
            .putString(KEY_GOOGLE_UID, googleUid)
            .putString(KEY_EMAIL, email)
            .putString(KEY_NOME, nome)
            .putLong(KEY_ULTIMO_LOGIN_EM, loginEpochMs)
            .putLong(KEY_TOKEN_VALIDO_ATE, tokenValidoAte ?: 0L)
            .apply()
    }

    fun limparSessao() {
        prefs.edit()
            .remove(KEY_AUTENTICADO)
            .remove(KEY_GOOGLE_UID)
            .remove(KEY_EMAIL)
            .remove(KEY_NOME)
            .remove(KEY_ULTIMO_LOGIN_EM)
            .remove(KEY_TOKEN_VALIDO_ATE)
            .apply()
    }

    fun obterSessao(): SessaoLocal {
        return SessaoLocal(
            autenticado = prefs.getBoolean(KEY_AUTENTICADO, false),
            googleUid = prefs.getString(KEY_GOOGLE_UID, "").orEmpty(),
            email = prefs.getString(KEY_EMAIL, "").orEmpty(),
            nome = prefs.getString(KEY_NOME, "").orEmpty(),
            ultimoLoginEm = prefs.getLong(KEY_ULTIMO_LOGIN_EM, 0L),
            tokenValidoAte = prefs.getLong(KEY_TOKEN_VALIDO_ATE, 0L).takeIf { it > 0L }
        )
    }

    fun sessaoValidaAgora(nowEpochMs: Long = System.currentTimeMillis()): Boolean {
        val sessao = obterSessao()
        if (!sessao.autenticado) return false
        if (sessao.googleUid.isBlank() || sessao.email.isBlank() || sessao.ultimoLoginEm <= 0L) return false

        val expiradaPorTempoLocal = nowEpochMs - sessao.ultimoLoginEm > DURACAO_SESSAO_MS
        if (expiradaPorTempoLocal) return false

        val tokenExpirado = sessao.tokenValidoAte?.let { nowEpochMs > it } ?: false
        return !tokenExpirado
    }

    companion object {
        private const val PREFS_NAME = "appcombncc_sessao"
        private const val KEY_AUTENTICADO = "autenticado"
        private const val KEY_GOOGLE_UID = "google_uid"
        private const val KEY_EMAIL = "email"
        private const val KEY_NOME = "nome"
        private const val KEY_ULTIMO_LOGIN_EM = "ultimo_login_em"
        private const val KEY_TOKEN_VALIDO_ATE = "token_valido_ate"

        // Política de negócio: sessão local válida por 30 dias.
        private const val DURACAO_SESSAO_MS = 30L * 24L * 60L * 60L * 1000L
    }
}

data class SessaoLocal(
    val autenticado: Boolean,
    val googleUid: String,
    val email: String,
    val nome: String,
    val ultimoLoginEm: Long,
    val tokenValidoAte: Long?
)
