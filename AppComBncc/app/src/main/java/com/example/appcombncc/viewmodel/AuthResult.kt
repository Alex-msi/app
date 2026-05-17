package com.example.appcombncc.viewmodel

sealed class AuthResult {
    data class Success(val email: String) : AuthResult()
    data object Cancelled : AuthResult()
    data object NetworkError : AuthResult()
    data class Error(val message: String) : AuthResult()
}
