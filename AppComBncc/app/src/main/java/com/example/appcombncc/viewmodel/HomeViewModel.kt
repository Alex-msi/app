package com.example.appcombncc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcombncc.repository.EtapaRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: EtapaRepository) : ViewModel() {
    val etapas = repository.getAllEtapas()

    fun debugCountEtapas() = viewModelScope.launch {
        Log.d("DB_CHECK", "Função debugCountEtapas FOI CHAMADA!")  // ← Log inicial
        val total = repository.countEtapas()
        Log.d("DB_CHECK", "Total etapas: $total")
    }
}
